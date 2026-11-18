# Dymith Browser

Trình duyệt Android nhẹ, lõi Rust và vỏ Kotlin/WebView. Bản cập nhật này tập trung vào giảm jank trên thiết bị tầm trung, trang chủ ít lớp vẽ, nút bấm Canvas thực và tin tức RSS có ảnh thật.

## Kiến trúc

| Thành phần | Vai trò |
|---|---|
| `core/` | Rust: phân giải URL, chặn domain mẫu, JNI |
| `MainActivity.kt` | Vỏ native, WebView, thanh địa chỉ đáy, điều hướng và tải xuống |
| `CanvasIconDrawable.kt` | Icon native vẽ bằng Canvas, tái sử dụng Paint/Path |
| `StartPage.kt` | Dashboard HTML/CSS nhẹ; icon Canvas vẽ một lần, không animation liên tục |
| `NewsFeed.kt` | RSS parser chạy trên executor nền, cache 15 phút, trả ảnh/tiêu đề/mô tả/nguồn |
| `ci/build.sh` | Rust + Gradle + kiểm tra ELF 16 KB + APK |

## Những thay đổi chính

- Loại bỏ `GlassBackdrop`: không còn chụp/vẽ lại toàn bộ WebView theo từng `OnPreDraw` và không blur từng khung hình.
- Khởi tạo WebView bằng `androidx.webkit:webkit:1.16.0` và `WebViewCompat.startUpWebView`; chỉ tạo WebView sau callback để tránh vô hiệu hóa lợi ích async startup.
- Màn hình chờ native nhẹ xuất hiện trước khi WebView sẵn sàng.
- Trang chủ không còn ảnh hero lớn, không có tin giả hoặc thumbnail emoji. Chỉ render tin lấy từ RSS; nếu không có mạng thì dùng cache hoặc thông báo rỗng.
- RSS lấy từ VnExpress và BBC News; đọc tiêu đề, URL, mô tả, ngày, nguồn và ảnh từ enclosure/media thumbnail/HTML description. Ảnh tải lazy và decode async.
- Nút/icon trên trang chủ dùng Canvas 2D, còn phần tử điều khiển vẫn là `<button>`/`<a>` thật để giữ hit target, keyboard và accessibility.
- Thanh công cụ native dưới cùng dùng `CanvasIconDrawable`; Paint, Path và RectF được tạo một lần, tái sử dụng trong `draw()`.
- Lịch sử phiên duyệt, dấu trang và tải xuống được nối với hành động native. DownloadManager nhận các download HTTP(S).
- Rust source không thay đổi: số liệu JNI trước đó không chứng minh Rust là nguyên nhân jank; đường nóng chặn quảng cáo nằm ở request callback nền.

## Xây dựng

Dùng GitHub Actions workflow hiện tại hoặc chạy `ci/build.sh` trên runner có Android SDK/NDK và JDK 17/21. Không có Gradle wrapper trong repo.

## Ghi chú hiệu năng

Đây là bản tối ưu có cơ sở mã nguồn, chưa phải kết quả benchmark trên HONOR MT6765. Cần cài APK mới và đo lại bằng cùng kịch bản: mở app nguội/nóng, cuộn trang chủ, cuộn trang web nặng, mở bàn phím, chuyển tab/trang, bật/tắt theme. So sánh p50/p90/p99 và dropped frames bằng Perfetto FrameTimeline; không suy luận hiệu năng chỉ từ một lần đo `FrameMetrics`.
