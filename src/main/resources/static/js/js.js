
function passwordBut(){
    if(document.getElementById("Password").type == "password"){
        document.getElementById("Password").type = "text";
        pas = false;
    }
    else {
        document.getElementById("Password").type = "password";
        pas = true;
    }
}

function passwordButton(password){
    if(document.getElementById(password).type == "password"){
        document.getElementById(password).type = "text";
        pas = false;
    }
    else {
        document.getElementById(password).type = "password";
        pas = true;
    }
}

function getType() {
    if (document.getElementById("divType").style.display == 'block'){
        document.getElementById("divTypeA").style.display = 'none';
		document.getElementById("divType").style.animation = 'ani2 0.5s linear both';
		document.getElementById("divType").addEventListener('animationend', function() {
			document.getElementById("divType").style.display = 'none';
			document.getElementById("divTypeA").style.display = 'none';
			document.getElementById("img").setAttribute("src", "/img/Down.png");
		});
	}
	else {
		document.getElementById("divType").style.display = 'block';
		document.getElementById("divType").style.animation = 'ani1 0.5s linear both';
		document.getElementById("divType").addEventListener('animationend', function() {
			document.getElementById("divType").style.display = 'block';
			document.getElementById("divTypeA").style.display = 'flex';
			document.getElementById("img").setAttribute("src", "/img/Up.png");
		});
	}
}

function complaint() {
    if (document.getElementById("divForm").style.display == 'block'){
        document.getElementById("form").style.display = 'none';
		document.getElementById("divForm").style.animation = 'ani2 0.5s linear both';
		document.getElementById("divForm").addEventListener('animationend', function() {
			document.getElementById("divForm").style.display = 'none';
			document.getElementById("form").style.display = 'none';
		});
	}
	else {
		document.getElementById("divForm").style.display = 'block';
		document.getElementById("divForm").style.animation = 'ani1 0.5s linear both';
		document.getElementById("divForm").addEventListener('animationend', function() {
			document.getElementById("divForm").style.display = 'block';
			document.getElementById("form").style.display = 'block';
			document.getElementById("img").setAttribute("src", "/img/Up.png");
		});
	}
}

function check(){
    let addressCheck = document.getElementById("AddressCheck");
    let telephoneCheck = document.getElementById("TelephoneCheck");
    let nameCheck = document.getElementById("NameCheck");

    if(nameCheck.checked){
        document.getElementById("Name").removeAttribute("required");
        document.getElementById("Name").value = "";
        document.getElementById("Name").placeholder="";
    }
    else {
        document.getElementById("Name").placeholder="Введите ФИО";
        document.getElementById("Name").setAttribute("required", "required");
    }

    if(addressCheck.checked){
        document.getElementById("Address").removeAttribute("required");
        document.getElementById("Address").value = "";
        document.getElementById("Address").placeholder="";
    }
    else {
        document.getElementById("Address").placeholder="Введите адрес";
        document.getElementById("Address").setAttribute("required", "required");
    }

    if(telephoneCheck.checked){
        document.getElementById("Telephone").removeAttribute("required");
        document.getElementById("Telephone").value = "";
        document.getElementById("Telephone").placeholder="";
    }
    else {
        document.getElementById("Telephone").placeholder="Введите телефон";
        document.getElementById("Telephone").setAttribute("required", "required");
    }
}