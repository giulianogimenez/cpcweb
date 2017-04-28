function buttonMap(idButton,idWindow) {
	objWindow = document.getElementById(idWindow);
	objButton = document.getElementById(idButton);
	
	if(objWindow.style.display == 'block'){
		objWindow.style.display = 'none';
		objButton.innerHTML = 'Adicionar';
	}else{
		objWindow.style.display = 'block';
		objButton.innerHTML = 'Mapa';
		
	}
}

function teste(){
	alert('ola');
}
