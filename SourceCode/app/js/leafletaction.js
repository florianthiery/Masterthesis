function leafletaction() {
	
	//Karten-Layer
	
		//Cloudmade Karte
		var cloudmadeUrl = 'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png',
			cloudmadeAttribution = 'Map data &copy; 2011 OpenStreetMap contributors, Imagery &copy; \n2011 CloudMade',
			cloudmade = new L.TileLayer(cloudmadeUrl, {minZoom: 0, maxZoom: 18, attribution: cloudmadeAttribution});
			
		//OSM Karte
		var osmTilesUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
			OSMAttribution = 'Map data &copy; OpenStreetMap',
			osmTiles = new L.TileLayer(osmTilesUrl, {minZoom: 0, maxZoom: 18, attribution: OSMAttribution});
			
		//ESRI World_Topo_Map
		var esriUrlTopo = 'http://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}',
			ESRIAttribution = 'ESRI &mdash; ' +
							  'Esri, DeLorme, NAVTEQ, TomTom, Intermap, iPC, USGS, FAO, NPS, NRCAN,<br>GeoBase, Kadaster NL, Ordnance Survey, Esri Japan, METI, Esri China (Hong Kong), and the GIS User Community',
			esriTopo = new L.TileLayer(esriUrlTopo, {minZoom: 0, maxZoom: 8, attribution: ESRIAttribution});
			
		//ESRI World_Physical_Map
		var esriUrlPhysical = 'http://server.arcgisonline.com/ArcGIS/rest/services/World_Physical_Map/MapServer/tile/{z}/{y}/{x}',
			ESRIAttribution = 'ESRI &mdash; ' + 'Source: US National Park Service',
			esriPhysical = new L.TileLayer(esriUrlPhysical, {minZoom: 0, maxZoom: 8, attribution: ESRIAttribution});
			
		//Stamen Watercolor
		var stamenWatercolorUrl = 'http://{s}.tile.stamen.com/watercolor/{z}/{x}/{y}.jpg',
			StamenAttribution = 'Map tiles by <a href="http://stamen.com">Stamen Design</a>, ' +
					'<a href="http://creativecommons.org/licenses/by/3.0">CC BY 3.0</a> &mdash; ' +
					'Map data &copy; OpenStreetMap',
			stamenWatercolor = new L.TileLayer(stamenWatercolorUrl, {minZoom: 3, maxZoom: 16, attribution: StamenAttribution});
			
		// Pleiades Layer //
		
		var terrain = L.tileLayer(
			'http://api.tiles.mapbox.com/v3/isawnyu.map-p75u7mnj/{z}/{x}/{y}.png', {
			attribution: "ISAW, 2012"
        });

		var streets = L.tileLayer(
			'http://api.tiles.mapbox.com/v3/isawnyu.map-zr78g89o/{z}/{x}/{y}.png', {
			attribution: "ISAW, 2012"
        });

		var imperium = L.tileLayer(
			'http://pelagios.dme.ait.ac.at/tilesets/imperium//{z}/{x}/{y}.png', {
			attribution: 'Tiles: <a href="http://pelagios.dme.ait.ac.at/maps/greco-roman/about.html">Pelagios</a>, 2012; Data: NASA, OSM, Pleiades, DARMC',
			maxZoom: 11
        });
		
	//Icon Definition
		
		var defaultIcon = L.icon({
			iconUrl: 'img/marker-icon.png',
			iconAnchor: [9, 30],
			popupAnchor: [0, -30]
		});
		
		var redIcon = L.icon({
			iconUrl: 'img/marker-icon_red.png',
			iconAnchor: [9, 30],
			popupAnchor: [0, -30]
		});
		
		var greenIcon = L.icon({
			iconUrl: 'img/marker-icon_green.png',
			iconAnchor: [9, 30],
			popupAnchor: [0, -30]
		});
		
		var greenLeaf = L.icon({
			iconUrl: 'img/leaf-green.png',
			iconSize:     [38, 95], // size of the icon
			iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
			popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		
		var redLeaf = L.icon({
			iconUrl: 'img/leaf-red.png',
			iconSize:     [38, 95], // size of the icon
			iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
			popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		
		var orangeLeaf = L.icon({
			iconUrl: 'img/leaf-orange.png',
			iconSize:     [38, 95], // size of the icon
			iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
			popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
		});
		
		var orangeCircle = {
			radius: 6,
			fillColor: "#ff7800",
			color: "#000",
			weight: 1,
			opacity: 1,
			fillOpacity: 1
		};
		
	//Click Event on Marker
		
		function onEachFeature(feature, layer) {
			var input = "";
			input += "Findspot: " + feature.properties.name;
			input += "<br>";
			input += "Kilnsite: " + feature.properties.kilnsite;
			input += "<br>";
			input += "Coordinates: " + feature.properties.lat + " | " + feature.properties.lon;
			layer.bindPopup(input);
		};
		
	//Overlay Layer

		//Geoserver WFS aus geoserverjson.js
		var findspots2 = L.geoJson(findspots, {

			pointToLayer: function (feature, latlng) {
				return L.marker(latlng, {icon: defaultIcon});
			},

			onEachFeature: onEachFeature
		});
		
		var kilnsites2 = L.geoJson(kilnsites, {

			pointToLayer: function (feature, latlng) {
				return L.marker(latlng, {icon: redIcon});
			},

			onEachFeature: onEachFeature
		});
		
		var exportplaces2 = L.geoJson(exportplaces, {

			pointToLayer: function (feature, latlng) {
				return L.marker(latlng, {icon: greenIcon});
			},

			onEachFeature: onEachFeature
		});
		
		var findspotscircle = L.geoJson(findspots, {

			pointToLayer: function (feature, latlng) {
				return L.circleMarker(latlng, orangeCircle);
			},

			onEachFeature: onEachFeature
		});
		
		//Geoserver WMS findspot
		var findspotsWMS = new L.TileLayer.WMS("http://143.93.114.104/geoserver/Samian/wms", {
			layers: 'Samian:findspot',
			format: 'image/png',
			transparent: true,
			attribution: ""
		});
		
	//Layer der Karte hinzufügen	
		
		var map = new L.Map('map', {
			center: new L.LatLng(50, 8), 
			zoom: 3,
			layers: [terrain, kilnsites2, exportplaces2]
		});
		
	//Layer Control
		
		var baseLayers = {
			"ISAW: Terrain&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": terrain,
			"Pelagios: Imperium&nbsp;&nbsp;": imperium,
			"ESRI: Topography&nbsp;&nbsp;&nbsp;&nbsp;":esriTopo,
			"Stamen: Watercolor&nbsp;&nbsp;": stamenWatercolor,
			"ISAW: Streets&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": streets,
			"Cloudmade: Fresh&nbsp;&nbsp;&nbsp;&nbsp;": cloudmade
		};
		
		var overlays = {
			"FindspotsWMS&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": findspotsWMS,
			"FindspotsWFS(marker)": findspots2,
			"KilnsiteWFS(marker)&nbsp;": kilnsites2,
			"ExportWFS(marker)&nbsp;&nbsp;&nbsp;": exportplaces2,
			"FindspotsWFS(circle)": findspotscircle
		};

		// add layer control to the map
		L.control.layers(baseLayers, overlays).addTo(map);
		
		// create fullscreen control
		var fullScreen = new L.Control.FullScreen();
		// add fullscreen control to the map
		map.addControl(fullScreen);

		// detect fullscreen toggling
		map.on('enterFullscreen', function(){
			if(window.console) window.console.log('enterFullscreen');
		});
		map.on('exitFullscreen', function(){
			if(window.console) window.console.log('exitFullscreen');
		});
		
	// Minimap
		var streetsMini = L.tileLayer(
			'http://api.tiles.mapbox.com/v3/isawnyu.map-zr78g89o/{z}/{x}/{y}.png', {
			attribution: "ISAW, 2012"
        });
		var miniMap = new L.Control.MiniMap(streetsMini, {zoomLevelOffset: -5}).addTo(map);
		
}