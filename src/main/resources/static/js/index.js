var map;
var markerRef;
var markers = [];

$(document).ready(function() {
	$('form').submit(function(e) {
		e.preventDefault();

		var nome = $('#nome').val().trim();
		var bandeira = $('#bandeira').val().trim();
		var endereco = $('#endereco').val().trim();
		
		var lat = markerRef.getPosition().lat();
		var longi = markerRef.getPosition().lng();
		var conveniencia = ($('#conveniencia').is(':checked'));
		var alimentacao = ($('#alimentacao').is(':checked'));
		var trocaOleo = ($('#trocaOleo').is(':checked'));
		var lavaRapido = ($('#lavaRapido').is(':checked'));
		var mecanico = ($('#mecanico').is(':checked'));
		var borracheiro = ($('#borracheiro').is(':checked'));
		var caixaEletronico = ($('#caixaEletronico').is(':checked'));
		var semParar = ($('#semParar').is(':checked'));
		var viaFacil = ($('#viaFacil').is(':checked'));
		var precoGasolina = ($('#gasolinaComum').val());
		var etanol = $('#etanol').val();
		$.post("/estabelecimento", JSON.stringify({
			'nome' : nome,
			'bandeira' : bandeira,
			'endereco' : endereco,
			'lat' : lat,
			'longi' : longi,
			'conveniencia' : conveniencia,
			'alimentacao' : alimentacao,
			'trocaOleo' : trocaOleo,
			'lavaRapido' : lavaRapido,
			'mecanico' : mecanico,
			'borracheiro' : borracheiro,
			'caixaEletronico' : caixaEletronico,
			'semParar' : semParar,
			'viaFacil' : viaFacil,
			'precoGasolina' : precoGasolina,
			'etanol' : etanol
		}), function(data) {
			if (data[0].status == 1) {
				window.location.href = '/index.html';
			} else
				alert("Erro");
		}, "json");
	});
	
	$("#cancelar").click(function(){
		$("#janela input[type=text]").val('');
		$("#janela select :selected").prop('selected', '');
		$("#janela input[type=checkbox]").prop('checked', '');
		$("#janela").css('display', 'none');
		markerRef.setMap(null);
		markerRef = null;
	});
});
function initMap() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(posicaoAtual);
	}
	map = new google.maps.Map(document.getElementById("map"), {
		center : {
			lat : -23.397,
			lng : -46.644
		},
		scrollwheel : true,
		zoom : 15
	});
	map.addListener('rightclick', function(e) {
		if (markerRef == null)
			iniciaMarker(e.latLng);
		else {
			markerRef.setPosition(e.latLng);
			setEndereco(e.latLng);
		}
	});

	$.getJSON("/estabelecimento", function(e) {
		$.each(e, function(i, item) {

			var infoWindow = new google.maps.InfoWindow({
				content : content(item)
			});
			var marker = new google.maps.Marker({
				position : {
					lat : item.lat,
					lng : item.longi
				},
				map : map,
				animation : google.maps.Animation.DROP
			});
			marker.addListener('click', function() {
				infoWindow.open(map, marker);
			});
			marker.setDraggable(false);

			if (item.bandeira === "SHELL")
				marker.setIcon("img/shell_pin.png");
			if (item.bandeira === "IPIRANGA")
				marker.setIcon("img/ipiranga_pin.png");
			if (item.bandeira === "BR")
				marker.setIcon("img/br_pin.png");
			markers.push(marker);
		});
	});
}

function content(item) {
	var str = "<div style=\"width: 300px; height:200px; padding:10px;\">";
	str += "<h5>" + item.nome + "</h5><br>";
	if (item.conveniencia)
		str += "Conveniência<br>";
	if (item.alimentacao)
		str += "Alimentação<br>";
	if (item.trocaOleo)
		str += "Troca de óleo<br>";
	if (item.lavaRapido)
		str += "Lava rápido<br>";
	if (item.mecanico)
		str += "Mecânico<br>";
	if (item.borracheiro)
		str += "Borracheiro<br>";
	if (item.caixaEletronico)
		str += "Caixa eletrônico<br>";
	if (item.semParar)
		str += "Sem parar<br>";
	if (item.viaFacil)
		str += "Via fácil<br>";
	str += "<div style=\"margin-top:5px\">";

	$.each(item.precos, function(i2, item2) {
		str += "<strong>" + item2 + "</strong><br>";
	});

	str += "<br></div>";
	str += "<a style=\"margin-top:15px\" onclick=\"selecionar('"
			+ item.nome
			+ "');\">Clique aqui para atualizar os preços dos combustíveis.</a>";
	str += "</div>";
	return str;
}

function iniciaMarker(latLng) {
	markerRef = new google.maps.Marker({
		position : latLng,
		map : map
	});
	markerRef.setDraggable(true);
	setEndereco(latLng);
	markerRef.addListener('dragend', function(e) {
		setEndereco(e.latLng);
	});
}

function setEndereco(latLng) {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'location' : latLng
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			$('#endereco').val(results[1].formatted_address);
		}
	});
}

function posicaoAtual(position) {
	map.setCenter({
		lat : position.coords.latitude,
		lng : position.coords.longitude
	});
}
function buttonMap(idButton, idWindow) {
	objWindow = document.getElementById(idWindow);
	objButton = document.getElementById(idButton);
	objWindow.style.display = 'block';
}

function setEndereco(latLng) {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode({
		'location' : latLng
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			$('#endereco').val(results[1].formatted_address);
		}
	});
}

function posicaoAtual(position) {
	map.setCenter({
		lat : position.coords.latitude,
		lng : position.coords.longitude
	});
}

function selecionar(descricao) {
	$("#janela").css('display', 'block');
	$.getJSON("/estabelecimento/" + descricao, function(data) {
		$("#nome").val(data[0].nome);
		$('#endereco').val(data[0].endereco);
		$("#bandeira").val(data[0].bandeira);
		$('#conveniencia').prop('checked', data[0].conveniencia);
		$('#alimentacao').prop('checked', data[0].alimentacao);
		$('#trocaOleo').prop('checked', data[0].trocaOleo);
		$('#lavaRapido').prop('checked', data[0].lavaRapido);
		$('#mecanico').prop('checked', data[0].mecanico);
		$('#borracheiro').prop('checked', data[0].borracheiro);
		$('#caixaEletronico').prop('checked', data[0].caixaEletronico);
		$('#semParar').prop('checked', data[0].semParar);
		$('#viaFacil').prop('checked', data[0].viaFacil);
		$.each(data[0].precos, function(i, item){
			if(item.split(' ')[0] === 'GASOLINA:')
				$('#gasolinaComum').val(item.split(' ')[3].replace(',', '.'));
			else
				$('#etanol').val(item.split(' ')[3].replace(',', '.'));
		});
		if(markerRef == null) {
			iniciaMarker({ lat: data[0].lat, lng: data[0].longi});
		} else { 
			markerRef.setPosition(data[0].lat, data[0].longi);
		}
		
	});
}