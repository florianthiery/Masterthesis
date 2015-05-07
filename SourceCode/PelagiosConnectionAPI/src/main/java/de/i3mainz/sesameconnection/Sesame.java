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

    private URL url;
    String id = "";

    /**
     * Konstruktor
     */
    public Sesame(String param) throws MalformedURLException, IOException, JDOMException {

        url = createQuery(param);
        id = getURIList(url);

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
    private URL createQuery(String param) throws MalformedURLException {

        String query = "";

        query = query + "SELECT DISTINCT ?erg WHERE {";
        query = query + "?a oa:hasBody ?erg .";
        query = query + "FILTER(REGEX(STR(?erg), \"^http://pleiades.stoa.org/places/\"))";
        query = query + "?a oa:hasTarget findspot:"+param+" .";
        query = query + "}";

        // Maskierung der URI
        query = query.replace(" ", "%20");
        query = query.replace("{", "%7B");
        query = query.replace("?", "%3F");
        query = query.replace("}", "%7D");
        query = query.replace("^", "%5E");
        query = query.replace("\"", "%22");

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
    private String getURIList(URL url) throws MalformedURLException, IOException, JDOMException {

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
        Element res2 = (Element) res.getContent(1); //result (1)
        Element res3 = (Element) res2.getContent(1); //binding (1)
        Element res4 = (Element) res3.getContent(1); //uri (1)
        String idp = res4.getContent(0).toString();
        
        //Ergebnis parsen
        String[] tmparray = idp.split("/");
        String result = tmparray[tmparray.length - 1].substring(0, tmparray[tmparray.length - 1].length() - 1);
        idp = result;

        return idp;

    }

    /**
     * Get ID
     *
     * @return
     */
    public String getID() {

        return id;

    }
}
