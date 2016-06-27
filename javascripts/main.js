console.log('This would be the main JS file.');

function load_home(){
	document.getElementById("main-content").innerHTML='<iframe id="content_frame" scrolling="no" src="home.html" onload="resizeIframe(this)"></iframe>';
}
  
function load_installation(){
	document.getElementById("main-content").innerHTML='<iframe id="content_frame" scrolling="no" src="installation.html" onload="resizeIframe(this)"></iframe>';
}

function load_change_log(){
	document.getElementById("main-content").innerHTML='<iframe id="content_frame" scrolling="no" src="change_log.html" onload="resizeIframe(this)"></iframe>';
}

function resizeIframe(object) {
    object.style.height = object.contentWindow.document.body.scrollHeight + 'px';
    object.style.width = object.contentWindow.document.body.scrollWidth + 'px';
}