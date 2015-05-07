function GeoserverJSON() {
	
	var isLoaded_findspots = false;
	var isLoaded_kilnsite = false;
	var isLoaded_export = false;
	
	var url_findspots = "http://143.93.114.104/getfeature/findspots?param=all";
	var url_kilnsite = "http://143.93.114.104/getfeature/findspots?param=kilnsite";
	var url_export = "http://143.93.114.104/getfeature/findspots?param=export";
	
	this.load = function(callback) {
		if (isLoaded_findspots) {
		} else {
			IO.read(url_findspots,onloadFindspots,callback);
		}
		if (isLoaded_kilnsite) {
		} else {
			IO.read(url_kilnsite,onloadKilnsites,callback);
		}
		if (isLoaded_export) {
		} else {
			IO.read(url_export,onloadExportPlaces,callback);
		}
	}
	
	var onloadFindspots = function(geoJSON,callback) {
		isLoaded_findspots = true;
		findspots = geoJSON;
		console.log('All Findspots loaded as WFS');
		if (isLoaded_findspots==true && isLoaded_kilnsite==true && isLoaded_export==true) {
			callback();
		}
	}
	
	var onloadKilnsites = function(geoJSON,callback) {
		isLoaded_kilnsite = true;
		kilnsites = geoJSON;
		console.log('Kilnsites loaded as WFS');
		if (isLoaded_findspots==true && isLoaded_kilnsite==true && isLoaded_export==true) {
			callback();
		}
	}
	
	var onloadExportPlaces = function(geoJSON,callback) {
		isLoaded_export = true;
		exportplaces = geoJSON;
		console.log('Export places loaded as WFS');
		if (isLoaded_findspots==true && isLoaded_kilnsite==true && isLoaded_export==true) {
			callback();
		}
	}
	

}
