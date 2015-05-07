<html>
<head>
<title>GeInArFa | Time Explorer</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.js"></script>

    <script type="text/javascript">
        
        var host = "http://143.93.114.104/";
        //var host = "http://localhost:8084/";
    
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
        
        function sendData(url, triple, callback) {
            
            $.ajax({
                    type: "GET",
                    contentType: 'text/plain',
                    data: {triple:triple},
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
        
        function readText(url, callback, info) {
            
            $.ajax({
                    type: "GET",
                    url: url,
                    dataType: 'text/plain',
                    success: function(text) {
                            //callback(xml);
                    },
                    error: function(err) {
                            alert(err.toString());
                    }
            });
        
        }
        
        function sendTriple() {
            
            var s = document.getElementById("subject");
            var p = document.getElementById("predicate");
            var o = document.getElementById("object");
            var sval = s.options[s.selectedIndex].value;
            var pval = p.options[p.selectedIndex].value;
            var oval = o.options[o.selectedIndex].value;
            
            var triple = "site:"+sval+" time:"+pval+" site:"+oval;
            
            sendData(host+"timeexplorer/sendTriple",triple,reloadSite);
            
        }
        
        function resetTriple() {
            
            readList(host+"timeexplorer/resetTable",reloadSite);
            
        }
        
        function setSubjectBox(xml) {

            var entities = xml.getElementsByTagName("entity");
        
            for (var i=0; i<entities.length; i++) {
                var text = entities[i].childNodes[0].nodeValue;
                var value = entities[i].childNodes[0].nodeValue;
                op = new Option(text, value, false, false);
                document.choise.subject.options[document.choise.subject.length] = op;
            }
        
        }
        
        function setRelationBox(xml) {

            var entities = xml.getElementsByTagName("relation");
        
            for (var i=0; i<entities.length; i++) {
                var text = entities[i].childNodes[0].nodeValue;
                var value = entities[i].childNodes[0].nodeValue;
                op = new Option(text, value, false, false);
                document.choise.predicate.options[document.choise.predicate.length] = op;
            }
        
        }
        
        function setObjectBox(xml) {

            var entities = xml.getElementsByTagName("entity");
        
            for (var i=0; i<entities.length; i++) {
                var text = entities[i].childNodes[0].nodeValue;
                var value = entities[i].childNodes[0].nodeValue;
                op = new Option(text, value, false, false);
                document.choise.object.options[document.choise.object.length] = op;
            }
        
        }
        
        function reloadSite() {
            //setTimeline(host+"timeexplorer/getTimeline");
            setTimeline(host+"timeexplorer/getSimile");
            setTriples(host+"timeexplorer/getStatus");
            //setTriples(host+"timeexplorer/getTurtle");
            readText(host+"timeexplorer/getTurtle"); // bei getTurtle wird eine tmp.ttl geschrieben
            setRDFgraph("http://rdf.florian-thiery.de/?url=http://samian:sigubep13@143.93.114.104/share/tmp.ttl");
        }
        
        function setTimeline(uri) {
    
            $('#timeline_content').html("<iframe src=\"" + uri + "\" frameBorder=\"0\" width=\"775\" height=\"550\"></iframe>");

        }
        
        function setRDFgraph(uri) {
    
            $('#rdfgraph_content').html("<iframe src=\"" + uri + "\" frameBorder=\"0\" width=\"440\" height=\"500\"></iframe>");

        }
        
        function setTriples(uri) {
    
            $('#triples_content').html("<iframe src=\"" + uri + "\" frameBorder=\"0\" width=\"420\" height=\"445\"></iframe>");

        }
            
    </script>
</head>

<body>
    
    <br><center><img src="lte.png" height="150"></center>
    <br><hr width="750"/><br>
    
    <table width="98%" border="0">
        <tr><td>
        <center>
        <form name="choise" action="" align="center">
        <b>Relationen &auml;ndern: </b>
        <select name="subject" id="subject" width="100"></select>
        <select name="predicate" id="predicate" width="100"></select>
        <select name="object" id="object" width="100"></select>
        <input type="button" value="sendTriple" onclick="sendTriple()">
        <input type="button" value="reset" onclick="resetTriple()">
    </form>
    </center>
    </td></tr></table>
    
    <div class="rdfgraph" id="rdfgraph" style="float: left;">
        <span id="rdfgraph_content"></span>
    </div>
    <div class="timeline" id="timeline" style="float: left;">
        <span id="timeline_content"></span>
    </div>
    <div class="triples" id="triples" style="float: left;">
        <span id="triples_content"></span>
    </div>
    
    
    
    
    <script type="text/javascript">
        readList(host+"timeexplorer/getEntities",setSubjectBox);
        readList(host+"timeexplorer/getRelations",setRelationBox);
        readList(host+"timeexplorer/getEntities",setObjectBox);
        setTimeline(host+"timeexplorer/getSimile");
        //setTimeline(host+"timeexplorer/getTimeline");
        setTriples(host+"timeexplorer/getStatus");
        //setTriples(host+"timeexplorer/getTurtle");
        readText(host+"timeexplorer/getTurtle"); // bei getTurtle wird eine tmp.ttl geschrieben
        setRDFgraph("http://rdf.florian-thiery.de/?url=http://samian:sigubep13@143.93.114.104/share/tmp.ttl");
    </script>

</body>
</html>