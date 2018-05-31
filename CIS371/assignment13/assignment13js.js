function readTable(ind) {
	var table = document.getElementById('sqltable');
	var cells = table.getElementsByTagName('tr');
	var cellItems = table.getElementsByTagName('td');

	var lines = [];
	var items = [];

	for (var i=1; i<cells.length; i++){
		lines.push(cells[i]);
	}

	for (var i=0; i<cellItems.length; i++){
		if(cellItems[i].cellIndex == ind){
			items.push(cellItems[i].innerHTML);
		}
	}

	sort(lines, items, ind);
}

function sort(lines, items, ind){
	items.sort();

	var sorted = [];

	for(var i=0; i<items.length; i++){
		for(var j=0; j<lines.length; j++){
			if(items[i] == lines[j].getElementsByTagName('td')[ind].innerHTML){
				sorted.push(lines[j]);
				break;
			}
		}
	}

	var content = document.getElementsByTagName('tbody');

	var table = 0;

	if(content[0].innerHTML.indexOf("ID") == -1){
		table = 1;
	}

	for(var i=0; i<sorted.length; i++){
		//console.log(sorted[i].innerHTML);
		content[table].appendChild(sorted[i]);
	}
}

function addClick(){
	var li = document.getElementsByTagName("th");

	for (var i = 0; i < li.length; i++) {
		li[i].addEventListener("click", sortHandler);
	}
}

function sortHandler(event) {
	readTable(this.cellIndex);
}

function validateForm() {
    var userName = document.forms["form1"]["userName"].value;
    var firstName = document.forms["form1"]["firstName"].value;
    var lastName = document.forms["form1"]["lastName"].value;
    var age = document.forms["form1"]["age"].value;
    var phoneNumber = document.forms["form1"]["phoneNumber"].value

	if(!userName || !firstName || !lastName || !age || !phoneNumber){
		console.log("Empty field.\n");
		document.getElementById("error").innerHTML = "Empty field.";
		return false;
	}

	if(age < 1 || age > 120){
		console.log("Invalid age.\n");
		document.getElementById("error").innerHTML = "Invalid age.";
		return false;
	}

	var stripped = phoneNumber.replace(/[\(\)\.\-\ ]/g, '');

	if (isNaN(parseInt(stripped))) {
        console.log("Illegal characters in phone number.\n");
		document.getElementById("error").innerHTML = "Illegal characters in phone number.";
		return false;
    } else if (stripped.length != 10) {
        console.log("Too many characters in phone number (XXX-XXX-XXXX).\n");
		document.getElementById("error").innerHTML = "Too many characters in phone number (XXX-XXX-XXXX).";
		return false;
    } else{
		document.getElementById("phone").value = stripped;
	}

	return true;
}
