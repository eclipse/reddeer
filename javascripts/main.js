function loadPage(page) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			setHtmlForElement("main-content", xhttp.responseText);
			loadSideMenu(page);
			window.scrollTo(0,0);
		}
	};
	xhttp.open("GET", page, true);
	xhttp.send();
}

function initPage() {
	if (window.location.hash) {
		var page = window.location.hash.substring(1).concat(".html");
		loadPage(page);
		loadSideMenu(page);
	} else {
		loadPage('home.html');
	}
}

function setHtmlForElement(elementID, html) {
	document.getElementById(elementID).innerHTML = html;
}

function loadSideMenu(page) {
	var x = document.getElementsByClassName("menu-item");
	var menuHTML = "";
	if (x.length > 0) {
		menuHTML += "<ul>"
		for (var i = 0; i < x.length; i++) {
			var menuItem = x[i].getAttribute("id");
			menuHTML += "<li><a href=\"#" + menuItem + "\">" + menuItem + "</a></li>";
		}
		menuHTML += "</ul>";
	}
	document.getElementById("menu-wrapper").innerHTML=menuHTML;
}

