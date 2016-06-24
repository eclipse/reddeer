console.log('This would be the main JS file.');

function load_home(){
	document.getElementById("main-content").innerHTML='<object type="text/html" data="home.html" ></object>';
}
  
function load_installation(){
	document.getElementById("main-content").innerHTML='<object type="text/html" data="installation.html" ></object>';
}