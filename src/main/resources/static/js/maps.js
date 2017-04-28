var map;
var marker;
		
function initMap(){
	map = new google.maps.Map(document.getElementById("map"), {
		center: {lat: -23.397, lng: -46.644},
		scrollwheel: true,
		zoom: 8
	});
	map.addListener('rightclick',function(e){
		if(!marker)
			marker = colocarMarker(e.latLng);
	});
}
	
function colocarMarker(latLng) {
	var mark = new google.maps.Marker({
		position: latLng,
		map: map
	});
	mark.setDraggable(true);
	marker.addListener('rightclick',function(){ marker.setMap(null); });
	return mark;
}

function teste(){
	alert('ola');
}
