package de.i3mainz.html;

import java.util.ArrayList;

/**
 * Klasse zum Erzeugen von HTMl Repräsentationen
 *
 * @author Florian Thiery
 */
public class HTML {

    private String pageURL;
    private ArrayList<String> resourcesList = new ArrayList<String>();
    private ArrayList<String> resourcesNamesList = new ArrayList<String>();
    private ArrayList<String> resourceList = new ArrayList<String>();
    private ArrayList<String> resourceNamesList = new ArrayList<String>();
    private String potterName;
    private String findspotName;
    private String findspotLocation;
    private boolean findspotKilnsite;
    private String fragmentID;
    private String fragmentDie;
    private String fragmentPotform;
    private int fragmentNumber;

    /**
     * Konstructor
     */
    public HTML() {
    }

    /**
     * Creates HTML Header Tag
     *
     * @param title
     * @return Header[String]
     */
    public String Header(String title) {

        String head = "";

        head = head + "<!DOCTYPE html>\n";
        head = head + "<html lang=\"en\">\n";
        head = head + "<head>\n";
        head = head + "\t<title>" + title + "</title>\n";
        head = head + "\t<meta charset=\"utf-8\" />\n";
        head = head + "\t<meta name=\"description\" content=\"SamianReST Interface - Masterthesis Florian Thiery\" />\n";
        head = head + "\t<meta name=\"author\" content=\"Florian Thiery [geinarfa@florian-thiery.de]\" />\n";
        head = head + "\t<style type=\"text/css\">\n";
        head = head + "\ta:link { color: #00F; }\n";
        head = head + "\ta:visited { color: #00F; }\n";
        head = head + "\t</style>\n";
        head = head + "</head>";

        return head;
    }

    /**
     * Creates HTML Body Tag
     *
     * @return
     */
    public String Body() {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><img src=\"http://143.93.114.104/app/img/lswd_s.jpg\"></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h2>Collection:</h2></center>\n";
        body = body + "\t<center><h2><a href=\"" + pageURL + "\">" + pageURL + "</a></h2></center>\n";
        if (resourcesList.size() > 0) {
            body = body + "\t<center><h2>Resources:</h2></center>\n";
        }
        for (int i = 0; i < resourcesList.size(); i++) {
            body = body + "\t<center><h2>" + resourcesNamesList.get(i) + ": <a href=\"" + resourcesList.get(i) + "\">" + resourcesList.get(i) + "</a></h2></center>\n";
        }
        if (resourceList.size() > 0) {
            body = body + "\t<center><h2>Resource:</h2></center>\n";
        }
        for (int i = 0; i < resourceList.size(); i++) {
            body = body + "\t<center><h2>" + resourceNamesList.get(i) + ": <a href=\"" + resourceList.get(i) + "\">" + resourceList.get(i) + "</a></h2></center>\n";
        }
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<p align=\"center\"><i>&copy; 2013 by Heinz/Mees/<a href=\"http://www.florian-thiery.de\">Thiery</a> (<a href=\"mailto:geinarfa@florian-thiery.de\">geinarfa@florian-thiery.de</a>) | Masterthesis [GeInArFa]</i></p>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML Body Potter Tag
     *
     * @return
     */
    public String BodyPotter() {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><img src=\"http://143.93.114.104/app/img/lswd_s.jpg\"></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h3>Collection: <a href=\"" + pageURL + "\">" + pageURL + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resources: " + resourcesNamesList.get(0) + ": <a href=\"" + resourcesList.get(0) + "\">" + resourcesList.get(0) + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resource: " + resourceNamesList.get(0) + ": <a href=\"" + resourceList.get(0) + "\">" + resourceList.get(0) + "</a></h3></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h1><font color=\"red\">Resource: " + potterName + "</font></h1></center>\n";
        body = body + "\t<center><h2>PotterName | " + potterName + "</h2></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<p align=\"center\"><i>&copy; 2013 by Heinz/Mees/<a href=\"http://www.florian-thiery.de\">Thiery</a> (<a href=\"mailto:geinarfa@florian-thiery.de\">geinarfa@florian-thiery.de</a>) | Masterthesis [GeInArFa]</i></p>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML Body Findspot Tag
     *
     * @return
     */
    public String BodyFindspot() {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><img src=\"http://143.93.114.104/app/img/lswd_s.jpg\"></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h3>Collection: <a href=\"" + pageURL + "\">" + pageURL + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resources: " + resourcesNamesList.get(0) + ": <a href=\"" + resourcesList.get(0) + "\">" + resourcesList.get(0) + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resource: " + resourceNamesList.get(0) + ": <a href=\"" + resourceList.get(0) + "\">" + resourceList.get(0) + "</a></h3></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h1><font color=\"red\">Resource: " + findspotName + "</font></h1></center>\n";
        body = body + "\t<center><h2>Name | " + findspotName + "</h2></center>\n";
        body = body + "\t<center><h2>Location (WGS84) | " + findspotLocation + "</h2></center>\n";
        body = body + "\t<center><h2>isKilnsite | " + findspotKilnsite + "</h2></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<p align=\"center\"><i>&copy; 2013 by Heinz/Mees/<a href=\"http://www.florian-thiery.de\">Thiery</a> (<a href=\"mailto:geinarfa@florian-thiery.de\">geinarfa@florian-thiery.de</a>) | Masterthesis [GeInArFa]</i></p>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML Body Fragment Tag
     *
     * @return
     */
    public String BodyFragment() {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><img src=\"http://143.93.114.104/app/img/lswd_s.jpg\"></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h3>Collection: <a href=\"" + pageURL + "\">" + pageURL + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resources: " + resourcesNamesList.get(0) + ": <a href=\"" + resourcesList.get(0) + "\">" + resourcesList.get(0) + "</a></h3></center>\n";
        body = body + "\t<center><h3>Resource: " + resourceNamesList.get(0) + ": <a href=\"" + resourceList.get(0) + "\">" + resourceList.get(0) + "</a></h3></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h1><font color=\"red\">Resource: " + fragmentID + "</font></h1></center>\n";
        body = body + "\t<center><h2>ID | " + fragmentID + "</h2></center>\n";
        body = body + "\t<center><h2>Anzahl | " + fragmentNumber + "</h2></center>\n";
        body = body + "\t<center><h2>Die | " + fragmentDie + "</h2></center>\n";
        body = body + "\t<center><h2>Potform | " + fragmentPotform + "</h2></center>\n";
        body = body + "\t<center><h2><font color=\"green\">Potter: " + potterName + "</font></h2></center>\n";
        body = body + "\t<center><h3>URI | <a href=\"" + pageURL + "/potters/" + potterName + "\">" + pageURL + "/potters/" + potterName + "</a></h3></center>\n";
        body = body + "\t<center><h3>PotterName | " + potterName + "</h3></center>\n";
        body = body + "\t<center><h2><font color=\"green\">Findspot: " + findspotName + "</font></h2></center>\n";
        body = body + "\t<center><h3>URI | <a href=\"" + pageURL + "/findspots/" + findspotName + "\">" + pageURL + "/findspots/" + findspotName + "</a></h3></center>\n";
        body = body + "\t<center><h3>Name | " + findspotName + "</h3></center>\n";
        body = body + "\t<center><h3>Loaction (WGS84) | " + findspotLocation + "</h3></center>\n";
        body = body + "\t<center><h3>isKilnsite | " + findspotKilnsite + "</h3></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<p align=\"center\"><i>&copy; 2013 by Heinz/Mees/<a href=\"http://www.florian-thiery.de\">Thiery</a> (<a href=\"mailto:geinarfa@florian-thiery.de\">geinarfa@florian-thiery.de</a>) | Masterthesis [GeInArFa]</i></p>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML Body Fragment Tag
     *
     * @return
     */
    public String BodyFragmentWarning() {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><img src=\"http://143.93.114.104/app/img/lswd_s.jpg\"></center>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<br/>\n";
        body = body + "\t<center><h2>Collection:</h2></center>\n";
        body = body + "\t<center><h2><a href=\"" + pageURL + "\">" + pageURL + "</a></h2></center>\n";
        if (resourcesList.size() > 0) {
            body = body + "\t<center><h2>Resources:</h2></center>\n";
        }
        for (int i = 0; i < resourcesList.size(); i++) {
            body = body + "\t<center><h2>" + resourcesNamesList.get(i) + ": <a href=\"" + resourcesList.get(i) + "\">" + resourcesList.get(i) + "</a></h2></center>\n";
        }

        body = body + "\t<br/>\n";
        body = body + "\t<center><font color=\"red\"><h1>WARNING</h1></font></center>\n";
        body = body + "\t<center><h2>The resource is to big to represent it as HTML into the browser.</h2></center>\n";
        body = body + "\t<center><h2>If you want to get the data as XML it can be took a while.</h2></center>\n";
        body = body + "\t<center><h2>Download overview textfile: <a href=\"http://143.93.114.104/downloads/Fragments.txt\" target=\"_blank\">http://143.93.114.104/downloads/Fragments.txt</a></h2></center>\n";

        body = body + "\t<br/>\n";
        body = body + "\t<hr width=\"80%\"/>\n";
        body = body + "\t<p align=\"center\"><i>&copy; 2013 by Heinz/Mees/<a href=\"http://www.florian-thiery.de\">Thiery</a> (<a href=\"mailto:geinarfa@florian-thiery.de\">geinarfa@florian-thiery.de</a>) | Masterthesis [GeInArFa]</i></p>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML Body Error-Tag
     *
     * @return
     */
    public String BodyError(String error) {

        String body = "";

        body = body + "\n<body>\n";
        body = body + "\t<h1>" + error + "</h1>\n";
        body = body + "</body>";

        return body;
    }

    /**
     * Creates HTML End Tag
     *
     * @return
     */
    public String Footer() {

        return "\n</html>";

    }

    public String Representations(String resURI) {

        String rep = "";

        rep = rep + "<center>";
        rep = rep + "<p>";
        rep = rep + "\t<hr width=\"80%\"/>";
        rep = rep + "\t<b>Repräsentation: </b>";
        rep = rep + "[<a href=\"" + resURI + ".xml\" target=\"_blank\">XML</a>] ";
        rep = rep + " [<a href=\"" + resURI + ".json\" target=\"_blank\">JSON</a>] ";
        rep = rep + " [<a href=\"" + resURI + ".ttl\" target=\"_blank\">Turtle</a>] ";
        rep = rep + " [<a href=\"" + resURI + ".rdf\" target=\"_blank\">RDF/XML</a>]";
        rep = rep + "</p>";
        rep = rep + "</center>";

        return rep;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public void setResourcesURL(String resourcesURL, String resource) {
        resourcesList.add(resourcesURL);
        resourcesNamesList.add(resource);
    }

    public void setResourceURL(String resourceURL, String resource) {
        resourceList.add(resourceURL);
        resourceNamesList.add(resource);
    }

    public void setPotterName(String potterName) {
        this.potterName = potterName;
    }

    public void setFindspotName(String findspotName) {
        this.findspotName = findspotName;
    }

    public void setFindspotLocation(String findspotLocation) {
        this.findspotLocation = findspotLocation;
    }

    public void setFindspotKilnsite(boolean findspotKilnsite) {
        this.findspotKilnsite = findspotKilnsite;
    }

    public void setFragmentDie(String fragmentDie) {
        this.fragmentDie = fragmentDie;
    }

    public void setFragmentNumber(int fragmentNumber) {
        this.fragmentNumber = fragmentNumber;
    }

    public void setFragmentPotform(String fragmentPotform) {
        this.fragmentPotform = fragmentPotform;
    }

    public void setFragmentID(String fragmentID) {
        this.fragmentID = fragmentID;
    }
}
