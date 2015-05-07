<html>
<head>
	<title>GeInArFa - Map</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<link rel="stylesheet" href="css/leaflet.css" />
	<link rel="stylesheet" href="css/Control.MiniMap.css" />
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	<script src="js/geoserverjson.js" type="text/javascript"></script>
	<script src="js/communicator.js" type="text/javascript"></script>
	<script src="js/leafletaction.js" type="text/javascript"></script>
	
	<script src="js/leaflet/leaflet.js"></script>
	<script src="js/leaflet/Control.FullScreen.js"></script>
	<script src="js/leaflet/Control.MiniMap.js"></script>
	
	<!-- styles specific to FullScreen extension -->
	<style type="text/css">
		#map { width: 1000px; height: 500px; border: 1px solid #ccc }
		#map:-webkit-full-screen { width: 100% !important; height: 100% !important; }
		#map:-moz-full-screen { width: 100% !important; height: 100% !important; }
		#map:full-screen { width: 100% !important; height: 100% !important; }
		.leaflet-control-zoom-fullscreen { background-image: url(img/icon-fullscreen.png); }
		.leaflet-control-zoom-fullscreen.last { margin-top: 5px }
		
		body {
			background-color: #EEE;
			text-align: center;
		}
		a:link {
			color: #000;
		}
		a:visited {
			color: #000;
		}
	</style>
</head>

<body>
	<table width="897" border="0" align="center">
		<tr>
			<td width="60%" height="91" valign="middle">
				<center><h3><img src="img/LinkedSamianWare_Schrift.png" height="50" align="middle"></h3></center>
			</td>
		</tr>
	</table>
	<center>
		<div id="map"></div>
		<em>
		<br> 
		[<a href="index.htm">Home</a>] [<a href="http://143.93.114.104/rest" target="_blank">ReST-Database</a>] [<a href="http://143.93.114.104/explorer" target="_blank">RDF-Explorer</a>]</em>
	</center>
	
	<script>

		// GeoJSON Objects filled by the Geoserver
		var findspots;
		var kilnsites;
		var exportplaces;
	
		// Get GeoJSON from Geoserver and map then on the map
		var gj = new GeoserverJSON();
		gj.load(leafletaction);
		
	</script>
</body>

</html>
