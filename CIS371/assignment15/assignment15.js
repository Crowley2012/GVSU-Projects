function addClick(){
	var events = document.getElementsByClassName("event");

	for (var i = 0; i < events.length; i++) {
		events[i].addEventListener("click", displayEvent);
	}
}

function displayEvent(event){
	console.log(this.innerHTML);
	loadDoc();
}

function loadDoc() {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("demo").innerHTML = xhttp.responseText;
		}
	};

	xhttp.open("GET", "ajax_info.txt", true);
	xhttp.send();
}
