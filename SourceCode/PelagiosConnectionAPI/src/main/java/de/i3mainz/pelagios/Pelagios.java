package de.i3mainz.pelagios;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Florian Thiery
 */
public class Pelagios {

    private String DatasetXML = "";
    private String TitleXML = "";
    private String DatasetTitleXML = "";
    private List<String> DatasetURI = new ArrayList<String>();
    private List<String> Titles = new ArrayList<String>();
    private List<String> PelagiosURIs = new ArrayList<String>();
    public HashMap<String, String> test = new HashMap<String, String>();
    public HashMap<String, String> test2 = new HashMap<String, String>();

    /**
     * Konstruktor
     */
    public Pelagios() {
    }

    /**
     * Get Pelagios URI from PleiadesID
     *
     * @param PleiadesID
     * @return List [Pelagios URIs]
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException
     */
    public List<String> getPelagiosURIs(String PleiadesID) throws MalformedURLException, IOException, JDOMException {


        DatasetXML = ReadDatasetRDFgetSPARQLqueryDatasets(PleiadesID);
        DatasetURI = ParseDatasetXMLgetDatasetID(DatasetXML);
        PelagiosURIs = ParseAnnotationsJSONgetAnnotationURI(DatasetURI, PleiadesID);

        DatasetTitleXML = ReadDatasetRDFgetSPARQLqueryDatasetsTitle(PleiadesID);
        ParseDatasetXMLgetDatasetIDTitle(DatasetTitleXML);

        return PelagiosURIs;

    }

    /**
     * Get Connection Titles from PleiadesID
     *
     * @param PleiadesID
     * @return List [Pelagios URIs]
     * @throws MalformedURLException
     * @throws IOException
     * @throws JDOMException
     */
    public List<String> getPelagiosConnections(String PleiadesID) throws MalformedURLException, IOException, JDOMException {

        TitleXML = ReadDatasetRDFgetSPARQLqueryTitles(PleiadesID);
        Titles = ParseDatasetXMLgetTitle(TitleXML);

        return Titles;

    }

    /**
     * Read Pelagios Dataset RDF, query it with SPARQL for Dataset XML
     *
     * @param PelagiosID
     * @return QueryResult (DatasetXML)
     * @throws MalformedURLException
     * @throws IOException
     */
    private String ReadDatasetRDFgetSPARQLqueryDatasets(String PelagiosID) throws MalformedURLException, IOException {

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // read PELAGIOS URI - Dataset (with API)
        String url = "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PelagiosID + "/datasets.rdf";
        InputStream in = new URL(url).openStream();

        // read the RDF/XML file
        model.read(in, null); // null base URI, since model URIs are absolute
        in.close();

        // Create a new query
        //String queryString = "SELECT * WHERE { ?s ?p ?o }";
        String queryString =
                "prefix dcterms: <http://purl.org/dc/terms/>"
                + "prefix void: <http://rdfs.org/ns/void#>"
                + "prefix foaf: <http://xmlns.com/foaf/0.1/>"
                + "SELECT ?dataset WHERE { "
                + "?dataset a void:Dataset"
                + "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results	
        String datasetXML = ResultSetFormatter.asXMLString(results);
        System.out.println(datasetXML); //consolelog

        // Important - free up resources used running the query
        qe.close();

        return datasetXML;

    }

    /**
     * Read Pelagios Dataset RDF, query it with SPARQL for Titles XML
     *
     * @param PelagiosID
     * @return QueryResult (DatasetXML)
     * @throws MalformedURLException
     * @throws IOException
     */
    private String ReadDatasetRDFgetSPARQLqueryTitles(String PelagiosID) throws MalformedURLException, IOException {

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // read PELAGIOS URI - Dataset (with API)
        String url = "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PelagiosID + "/datasets.rdf";
        InputStream in = new URL(url).openStream();

        // read the RDF/XML file
        model.read(in, null); // null base URI, since model URIs are absolute
        in.close();

        // Create a new query
        //String queryString = "SELECT * WHERE { ?s ?p ?o }";
        String queryString =
                "prefix dcterms: <http://purl.org/dc/terms/>"
                + "SELECT ?title WHERE { "
                + "?dataset dcterms:title ?title"
                + "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results	
        String titleXML = ResultSetFormatter.asXMLString(results);
        System.out.println(titleXML); //consolelog

        // Important - free up resources used running the query
        qe.close();

        return titleXML;

    }

    /**
     * Read Pelagios Dataset RDF, query it with SPARQL for Dataset XML
     *
     * @param PelagiosID
     * @return QueryResult (DatasetXML)
     * @throws MalformedURLException
     * @throws IOException
     */
    private String ReadDatasetRDFgetSPARQLqueryDatasetsTitle(String PelagiosID) throws MalformedURLException, IOException {

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // read PELAGIOS URI - Dataset (with API)
        String url = "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PelagiosID + "/datasets.rdf";
        InputStream in = new URL(url).openStream();

        // read the RDF/XML file
        model.read(in, null); // null base URI, since model URIs are absolute
        in.close();

        // Create a new query
        //String queryString = "SELECT * WHERE { ?s ?p ?o }";
        String queryString =
                "prefix dcterms: <http://purl.org/dc/terms/>"
                + "prefix void: <http://rdfs.org/ns/void#>"
                + "prefix foaf: <http://xmlns.com/foaf/0.1/>"
                + "SELECT ?dataset ?title WHERE { "
                + "?dataset a void:Dataset ."
                + "?dataset dcterms:title ?title ."
                + "}";

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results	
        String datasetTitleXML = ResultSetFormatter.asXMLString(results);
        System.out.println(datasetTitleXML); //consolelog

        // Important - free up resources used running the query
        qe.close();

        return datasetTitleXML;

    }

    /**
     * Parse Pelagios Dataset XML, get Dataset ID
     *
     * @param DatasetXML
     * @return List [DatasetID]
     * @throws JDOMException
     * @throws IOException
     */
    private List<String> ParseDatasetXMLgetDatasetID(String DatasetXML) throws JDOMException, IOException {

        // XML Paster initialisieren
        SAXBuilder builder = new SAXBuilder();

        // remove attribut
        DatasetXML = DatasetXML.replace(" xmlns=\"http://www.w3.org/2005/sparql-results#\"", "");

        // convert String to XML
        Reader in2 = new StringReader(DatasetXML);
        Document document = (Document) builder.build(in2);
        in2.close();

        // read xml
        Element rootNode = document.getRootElement(); //sparql
        List result = rootNode.getChildren("results"); //results

        // outputList
        List<String> uri = new ArrayList<String>();

        for (int i = 0; i < result.size(); i++) {

            Element resultsnode = (Element) result.get(i);
            List resultlist = resultsnode.getChildren("result"); //result

            for (int j = 0; j < resultlist.size(); j++) {

                Element resultlistnode = (Element) resultlist.get(j);
                List bindinglist = resultlistnode.getChildren("binding"); //binding

                for (int k = 0; k < bindinglist.size(); k++) {

                    Element bindinglistnode = (Element) bindinglist.get(k);

                    // binding-attribut:name, tag:uri
                    if (bindinglistnode.getAttributeValue("name").toString().equals("dataset")) {
                        uri.add(bindinglistnode.getChildText("uri"));
                    }

                }

            }

        }

        //consolelog
        for (int i = 0; i < uri.size(); i++) {
            System.out.println(uri.get(i));
        }
        return uri;

    }

    /**
     * Parse Pelagios Dataset XML, get Dataset ID
     *
     * @param DatasetXML
     * @return List [DatasetID]
     * @throws JDOMException
     * @throws IOException
     */
    private List<String> ParseDatasetXMLgetTitle(String TitleXML) throws JDOMException, IOException {

        // XML Paster initialisieren
        SAXBuilder builder = new SAXBuilder();

        // remove attribut
        TitleXML = TitleXML.replace(" xmlns=\"http://www.w3.org/2005/sparql-results#\"", "");

        // convert String to XML
        Reader in2 = new StringReader(TitleXML);
        Document document = (Document) builder.build(in2);
        in2.close();

        // read xml
        Element rootNode = document.getRootElement(); //sparql
        List result = rootNode.getChildren("results"); //results

        // outputList
        List<String> title = new ArrayList<String>();

        for (int i = 0; i < result.size(); i++) {

            Element resultsnode = (Element) result.get(i);
            List resultlist = resultsnode.getChildren("result"); //result

            for (int j = 0; j < resultlist.size(); j++) {

                Element resultlistnode = (Element) resultlist.get(j);
                List bindinglist = resultlistnode.getChildren("binding"); //binding

                for (int k = 0; k < bindinglist.size(); k++) {

                    Element bindinglistnode = (Element) bindinglist.get(k);

                    // binding-attribut:name, tag:uri
                    if (bindinglistnode.getAttributeValue("name").toString().equals("title")) {
                        title.add(bindinglistnode.getChildText("literal"));
                        //test2.put(TitleXML, TitleXML)
                    }

                }

            }

        }

        //consolelog
        for (int i = 0; i < title.size(); i++) {
            System.out.println(title.get(i));
        }
        return title;

    }

    /**
     * Parse Pelagios Dataset XML, get Dataset ID
     *
     * @param DatasetXML
     * @return List [DatasetID]
     * @throws JDOMException
     * @throws IOException
     */
    private List<String> ParseDatasetXMLgetDatasetIDTitle(String DatasetTitleXML) throws JDOMException, IOException {

        // XML Paster initialisieren
        SAXBuilder builder = new SAXBuilder();

        // remove attribut
        DatasetXML = DatasetXML.replace(" xmlns=\"http://www.w3.org/2005/sparql-results#\"", "");

        // convert String to XML
        Reader in2 = new StringReader(DatasetXML);
        Document document = (Document) builder.build(in2);
        in2.close();

        // read xml
        Element rootNode = document.getRootElement(); //sparql
        List result = rootNode.getChildren("results"); //results

        // outputList
        List<String> uri = new ArrayList<String>();

        for (int i = 0; i < result.size(); i++) {

            Element resultsnode = (Element) result.get(i);
            List resultlist = resultsnode.getChildren("result"); //result

            for (int j = 0; j < resultlist.size(); j++) {

                Element resultlistnode = (Element) resultlist.get(j);
                List bindinglist = resultlistnode.getChildren("binding"); //binding

                for (int k = 0; k < bindinglist.size(); k++) {

                    Element bindinglistnode = (Element) bindinglist.get(k);

                    // binding-attribut:name, tag:uri
                    if (bindinglistnode.getAttributeValue("name").toString().equals("dataset")) {
                        uri.add(bindinglistnode.getChildText("uri"));
                    }

                }

            }

        }

        //consolelog
        for (int i = 0; i < uri.size(); i++) {
            System.out.println(uri.get(i));
        }
        return uri;

    }

    /**
     *
     * @param DatasetURI
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private List<String> ParseAnnotationsJSONgetAnnotationURI(List<String> DatasetURI, String PleiadesURI) throws MalformedURLException, IOException {

        System.out.println("");

        // outputList
        List<String> title = new ArrayList<String>();

        for (int i = 0; i < DatasetURI.size(); i++) {

            // read PELAGIOS URI - Dataset (with API)
            String url = DatasetURI.get(i).toString()
                    + "/annotations.json?forPlace=http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesURI;
            InputStream in = new URL(url).openStream();

            // convert inputStream to String
            String JSONstring = inputStreamToString(in);
            in.close();

            // initialize JSON Parser
            JSONParser parser = new JSONParser();

            try {

                // parse JSON
                Object obj = parser.parse(JSONstring);
                JSONObject jsonObject = (JSONObject) obj;

                //String mJsonArray = (String) jsonObject.get("annotations");
                JSONArray msg = (JSONArray) jsonObject.get("annotations");

                for (int j = 0; j < msg.size(); j++) {
                    JSONObject o = (JSONObject) msg.get(j);
                    String uri_string = (String) o.get("hasTarget");
                    title.add(uri_string);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return title;

    }

    /**
     * Methot to convert InputStream to String
     *
     * @param in
     * @return String
     * @throws IOException
     */
    private String inputStreamToString(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }
}
