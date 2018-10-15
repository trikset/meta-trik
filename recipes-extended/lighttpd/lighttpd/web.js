var allVarsContainer = {};
var currentVariables = {};

setInterval(function() {
                  sendRequest();
                }, 10000); 

function sendRequest() {
  	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "/web/", true);
  	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var o = JSON.parse(xhttp.response);
			currentVariables = o;
			for (var v in o) {
				var d = document.getElementById('varValue_'+ v );
				if (typeof d !== "undefined" && d !== null) {
					d.innerHTML = o[v];
				}
			}
    		}
	};
  
	xhttp.send();
}

function checkVariableExists(varName) {
	console.log("check vars");
	console.log(currentVariables);
	for (var v in currentVariables) {
		if (v == varName) {
			console.log(v, varName);
			return true;
		}
	}
	return false;
}

function addNewVarView(varName) {
	if (checkVariableExists(varName)) {
		var d = document.getElementById('var_'+ varName) 
		if (typeof d === "undefined" || d === null) {
			var nameCell = document.createElement('div');
			nameCell.classList.add("TableCell");
			nameCell.setAttribute("id", "varName_" + varName);
			nameCell.innerHTML= varName;
			
			var valueCell = document.createElement('div');
			valueCell.classList.add("TableCell");
			valueCell.setAttribute("id", "varValue_" + varName);
		
			var row = document.createElement('div');
			row.classList.add("TableRow");
			row.setAttribute("id", "var_" + varName);
			row.appendChild(nameCell);
			row.appendChild(valueCell);
		
			allVarsContainer.appendChild(row);
		} else {
			console.log("This variables already is used");
		}
	}
}

function globalInit() {
	allVarsContainer = document.getElementById("allVars");
}
 
window.onload = function(e) { globalInit(); }
