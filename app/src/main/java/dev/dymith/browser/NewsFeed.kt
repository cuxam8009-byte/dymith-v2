package dev.dymith.browser

import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/**
 * Tải RSS ngoài UI thread. Feed được cache ngắn hạn để trang chủ luôn có nội dung
 * ngay cả khi mạng chậm hoặc mất kết nối.
 */
internal class NewsFeed(private val activity: MainActivity) {
    private val worker = Executors.newSingleThreadExecutor()
    private val prefs = activity.getSharedPreferences("dymith_news", android.content.Context.MODE_PRIVATE)

    fun refresh(force: Boolean) {
        worker.execute {
            val cached = prefs.getString("json", "[]") ?: "[]"
            val updated = prefs.getLong("updated", 0L)
            val freshEnough = cached != "[]" && System.currentTimeMillis() - updated < 15L * 60L * 1000L
            if (cached != "[]") activity.runOnUiThread { deliver(cached, true) }
            if (!force && freshEnough) return@execute

            val fresh = fetch()
            if (fresh.length() > 0) {
                val encoded = fresh.toString()
                prefs.edit().putString("json", encoded).putLong("updated", System.currentTimeMillis()).apply()
                activity.runOnUiThread { deliver(encoded, false) }
            }
        }
    }

    private fun deliver(json: String, cached: Boolean) {
        val quoted = JSONObject.quote(json)
        activity.evaluateNews("window.DymithNews && window.DymithNews.receive($quoted, ${if (cached) "true" else "false"})")
    }

    private fun fetch(): JSONArray {
        val feeds = listOf(
            "https://vnexpress.net/rss/tin-moi-nhat.rss",
            "https://feeds.bbci.co.uk/news/rss.xml"
        )
        val all = JSONArray()
        for (feed in feeds) {
            try {
                val connection = URL(feed).openConnection() as HttpURLConnection
                connection.connectTimeout = 4500
                connection.readTimeout = 5000
                connection.instanceFollowRedirects = true
                connection.setRequestProperty("User-Agent", "DymithBrowser/1.0 (Android)")
                connection.setRequestProperty("Accept", "application/rss+xml, application/xml, text/xml")
                connection.inputStream.use { stream ->
                    val factory = XmlPullParserFactory.newInstance()
                    factory.isNamespaceAware = true
                    val parser = factory.newPullParser()
                    parser.setInput(stream, "UTF-8")
                    var inItem = false
                    var title = ""
                    var link = ""
                    var description = ""
                    var pubDate = ""
                    var image = ""
                    var source = if (feed.contains("vnexpress")) "VnExpress" else "BBC News"
                    var event = parser.eventType
                    while (event != XmlPullParser.END_DOCUMENT && all.length() < 14) {
                        when (event) {
                            XmlPullParser.START_TAG -> {
                                val tag = parser.name.lowercase()
                                if (tag == "item") {
                                    inItem = true
                                    title = ""; link = ""; description = ""; pubDate = ""; image = ""
                                } else if (inItem) {
                                    when (tag) {
                                        "title" -> title = parser.nextText().trim()
                                        "link" -> link = parser.nextText().trim()
                                        "description" -> description = parser.nextText().trim()
                                        "pubdate" -> pubDate = parser.nextText().trim()
                                        "enclosure" -> image = parser.getAttributeValue(null, "url") ?: image
                                        "content", "thumbnail" -> {
                                            val candidate = parser.getAttributeValue(null, "url")
                                            if (!candidate.isNullOrBlank()) image = candidate
                                        }
                                        "source" -> source = parser.nextText().trim().ifBlank { source }
                                    }
                                }
                            }
                            XmlPullParser.END_TAG -> if (parser.name.equals("item", true) && inItem) {
                                if (title.isNotBlank() && link.startsWith("http")) {
                                    if (image.isBlank()) {
                                        val match = Regex("""<img[^>]+src=['"]([^'"]+)""", RegexOption.IGNORE_CASE).find(description)
                                        image = match?.groupValues?.getOrNull(1) ?: ""
                                    }
                                    val summary = description.replace(Regex("<[^>]*>"), " ")
                                        .replace("&nbsp;", " ").replace("&amp;", "&")
                                        .replace(Regex("\\s+"), " ").trim().take(180)
                                    all.put(JSONObject()
                                        .put("title", title)
                                        .put("url", link)
                                        .put("summary", summary)
                                        .put("image", image)
                                        .put("date", pubDate)
                                        .put("source", source))
                                }
                                inItem = false
                            }
                        }
                        event = parser.next()
                    }
                }
                connection.disconnect()
            } catch (_: Throwable) {
                // Một feed lỗi không làm mất dữ liệu từ feed còn lại.
            }
        }
        return all
    }

    fun shutdown() {
        worker.shutdownNow()
    }
}
