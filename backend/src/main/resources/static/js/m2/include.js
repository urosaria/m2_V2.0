document.write('<link href="/css/m2/_m2.css" rel="stylesheet" media="all" />');
document.write('<script src="/css/bootstrap/vendor/jquery/jquery.min.js"></script>');
document.write('<script src="/js/m2/layout.js"></script>');
document.write('<script src="/js/m2/form.js"></script>');
//운영서버용
// if (document.location.protocol == 'http:') {
//   document.location.href = document.location.href.replace('http:', 'https:');
// }

//analytics google
document.write('<script async src="https://www.googletagmanager.com/gtag/js?id=UA-129361480-1"></script>');
window.dataLayer = window.dataLayer || [];
function gtag(){dataLayer.push(arguments);}
gtag('js', new Date());
gtag('config', 'UA-129361480-1');

//analytics naver
document.write('<script async src="https://wcs.naver.net/wcslog.js"></script>');
window.onload = function() {
  if (!wcs_add) var wcs_add = {};
  wcs_add["wa"] = "b99938aa251970";
  wcs_do();
}

//analytics cafe24
//var JsHost = (("https:" == document.location.protocol) ? "https://" : "http://");
//var sTime = new Date().getTime();
//document.write(unescape("%3Cscript id='log_script' src='" + JsHost + "apppanel.weblog.cafe24.com/weblog.js?uid=apppanel&t="+sTime+"' type='text/javascript'%3E%3C/script%3E"));
