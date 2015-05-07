var IO = {};

IO.read = function(url, callback, info) {
	$.ajax({
		beforeSend: function(req) {
			req.setRequestHeader("Accept","json");
		},
		dataType: "json",
		url: url,
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		success: function (xml) {
			callback(xml,info);
		}
	});
}