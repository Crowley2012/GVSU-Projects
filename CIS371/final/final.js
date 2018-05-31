function start(){
	var upcase = document.getElementsByClassName("allcaps");

	for(i=0; i<upcase.length; i++){
		upcase[i].style.textTransform = 'uppercase';
	}

	var upcase = document.getElementsByClassName("upcase");

	for(i=0; i<upcase.length; i++){
		upcase[i].style.textTransform = 'capitalize';
	}
}
