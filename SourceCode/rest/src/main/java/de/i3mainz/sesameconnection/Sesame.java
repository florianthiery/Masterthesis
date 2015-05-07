package de.i3mainz.sesameconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Klasse um den Triplestore anzusprechen
 *
 * @author Florian Thiery
 */
public class Sesame {

    private List<String> uris = new ArrayList<String>();
    private List<String[]> findspots = new ArrayList<String[]>();
    private URL url;
    private List<String> uri = new ArrayList<String>();

    /**
     * Konstruktor
     */
    public Sesame(String target, String param, String query) throws MalformedURLException, IOException, JDOMException {

        url = createQuery(target, param, query);
        uris = getURIList(url);

    }

    /**
     * Konstruktor
     */
    public Sesame(String target, String param, String query, String mode) throws MalformedURLException, IOException, JDOMException {

        if (mode.equals("findspots")) {
            url = createQuery(target, param, query);
            findspots = getFindspotList(url);
        }

    }

    /**
     * Get URI list of query
     *
     * @return URI query list
     */
    public List<String> getURIs() {
        return uris;
    }

    /**
     * Get URL to query the Sesame TripleStore and create SPARQL query
     *
     * @param target
     * @param param
     * @param targetquery
     * @return
     * @throws MalformedURLException
     */
    private URL createQuery(String target, String param, String targetquery) throws MalformedURLException {

        String query = "";

        if (target.equals("potter") && targetquery.equals("findspot")) {
            // Select Findspots of Potter
            query = query + "SELECT DISTINCT ?erg WHERE {";
            query = query + "{ ?erg rdf:type pleiades:Place . } UNION";
            query = query + "{ ?erg rdf:type pleiades:Location . }";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget potter:" + param + " .";
            query = query + " }";
        } else if (target.equals("potter") && targetquery.equals("fragment")) {
            // Select Fragments of Potter
            query = query + "SELECT DISTINCT ?erg WHERE { ";
            query = query + "?erg rdf:type crm:E22_Man-Made_Object .";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget potter:" + param + " .";
            query = query + " }";
        } else if (target.equals("findspot") && targetquery.equals("fragment")) {
            // Select Fragments of Findspot
            query = query + "SELECT DISTINCT ?erg WHERE { ";
            query = query + "?erg rdf:type crm:E22_Man-Made_Object .";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget findspot:" + param + " .";
            query = query + " }";
        } else if (target.equals("findspot") && targetquery.equals("potter")) {
            // Select Potter of Findspot
            query = query + "SELECT DISTINCT ?erg WHERE { ";
            query = query + "?erg rdf:type foaf:Person .";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget findspot:" + param + " .";
            query = query + " }";
        } else if (target.equals("fragment") && targetquery.equals("potter")) {
            // Select Potter of Fragment
            query = query + "SELECT DISTINCT ?erg WHERE {";
            query = query + "?erg rdf:type foaf:Person .";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget fragment:" + param + " .";
            query = query + " }";
        } else if (target.equals("fragment") && targetquery.equals("findspot")) {
            // Select Findspot of Fragment
            query = query + "SELECT DISTINCT ?erg WHERE {";
            query = query + "{ ?erg rdf:type pleiades:Place . } UNION";
            query = query + "{ ?erg rdf:type pleiades:Location . }";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget fragment:" + param + " .";
            query = query + " }";
        } else if (target.equals("fragmenttype") && targetquery.equals("findspot")) {
            // Select Findspot of Fragment
            query = query + "SELECT DISTINCT ?erg WHERE {";
            query = query + "{ ?erg rdf:type pleiades:Place . } UNION";
            query = query + "{ ?erg rdf:type pleiades:Location . }";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget ?f .";
            query = query + "?f crm:P2_has_type '" + param + "' .";
            query = query + " }";
        } else if (target.equals("potter") && targetquery.equals("findspot2")) {
            // Select Findspot of Pott
            query = query + "SELECT DISTINCT ?erg ?lat ?lon ?name ?ft WHERE {";
            query = query + "?erg pleiades:hasFeatureType ?ft .";
            query = query + "?erg gn:name ?name .";
            query = query + "?erg geo:lat ?lat .";
            query = query + "?erg geo:long ?lon .";
            query = query + "{ ?erg rdf:type pleiades:Place . } UNION";
            query = query + "{ ?erg rdf:type pleiades:Location . }";
            query = query + "?a oa:hasBody ?erg .";
            query = query + "?a oa:hasTarget potter:" + param + " .";
            query = query + " }";

        }

        // Maskierung der URI
        query = query.replace(" ", "%20");
        query = query.replace("{", "%7B");
        query = query.replace("?", "%3F");
        query = query.replace("}", "%7D");

        // Prefixes
        String prefix = "";
        prefix = prefix + "PREFIX pleiadestp:<http://pleiades.stoa.org/vocabularies/time-periods/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX time:<http://www.w3.org/2006/time#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX whois:<http://www.kanzaki.com/ns/whois#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX potter:<http://143.93.114.104/rest/samian/potters/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX pleiadesplace:<http://pleiades.stoa.org/places/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX pleiadestime:<http://pleiades.stoa.org/vocabularies/time-periods/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX geo:<http://www.w3.org/2003/01/geo/wgs84_pos#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX spatial:<http://geovocab.org/spatial#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX agent:<http://143.93.114.104/rest/samian/agents#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX dcterms:<http://purl.org/dc/terms/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX gn:<http://www.geonames.org/ontology#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX pleiadespt:<http://pleiades.stoa.org/vocabularies/place-types/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX oa:<http://www.openannotation.or4g/ns/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX osgeo:<http://data.ordnancesurvey.co.uk/ontology/geometry/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX anno:<http://143.93.114.104/rest/samian/annotations#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX pleiades:<http://pleiades.stoa.org/places/vocab#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX fragment:<http://143.93.114.104/rest/samian/fragments/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX skos:<http://www.w3.org/2004/02/skos/core#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX findspot:<http://143.93.114.104/rest/samian/findspots/>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX geovocab:<http://geovocab.org/spatial#>" + "%0A"; //Prexix + Enter
        prefix = prefix + "PREFIX crm:<http://purl.org/NET/crm-owl#>" + "%0A"; //Prexix + Enter
        prefix = prefix.replace(" ", "%20");
        prefix = prefix.replace(":", "%3A");
        prefix = prefix.replace("<", "%3C");
        prefix = prefix.replace("/", "%2F");
        prefix = prefix.replace("#", "%23");
        prefix = prefix.replace(">", "%3E");
        // Triplestore und SPARQL Parameter
        String triplestore_url = "http://143.93.114.104/openrdf-workbench/repositories/samian/";
        String query_properties = "query?action=exec&queryLn=SPARQL&query=";
        //String query_properties2 = "&limit=100&infer=true&";
        String query_properties2 = "&infer=true&";
        URL url = new URL(triplestore_url + query_properties + prefix + query + query_properties2);
        return url;
    }

    /**
     * Get List of URIs als result of the SPARQL query
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException
     */
    private List<String> getURIList(URL url) throws MalformedURLException, IOException, JDOMException {

        // Verbindungsaufbau zu veschlüsselter Seite
        String login = "samian";
        String password = "sigubep13";
        String loginPassword = login + ":" + password;
        String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Authorization", "Basic " + encoded);

        // Response lesen
        String inputXML = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
            inputXML = inputXML + line;
        }
        br.close();

        // Response bearbeiten
        int start = inputXML.indexOf("<sparql");
        int ende = inputXML.indexOf("</head>");
        String prefix = "<sparql xmlns='http://www.w3.org/2005/sparql-results#' xmlns:q='http://www.openrdf.org/schema/qname#'>";
        inputXML = inputXML.substring(0, start) + prefix + inputXML.substring(ende + 7);

        // convert String to XML
        SAXBuilder builder = new SAXBuilder();
        Reader in2 = new StringReader(inputXML);
        Document document = (Document) builder.build(in2);
        in2.close();

        // read xml
        Element rootNode = document.getRootElement(); //sparql
        Element res = (Element) rootNode.getContent(1); //results

        // read uri Tag
        int j = 1;
        while (j < res.getContentSize()) {
            Element res2 = (Element) res.getContent(j); //result (1)
            Element res3 = (Element) res2.getContent(1); //binding (1)
            Element res4 = (Element) res3.getContent(1); //uri (1)
            uri.add(res4.getContent(0).toString());
            j += 2;
        }

        return uri;

    }

    /**
     * Get List of Findspots with attributes (name,typ,lat,lon) als result of the SPARQL query
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException
     */
    private List<String[]> getFindspotList(URL url) throws MalformedURLException, IOException, JDOMException {

        List<String[]> l2 = new ArrayList<String[]>();

        try {

            // Verbindungsaufbau zu veschlüsselter Seite
            String login = "samian";
            String password = "sigubep13";
            String loginPassword = login + ":" + password;
            String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            // Response lesen
            String inputXML = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                inputXML = inputXML + line;
            }
            br.close();

            // Response bearbeiten
            int start = inputXML.indexOf("<sparql");
            int ende = inputXML.indexOf("</head>");
            String prefix = "<sparql xmlns='http://www.w3.org/2005/sparql-results#' xmlns:q='http://www.openrdf.org/schema/qname#'>";
            inputXML = inputXML.substring(0, start) + prefix + inputXML.substring(ende + 7);

            // convert String to XML
            SAXBuilder builder = new SAXBuilder();
            Reader in2 = new StringReader(inputXML);
            Document document = (Document) builder.build(in2);
            in2.close();

            // read xml
            Element rootNode = document.getRootElement(); //sparql
            Element res = (Element) rootNode.getContent(1); //results


            List<String[]> l = new ArrayList<String[]>();

            // read uri Tag
            int j = 1;
            while (j < res.getContentSize()) {
                Element res2 = (Element) res.getContent(j); //result (1)

                int k = 1;
                String[] array = new String[4];
                array[0] = "";
                array[1] = "";
                array[2] = "";
                array[3] = "";
                while (k < res2.getContentSize()) {

                    String tmp = "";
                    Element res3 = (Element) res2.getContent(k); //result (1)

                    if (k == 1) {
                        Element res4 = (Element) res3.getContent(1); //binding (1)
                        tmp = res4.getContent(0).toString(); //Literal (Lon)
                        String[] tmparray = tmp.split(" ");
                        String result = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
                        uri.add(result);
                        array[3] = result;
                    } else if (k == 5) { //name
                        Element res4 = (Element) res3.getContent(1); //binding (1)
                        tmp = res4.getContent(0).toString(); //Literal (Name)
                        String[] tmparray = tmp.split(":");
                        String result2 = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
                        String result = result2.substring(1, result2.length());
                        result = result.replace(" ", "_");
                        uri.add(result);
                        array[0] = result;
                    } else if (k == 7) {
                        Element res4 = (Element) res3.getContent(1); //binding (1)
                        tmp = res4.getContent(0).toString(); //Name URL
                        String[] tmparray = tmp.split("/");
                        String result = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
                        uri.add(result);
                        array[1] = result;
                    } else if (k == 9) {
                        Element res4 = (Element) res3.getContent(1); //binding (1)
                        tmp = res4.getContent(0).toString(); //Literal (Lat)
                        String[] tmparray = tmp.split(" ");
                        String result = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
                        uri.add(result);
                        array[2] = result;
                    }
                    k += 2;
                }
                l.add(array);
                j += 2;
            }

            // Liste mit je einem Array (name,typ,lat,lon) erzeugen je nach Produktionsart

            for (int i = 0; i < l.size();) {

                String[] array = new String[4];
                if ((i + 2) < l.size()) {
                    if (l.get(i)[0].equals(l.get(i + 1)[0]) && l.get(i + 1)[0].equals(l.get(i + 2)[0])) { //wenn drei --> production
                        array[0] = l.get(i)[0];
                        array[1] = "production";
                        array[2] = l.get(i)[2];
                        array[3] = l.get(i)[3];
                        l2.add(array);
                        i = i + 3;
                    } else if (l.get(i)[0].equals(l.get(i + 1)[0])) { //immer zwei da mind. settlement + findspot ffg. production --> zwei not kilnsite
                        array[0] = l.get(i)[0];
                        array[1] = "settlement";
                        array[2] = l.get(i)[2];
                        array[3] = l.get(i)[3];
                        l2.add(array);
                        i = i + 2;
                    }
                } else {
                    if (l.get(i)[0].equals(l.get(i + 1)[0])) { //immer zwei da mind. settlement + findspot ffg. production --> zwei not kilnsite
                        array[0] = l.get(i)[0];
                        array[1] = "settlement";
                        array[2] = l.get(i)[2];
                        array[3] = l.get(i)[3];
                        l2.add(array);
                        i = i + 2;
                    }
                }

                if (i >= l.size()) {
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return l2;
    }

    /**
     * Get URI List Items
     *
     * @return
     */
    public List<String> getItems() {

        // outputList
        List<String> items = new ArrayList<String>();

        for (int i = 0; i < uri.size(); i++) {
            String tmp = uri.get(i);
            String[] tmparray = tmp.split("/");
            String result = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
            items.add(result);
        }

        return items;

    }

    /**
     * Get Findspot as Array List
     * @return 
     */
    public List<String[]> getFindspots() {
        return findspots;
    }
}
