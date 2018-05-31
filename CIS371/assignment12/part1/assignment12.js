function findLinks() {
    var answer = [];

	var arr = [], l = document.links;
	for(var i=0; i<l.length; i++) {
		if(l[i].href.indexOf("#") > -1){
			answer.push(l[i].href.substring(l[i].href.indexOf("#"), l[i].href.length));
		}else{
			answer.push(l[i].href.substring(0, l[i].href.length - 1));
		}
	}
    return answer;
}

function verifyResult(observed, expected) {
    if (typeof(observed) == "undefined") {
        console.log("findLinks doesn't appear to be ready yet.");
        return;
    }

    console.log(observed);
    console.log(expected);

    var message = "Success";
    var detail = "";
    if (observed.length != expected.length) {
        message = "Fail:  Lengths differ.";
    } else {
        expected.forEach(function (element, index) {
            if (element != observed[index]) {
                message = "Fail";
                detail += "<br/>Element " + index + " differs.";
                console.log("Element " + index + " differs: ");
                console.log(element);
                console.log(observed[index]);
            }
        });
    }

    var newItem = document.createElement("div");
    newItem.innerHTML = message + detail;
    newItem.style.display = "inline-block";
    newItem.style.backgroundColor = message === "Success" ? "lightgreen" : "red";
    newItem.style.padding = "15px";
    document.getElementsByTagName("body")[0].appendChild(newItem);
}

findLinks();


