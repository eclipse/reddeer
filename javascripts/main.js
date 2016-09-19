function loadPage(page) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			setHtmlForElement("main-content", xhttp.responseText);
		}
	};
	xhttp.open("GET", page, true);
	xhttp.send();
}

function initPage() {
	if (window.location.hash) {
		loadPage(window.location.hash.substring(1).concat(".html"));
	} else {
		loadPage('home.html');
	}
}

function setHtmlForElement(elementID, html) {
	document.getElementById(elementID).innerHTML = html;
}