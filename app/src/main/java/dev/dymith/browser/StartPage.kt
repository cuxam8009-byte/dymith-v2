package dev.dymith.browser

/** Trang khởi động Dymith: UI tĩnh, nhẹ; dữ liệu tin tức được bơm từ RSS native. */
object StartPage {
    const val URL = "https://start.dymith.invalid/"
    val HTML: String = """
<!doctype html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover">
<meta name="theme-color" content="#f7f8fc">
<title>Dymith</title>
<style>
:root{color-scheme:light;--bg:#f7f8fc;--surface:#fff;--surface2:#f1f3fb;--ink:#17213b;--muted:#77829a;--line:#e8ebf4;--accent:#6656df;--accent-soft:#efedff;--radius:20px}
*{box-sizing:border-box;-webkit-tap-highlight-color:transparent}
html{background:var(--bg);min-height:100%;scroll-behavior:smooth}
body{margin:0;padding:calc(env(safe-area-inset-top) + 10px) 16px 30px;background:var(--bg);color:var(--ink);font:15px/1.45 system-ui,-apple-system,"Segoe UI",Roboto,sans-serif}
button,a{font:inherit}button{color:inherit}
.topbar{height:48px;display:flex;align-items:center;justify-content:space-between}
.brand{display:flex;align-items:center;gap:10px;font-size:22px;font-weight:780;letter-spacing:-.7px}
.brandmark{width:34px;height:34px;border-radius:12px;background:var(--accent);color:white;display:grid;place-items:center;font-size:21px;font-weight:850}
.actions{display:flex;gap:8px}
.icon-button{width:42px;height:42px;display:grid;place-items:center;border:1px solid var(--line);border-radius:14px;background:var(--surface);padding:0;cursor:pointer}
.icon-button:active,.tool:active,.shortcut:active{transform:scale(.97)}
canvas.glyph{width:21px;height:21px;display:block;pointer-events:none}
.welcome{padding:23px 2px 18px}
.eyebrow{font-size:11px;font-weight:750;letter-spacing:1.1px;text-transform:uppercase;color:var(--accent)}
h1{font-size:29px;line-height:1.13;letter-spacing:-1.15px;margin:7px 0 7px;font-weight:780}
.welcome p{font-size:14px;color:var(--muted);margin:0;max-width:310px}
.event{margin-top:13px;display:inline-flex;align-items:center;gap:7px;border-radius:999px;padding:6px 10px;background:var(--accent-soft);color:var(--accent);font-size:11px;font-weight:700}
.section-head{display:flex;align-items:center;justify-content:space-between;margin:18px 2px 11px}
h2{font-size:15px;letter-spacing:-.2px;margin:0;font-weight:760}
.subtle-action{border:0;background:transparent;color:var(--accent);font-size:12px;font-weight:700;padding:8px 2px}
.shortcuts{display:grid;grid-template-columns:repeat(5,minmax(0,1fr));gap:8px}
.shortcut{min-width:0;text-decoration:none;color:var(--ink);text-align:center;display:block}
.shortcut-tile{height:60px;max-width:64px;margin:auto;border:1px solid var(--line);border-radius:19px;background:var(--surface);display:grid;place-items:center;box-shadow:0 2px 8px rgba(24,36,72,.035)}
.shortcut-tile canvas{width:28px;height:28px}
.shortcut-label{display:block;margin-top:7px;font-size:11px;font-weight:650;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}
.tools{display:grid;grid-template-columns:repeat(4,minmax(0,1fr));gap:9px}
.tool{border:1px solid var(--line);border-radius:17px;background:var(--surface);min-width:0;padding:13px 4px 11px;display:flex;flex-direction:column;align-items:center;gap:7px;font-size:11px;font-weight:650;cursor:pointer}
.tool-icon{width:38px;height:38px;border-radius:13px;background:var(--accent-soft);display:grid;place-items:center;color:var(--accent)}
.tool-icon canvas{width:21px;height:21px}
.news-head{margin-top:23px}
.news-status{font-size:10px;color:var(--muted);font-weight:500}
.news-list{display:grid;gap:0;border:1px solid var(--line);border-radius:var(--radius);background:var(--surface);padding:0 13px;overflow:hidden}
.news-card{display:grid;grid-template-columns:108px minmax(0,1fr);gap:12px;padding:13px 0;border-bottom:1px solid var(--line);color:inherit;text-decoration:none;min-height:105px}
.news-card:last-child{border-bottom:0}
.news-image{width:108px;height:82px;border-radius:12px;object-fit:cover;background:var(--surface2);display:block}
.news-placeholder{width:108px;height:82px;border-radius:12px;background:var(--surface2);display:grid;place-items:center;color:var(--muted);font-size:10px}
.news-source{font-size:10px;font-weight:750;color:var(--accent)}
.news-title{font-size:13px;line-height:1.35;font-weight:720;margin:4px 0 5px;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;overflow:hidden}
.news-summary{font-size:11px;color:var(--muted);line-height:1.35;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;overflow:hidden}
.news-meta{font-size:10px;color:var(--muted);margin-top:6px}
.empty-news{padding:22px 10px;text-align:center;color:var(--muted);font-size:12px}
.loading-dot{display:inline-block;width:6px;height:6px;border-radius:50%;background:var(--accent);margin-right:5px}
.footer{padding:18px 0 4px;text-align:center;color:#9aa3b7;font-size:10px}
body.dark{color-scheme:dark;--bg:#111827;--surface:#1b2538;--surface2:#263249;--ink:#edf1fb;--muted:#a5b0c6;--line:#2b3850;--accent:#a99cff;--accent-soft:#302d54}
body.dark .brandmark{background:#7768e9}
body.dark .news-image{filter:none}
@media(max-width:360px){body{padding-left:13px;padding-right:13px}.shortcut-tile{height:54px;max-width:57px}.news-card{grid-template-columns:92px minmax(0,1fr);gap:10px}.news-image,.news-placeholder{width:92px;height:74px}.tool{padding-left:2px;padding-right:2px}}
@media(prefers-reduced-motion:reduce){*{scroll-behavior:auto!important;transition:none!important;animation:none!important}}
</style>
</head>
<body>
<header class="topbar">
  <button class="icon-button" aria-label="Mở menu" onclick="panel('settings')"><canvas class="glyph" data-icon="menu"></canvas></button>
  <div class="brand"><span class="brandmark">D</span><span>Dymith</span></div>
  <div class="actions">
    <button class="icon-button" aria-label="Đổi giao diện sáng tối" onclick="toggleTheme()"><canvas class="glyph" data-icon="theme"></canvas></button>
    <button class="icon-button" aria-label="Cài đặt" onclick="panel('settings')"><canvas class="glyph" data-icon="settings"></canvas></button>
  </div>
</header>

<main>
  <section class="welcome">
    <div class="eyebrow">YOUR EVERYDAY BROWSER</div>
    <h1 id="greeting">Chào mừng trở lại.</h1>
    <p id="welcome-copy">Một không gian gọn gàng để khám phá thế giới web theo cách của bạn.</p>
    <div class="event" id="event-chip" hidden></div>
  </section>

  <div class="section-head"><h2>Truy cập nhanh</h2><button class="subtle-action" onclick="panel('bookmarks')">Quản lý</button></div>
  <section class="shortcuts" aria-label="Website truy cập nhanh">
    <a class="shortcut" href="https://www.google.com"><span class="shortcut-tile"><canvas class="glyph" data-icon="google"></canvas></span><span class="shortcut-label">Google</span></a>
    <a class="shortcut" href="https://m.youtube.com"><span class="shortcut-tile"><canvas class="glyph" data-icon="youtube"></canvas></span><span class="shortcut-label">YouTube</span></a>
    <a class="shortcut" href="https://www.facebook.com"><span class="shortcut-tile"><canvas class="glyph" data-icon="facebook"></canvas></span><span class="shortcut-label">Facebook</span></a>
    <a class="shortcut" href="https://www.tiktok.com"><span class="shortcut-tile"><canvas class="glyph" data-icon="tiktok"></canvas></span><span class="shortcut-label">TikTok</span></a>
    <button class="shortcut" style="border:0;background:none;padding:0" onclick="panel('bookmarks')"><span class="shortcut-tile"><canvas class="glyph" data-icon="bookmark"></canvas></span><span class="shortcut-label">Dấu trang</span></button>
  </section>

  <div class="section-head"><h2>Công cụ của bạn</h2></div>
  <section class="tools">
    <button class="tool" onclick="panel('history')"><span class="tool-icon"><canvas class="glyph" data-icon="history"></canvas></span>Lịch sử</button>
    <button class="tool" onclick="panel('downloads')"><span class="tool-icon"><canvas class="glyph" data-icon="download"></canvas></span>Tải xuống</button>
    <button class="tool" onclick="panel('bookmarks')"><span class="tool-icon"><canvas class="glyph" data-icon="bookmark"></canvas></span>Dấu trang</button>
    <button class="tool" onclick="panel('settings')"><span class="tool-icon"><canvas class="glyph" data-icon="settings"></canvas></span>Cài đặt</button>
  </section>

  <div class="section-head news-head"><div><h2>Tin tức mới nhất</h2><div class="news-status" id="news-status">Đang chuẩn bị nguồn tin…</div></div><button class="subtle-action" onclick="refreshNews(true)">Làm mới</button></div>
  <section class="news-list" id="news-list" aria-live="polite"><div class="empty-news"><span class="loading-dot"></span>Đang tải tin tức…</div></section>
</main>
<footer class="footer">Dymith · Gọn gàng, nhanh chóng, riêng tư</footer>

<script>
(function(){
'use strict';
const NS='http://www.w3.org/2000/svg';
function draw(canvas,name){
 const dpr=Math.min(window.devicePixelRatio||1,2),w=canvas.clientWidth||22,h=canvas.clientHeight||22;
 canvas.width=Math.round(w*dpr);canvas.height=Math.round(h*dpr);
 const c=canvas.getContext('2d',{alpha:true});if(!c)return;c.scale(dpr,dpr);
 c.strokeStyle='#6656df';c.fillStyle='#6656df';c.lineWidth=1.9;c.lineCap='round';c.lineJoin='round';
 const line=(pts)=>{c.beginPath();pts.forEach((p,i)=>i?c.lineTo(p[0],p[1]):c.moveTo(p[0],p[1]));c.stroke()};
 const circle=(x,y,r)=>{c.beginPath();c.arc(x,y,r,0,Math.PI*2);c.stroke()};
 switch(name){
 case'menu':line([[4,6],[18,6]]);line([[4,11],[18,11]]);line([[4,16],[18,16]]);break;
 case'theme':circle(11,11,4);for(let a=0;a<8;a++){let t=a*Math.PI/4;line([[11+7*Math.cos(t),11+7*Math.sin(t)],[11+9*Math.cos(t),11+9*Math.sin(t)]])}break;
 case'settings':circle(11,11,7);circle(11,11,2.5);for(let a=0;a<8;a++){let t=a*Math.PI/4;line([[11+8*Math.cos(t),11+8*Math.sin(t)],[11+10*Math.cos(t),11+10*Math.sin(t)]])}break;
 case'plus':line([[11,4],[11,18]]);line([[4,11],[18,11]]);break;
 case'history':circle(11,11,8);line([[11,6],[11,11],[15,13]]);break;
 case'download':line([[11,3],[11,14]]);line([[7,10],[11,14],[15,10]]);line([[4,18],[18,18]]);break;
 case'bookmark':c.beginPath();c.moveTo(6,3);c.lineTo(16,3);c.lineTo(16,19);c.lineTo(11,15);c.lineTo(6,19);c.closePath();c.stroke();break;
 case'google':c.lineWidth=3.5;c.strokeStyle='#4285F4';c.beginPath();c.arc(11,11,7,Math.PI*.18,Math.PI*1.85);c.stroke();c.strokeStyle='#34A853';c.beginPath();c.arc(11,11,7,Math.PI*.85,Math.PI*1.45);c.stroke();c.strokeStyle='#FBBC05';c.beginPath();c.arc(11,11,7,Math.PI*1.45,Math.PI*1.78);c.stroke();c.strokeStyle='#EA4335';c.beginPath();c.arc(11,11,7,Math.PI*1.78,Math.PI*2.18);c.stroke();line([[11,11],[18,11]]);break;
 case'youtube':c.fillStyle='#f12b35';c.beginPath();c.roundRect(2,5,18,12,4);c.fill();c.fillStyle='#fff';c.beginPath();c.moveTo(9,8);c.lineTo(15,11);c.lineTo(9,14);c.closePath();c.fill();break;
 case'facebook':c.fillStyle='#1877f2';c.beginPath();c.arc(11,11,9,0,Math.PI*2);c.fill();c.fillStyle='#fff';c.font='bold 17px Arial';c.fillText('f',8,17);break;
 case'tiktok':c.strokeStyle='#111';c.lineWidth=3;c.beginPath();c.moveTo(12,3);c.lineTo(12,14);c.arc(9,14,3,0,Math.PI*2);c.stroke();c.strokeStyle='#20d5ec';c.lineWidth=1.4;c.beginPath();c.moveTo(10,3);c.lineTo(10,14);c.stroke();c.strokeStyle='#ff315a';c.beginPath();c.moveTo(14,4);c.lineTo(14,15);c.stroke();break;
 }
}
document.querySelectorAll('canvas[data-icon]').forEach(x=>draw(x,x.dataset.icon));
window.addEventListener('resize',()=>document.querySelectorAll('canvas[data-icon]').forEach(x=>draw(x,x.dataset.icon)),{passive:true});
function nativeAction(action,extra){if(window.Dymith&&typeof Dymith.postMessage==='function')Dymith.postMessage(JSON.stringify(Object.assign({action:action},extra||{})))}
window.panel=function(name){nativeAction('panel',{name:name})};
window.addBookmark=function(){nativeAction('bookmark')};
window.toggleTheme=function(){document.body.classList.toggle('dark');localStorage.setItem('dymith-theme',document.body.classList.contains('dark')?'dark':'light')};
if(localStorage.getItem('dymith-theme')==='dark')document.body.classList.add('dark');
window.refreshNews=function(force){const status=document.getElementById('news-status');status.textContent='Đang cập nhật…';if(window.Dymith&&Dymith.postMessage)nativeAction('news',{force:!!force});else status.textContent='Không thể kết nối dịch vụ tin tức'};
function escapeHtml(s){return String(s||'').replace(/[&<>"']/g,ch=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[ch]))}
function render(items,cached){
 const list=document.getElementById('news-list'),status=document.getElementById('news-status');
 if(!Array.isArray(items)||!items.length){if(!list.children.length)list.innerHTML='<div class="empty-news">Chưa tải được tin tức. Hãy kiểm tra kết nối và thử làm mới.</div>';status.textContent='Không có dữ liệu mới';return}
 list.innerHTML='';
 items.slice(0,10).forEach(item=>{
  const a=document.createElement('a');a.className='news-card';a.href=item.url;a.rel='noopener noreferrer';
  const image=(item.image||'').startsWith('https://')?item.image:'';
  const media=image?'<img class="news-image" loading="lazy" decoding="async" width="108" height="82" src="'+escapeHtml(image)+'" alt="">':'<div class="news-placeholder">DY MITH NEWS</div>';
  a.innerHTML=media+'<div><div class="news-source">'+escapeHtml(item.source||'Tin tức')+'</div><div class="news-title">'+escapeHtml(item.title)+'</div><div class="news-summary">'+escapeHtml(item.summary||'Mở bài viết để xem nội dung đầy đủ.')+'</div><div class="news-meta">'+escapeHtml(item.date||'Tin mới')+'</div></div>';
  list.appendChild(a);
 });
 status.textContent=(cached?'Tin đã lưu · ':'Cập nhật trực tuyến · ')+items.length+' bài viết';
}
window.DymithNews={receive:function(json,cached){try{render(JSON.parse(json),cached)}catch(e){document.getElementById('news-status').textContent='Lỗi đọc nguồn tin'}}};
const d=new Date(),md=(d.getMonth()+1)+'-'+d.getDate();
const holidays={'1-1':['Chào năm mới','Happy New Year'],'2-14':['Ngày Valentine','Valentine’s Day'],'3-8':['Ngày Quốc tế Phụ nữ','International Women’s Day'],'4-22':['Ngày Trái Đất','Earth Day'],'5-1':['Ngày Quốc tế Lao động','International Workers’ Day'],'6-5':['Ngày Môi trường Thế giới','World Environment Day'],'10-31':['Halloween','Halloween'],'12-24':['Mùa Giáng sinh','Christmas season'],'12-25':['Giáng sinh','Merry Christmas'],'12-31':['Đêm giao thừa','New Year’s Eve']};
const event=holidays[md];if(event){const chip=document.getElementById('event-chip');chip.hidden=false;chip.textContent='✦ '+event[0]+' · '+event[1];document.getElementById('welcome-copy').textContent='Chúc bạn có một ngày thật đẹp. Cùng khám phá thế giới web nhé.'}
const hour=d.getHours();document.getElementById('greeting').textContent=hour<11?'Chào buổi sáng.':hour<18?'Chào buổi chiều.':'Chào buổi tối.';
window.addEventListener('load',()=>setTimeout(()=>refreshNews(false),120));
})();
</script>
</body>
</html>
""".trimIndent()
}
