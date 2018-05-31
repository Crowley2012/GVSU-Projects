function readTable() {
	var table = document.getElementById('profs');
	var cells = table.getElementsByTagName('tr');
	var cellItems = table.getElementsByTagName('td');

	var lines = [];
	var profs = [];

	for (var i=1; i<cells.length; i++){
		lines.push(cells[i]);
	}

	for (var i=0; i<cellItems.length; i++){
		if(cellItems[i].cellIndex == 1){
			profs.push(cellItems[i].innerHTML);
		}
	}

	sort(lines, profs);
}

function sort(lines, profs){
	profs.sort();

	var sorted = [];

	for(var i=0; i<profs.length; i++){
		for(var j=0; j<lines.length; j++){
			if(profs[i] == lines[j].getElementsByTagName('td')[1].innerHTML){
				sorted.push(lines[j]);
				break;
			}
		}
	}

	var content = document.getElementsByTagName('tbody');

	var table = 0;

	if(content[0].innerHTML.indexOf("First Name") == -1){
		table = 1;
	}

	for(var i=0; i<sorted.length; i++){
		content[table].appendChild(sorted[i]);
	}

}
