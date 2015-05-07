function init() {
    $('#navi').hide();
    $('#rep').hide();
    $('#list').hide();
    $('#pelagios').hide();
    $('#web').hide();
    $('#close').hide();
}

function reset_div() {
    $('#navi').hide();
    $('#rep').hide();
    $('#list').hide();
    $('#web').hide();
    $('#pelagios').hide();
    $('#close').hide();
    $('#web_content').html("");
    $('#list_content').html("");
    $('#pelagios_content').html("");
}

function showdata(content) {

    reset_div();

    $('#navi').show();
    var uri = uri_prefix_samian + "findspots/" + content;
    $('#navi').html("<b>Findspot: " + String(content) + "</b>" + " | " + "<a href=\"#\" onclick=\"web_content('" + uri + "')\">Findspot(Resource)</a>" + " | " + "<a href=\"#\" onclick=\"reset_findspotlayer()\">Reset(Findspots)</a>");

    $('#rep').show();
    $('#list').show();
    $('#web').show();
    $('#pelagios').show();
    $('#close').show();

    findspot(content);
    potterfragment(content);
    pelagios(content);
    web_content(uri_prefix_samian + "findspots/" + content);
}

function findspot(content) {
    readFindspot(uri_prefix_samian + "findspots/" + content + ".ttl", setFindspotText);
}

function potterfragment(content) {
    readList(uri_prefix_samian + "findspots/" + content + ".exp", setListText);
}

function pelagios(content) {
    readPelagios(uri_prefix_server + "PelagiosConnectionAPI/PelagiosData?Findspot=" + content + "&type=xml", setPelagiosText);
}

function readFindspot(url, callback, info) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'text/plain',
        success: function(msg) {
            callback(msg);
        },
        error: function(err) {
            alert(err.toString());
        }
    });
}

function readList(url, callback, info) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'xml',
        success: function(xml) {
            callback(xml);
        },
        error: function(err) {
            alert(err.toString());
        }
    });
}

function readPelagios(url, callback, info) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'xml',
        success: function(xml) {
            callback(xml);
        },
        error: function(err) {
            alert(err.toString());
        }
    });
}

function readPotterGeoJSON(url, callback, info) {
    $.ajax({
        beforeSend: function(req) {
            req.setRequestHeader("Accept", "json");
        },
        type: "GET",
        url: url,
        dataType: 'json',
        success: function(json) {
            callback(json);
        },
        error: function(err) {
            alert(err.toString());
        }
    });
}

function readFindspotGeoJSON(url, callback, info) {
    $.ajax({
        beforeSend: function(req) {
            req.setRequestHeader("Accept", "json");
        },
        type: "GET",
        url: url,
        dataType: 'json',
        success: function(json) {
            callback(json);
        },
        error: function(err) {
            alert(err.toString());
        }
    });
}

function setFindspotText(text) {

    var replacer = new RegExp("\n", "g");
    var replacer2 = new RegExp("\r", "g");
    text = text.replace(replacer, "<br>");
    text = text.replace(replacer2, "");

    $('#rep_content').html(text);

}

function setListText(xml) {

    var potters = xml.getElementsByTagName("potter");
    var fragments = xml.getElementsByTagName("fragment");

    var htmltag = "<b>Potter:</b>";
    htmltag = htmltag + "<ul style=\"list-style-position:outside\">";

    for (var i = 0; i < potters.length; i++) {
        var uri = potters[i].getAttribute("uri");
        htmltag = htmltag + "<li><a href=\"#\" onclick=\"web_content('" + uri + "','potter')\">" + uri + "</a>&nbsp;[<a href=\"" + uri + "\" target=\"_blank\">Link</a>]";
        var uri_frag = potters[i].getElementsByTagName("uri");
        htmltag = htmltag + "<ul>";
        for (var j = 0; j < uri_frag.length; j++) {
            var uri_f = uri_frag[j].childNodes[0].nodeValue;
            htmltag = htmltag + "<li>" + uri_f + "&nbsp;[<a href=\"" + uri_f + "\" target=\"_blank\">Link</a>]</li>";
        }
        htmltag = htmltag + "</ul>";
        htmltag = htmltag + "</li>";
    }

    htmltag = htmltag + "</ul>";

    htmltag = htmltag + "<b>Fragments:</b>";
    htmltag = htmltag + "<ul>";

    for (var k = 0; k < fragments.length; k++) {
        var uri = fragments[k].getAttribute("uri");
        htmltag = htmltag + "<li>" + uri + "&nbsp;[<a href=\"" + uri + "\" target=\"_blank\">Link</a>]";
        var uri_pott = fragments[k].getElementsByTagName("uri");
        htmltag = htmltag + "<ul>";
        for (var l = 0; l < uri_pott.length; l++) {
            var uri_p = uri_pott[l].childNodes[0].nodeValue;
            htmltag = htmltag + "<li><a href=\"#\" onclick=\"web_content('" + uri_p + "','potter')\">" + uri_p + "</a>&nbsp;[<a href=\"" + uri_p + "\" target=\"_blank\">Link</a>]</li>";
        }
        htmltag = htmltag + "</ul>";
        htmltag = htmltag + "</li>";
    }

    htmltag = htmltag + "</ul>";

    $('#list_content').html(htmltag);

}

function setPotterMap(jsonIn) {

    map.removeLayer(markers);
    markers = null;
    markers = L.markerClusterGroup();

    findspotLayer = L.geoJson(jsonIn, {
        
		pointToLayer: function(feature, latlng) {
            
			if (feature.properties.name.indexOf("__") != -1) {
				var marker = L.marker(latlng, {icon: markerIconPotterLocation});
			} else {
				var marker = L.marker(latlng, {icon: markerIconPotter});
			}

            var popupContent = "";

            if (feature.properties && feature.properties.name) {
                var replacer = new RegExp(" ", "g");
                var content = String(feature.properties.name);
                var content2 = content.replace(replacer, "_");
                popupContent += feature.properties.name;
                popupContent += "<br>";
                popupContent += "<a href=\"javascript:showdata('" + content2 + "');\">Info</a>";
            }

            marker.bindPopup(popupContent);
            markers.addLayer(marker);

            return L.marker(latlng, {icon: markerIconFindspot});
        }
    });
    
    map.addLayer(markers);

}

function setFindspotMap(jsonIn) {

    markers = null;
    markers = L.markerClusterGroup();
    
    findspotLayer = L.geoJson(jsonIn, {
        
		pointToLayer: function(feature, latlng) {

            if (feature.properties.name.indexOf("__") != -1) {
				var marker = L.marker(latlng, {icon: markerIconLocation});
			} else {
				var marker = L.marker(latlng, {icon: markerIconFindspot});
			}

            var popupContent = "";

            if (feature.properties && feature.properties.name) {
                var replacer = new RegExp(" ", "g");
                var content = String(feature.properties.name);
                var content2 = content.replace(replacer, "_");
                popupContent += feature.properties.name;
                popupContent += "<br>";
                popupContent += "<a href=\"javascript:showdata('" + content2 + "');\">Info</a>";
            }

            marker.bindPopup(popupContent);
            markers.addLayer(marker);

            return L.marker(latlng, {icon: markerIconFindspot});
        }
    });
    
    map.addLayer(markers);

}

function reset_findspotlayer() {

    map.removeLayer(markers);
    markers = null;
    markers = L.markerClusterGroup();

    readPotterGeoJSON(uri_prefix_server + "getfeature/findspots?param=all", setFindspotMap);

}

function setPelagiosText(xml) {

    var links = xml.getElementsByTagName("link");
    var htmltag = "<b>PELAGIOS Links:</b>";
    htmltag = htmltag + "<ul>";

    for (var i = 0; i < links.length; i++) {
        var uri = xml.getElementsByTagName("link")[i].firstChild.data;
        htmltag = htmltag + "<li><a href=\"#\" onclick=\"web_content('" + uri + "')\">" + uri + "</a>&nbsp;[<a href=\"" + uri + "\" target=\"_blank\">Link</a>]</li>";
    }

    var htmltag = htmltag + "<ul>";

    $('#pelagios_content').html(htmltag);

}

function web_content(uri, type) {
    
	$('#web_content').html("<iframe src=\"" + uri + "\" frameBorder=\"0\" width=\"825\" height=\"280\"></iframe>");
    if (type == "potter") {
        var splitarray = uri.split("/");
        var name = splitarray[splitarray.length - 1];
        readPotterGeoJSON(uri_prefix_samian + "potters/" + name + ".exp", setPotterMap);
    }
	
}


