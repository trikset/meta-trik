function hasClass(element, wantedClass) {
    return (" " + element.className + " ").indexOf(" " + wantedClass + " ") > -1;
}

function sendNetworkData(paramString) {
    var xhr = new XMLHttpRequest();
    xhr.open("post", "wpa-writer"); 
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                successMessage.style.display = 'block';
            } else if (xhr.status == 422) {
                errorMessage.innerHTML = 'Passphrase must be 8..63 characters!';
                errorMessage.style.display = 'block';
            } else if (xhr.status == 500) {
                errorMessage.innerHTML = 'Internal server error. Please try again.';
                errorMessage.style.display = 'block';
            }
        }
    };

    xhr.send(paramString);
}

var links = document.getElementsByTagName('a'); 
var successMessage = document.getElementById('success_msg');
var errorMessage = document.getElementById('error_msg');
for(var i = 0; i < links.length; i++) {
    links[i].onclick = function(event) {
        successMessage.style.display = 'none';
        errorMessage.style.display = 'none';
        var essid = event.target.text;
        if (hasClass(event.target, 'encrypted')) {
            var password = prompt("Input password for " + essid);
            if (password) {
                sendNetworkData('essid=' + essid + '&password=' + password);
            }
        } else {
            sendNetworkData('essid=' + essid);
        }
    } 
}

