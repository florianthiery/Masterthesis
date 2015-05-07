package de.i3mainz.createOutput;

import de.i3mainz.dbutils.event.EventManager;
import de.i3mainz.dbutils.event.Findspot;
import de.i3mainz.dbutils.event.Fragment;
import de.i3mainz.dbutils.event.Pleiadesmatch;
import de.i3mainz.dbutils.event.Potter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasse zum Erzeugen von RDF/XML Elementen
 *
 * @author Florian Thiery
 */
public class CreateRDFXML {

    private EventManager mgr = new EventManager();
    private String url_string = "";
    private List<String> findspot_list = new ArrayList<String>();
    private List<String> fragment_list = new ArrayList<String>();
    private List<String> potter_list = new ArrayList<String>();
    private List<Pleiadesmatch> match_list = new ArrayList<Pleiadesmatch>();
    private String error = "";

    /**
     * Konstruktor
     */
    public CreateRDFXML(String baseURI) {
        this.url_string = baseURI;
    }

    /**
     * Get Potter as WHOIS Turtle
     *
     * @param potterName
     * @return
     */
    public String getWHOIS(String potterName) {

        String whois = "";
        String PotterNameWhitespace = potterName; // findspot Name mit Whitespace und Underscore (for Database)
        PotterNameWhitespace = PotterNameWhitespace.replace("_", " ");

        try {

            // Repräsentation als RDF

            String potter = mgr.getPotterByName(PotterNameWhitespace).getName();

            whois = whois + "<?xml version=\"1.0\"?>\n";
            whois = whois + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"http://localhost:8084/rest/samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"http://localhost:8084/rest/samian/findspots/\" xmlns:agent=\"http://localhost:8084/rest/samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\">\n";

            whois = whois + "  <foaf:Person rdf:about=\"potter:" + potterName + "\">\n";
            whois = whois + "    <foaf:name>" + PotterNameWhitespace + "</foaf:name>\n";
            whois = whois + "    <foaf:gender>male</foaf:gender>\n";
            whois = whois + "    <whois:career>\n";
            whois = whois + "      <whois:Job>\n";
            whois = whois + "        <rdfs:label>Potter</rdfs:label>\n";
            whois = whois + "      </whois:Job>\n";
            whois = whois + "     </whois:career>\n";
            whois = whois + "     <time:inside rdf:resource=\"pleiadestp:roman\" />\n";
            whois = whois + "   </foaf:Person>\n";

            // Repräsentation der Links zu Fragments und Findspots als RDF

            //getFragmentsFindspotsPotter(potterName);
            fragment_list.clear();
            findspot_list.clear();
            fragment_list = mgr.getFragmentStringByPotterName(PotterNameWhitespace);
            findspot_list = mgr.getFindspotsStringByPotterName(PotterNameWhitespace);

            if (findspot_list.size() > 0 || fragment_list.size() > 0) {

                for (int i = 0; i < findspot_list.size(); i++) {
                    String findspot = findspot_list.get(i).toString();
                    findspot = findspot.replace("__", "#");
                    findspot = findspot.replace(" ", "_");
                    findspot = findspot.replace("#", "__");
                    whois = whois + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + potterName + "-" + findspot + "\">\n";
                    whois = whois + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                    whois = whois + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                    whois = whois + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                    whois = whois + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspot + "\" />\n";
                    whois = whois + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potterName + "\" />\n";
                    whois = whois + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                    whois = whois + "  </oa:Annotation>\n";
                }

                for (int i = 0; i < fragment_list.size(); i++) {
                    whois = whois + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + potterName + "-" + fragment_list.get(i).toString() + "\">\n";
                    whois = whois + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                    whois = whois + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                    whois = whois + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                    whois = whois + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragment_list.get(i).toString() + "\" />\n";
                    whois = whois + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potterName + "\" />\n";
                    whois = whois + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                    whois = whois + "  </oa:Annotation>\n";
                }

                whois = whois + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
                whois = whois + "    <foaf:name>Florian Thiery</foaf:name>\n";
                whois = whois + "  </foaf:person>\n";

                whois = whois + "</rdf:RDF>\n";

            }

            return whois;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");
            return "<error>no potter with this name available</error>";
        }

    }
    
    public String getPlaiades(String findspotName) {

        String pleiades = "";
        String findspotNameWhitespace = findspotName; // findspot Name mit Whitespace und Underscore (for Database)
        findspotNameWhitespace = findspotNameWhitespace.replace("__", "#");
        findspotNameWhitespace = findspotNameWhitespace.replace("_", " ");
        findspotNameWhitespace = findspotNameWhitespace.replace("#", "__");

        try {

            //Get all Findspots with Prefix findspotName
            List<Findspot> findspots_list = mgr.getFindspotsLocationsByFindspotName(findspotNameWhitespace);

            //Ist das Angefragte ein Place?
            boolean isPlace = false;
            for (int i = 0; i < findspots_list.size(); i++) {
                if (!findspots_list.get(i).getName().toString().contains("__")) {
                    isPlace = true;
                    break;
                }
            }

            Findspot findspot = mgr.getFindspotByName(findspotNameWhitespace);

            if (isPlace == true) { //Place

                pleiades = pleiades + "<?xml version=\"1.0\"?>\n";
                pleiades = pleiades + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:spatial=\"http://geovocab.org/spatial#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"" + url_string + "samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"" + url_string + "samian/findspots/\" xmlns:agent=\"" + url_string + "samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\" xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\" xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\" xmlns:gn=\"http://www.geonames.org/ontology#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:pleiades=\"http://pleiades.stoa.org/places/vocab#\" xmlns:osgeo=\"http://data.ordnancesurvey.co.uk/ontology/geometry/\" xmlns:pleiadestime=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:pleiadespt=\"http://pleiades.stoa.org/vocabularies/place-types/\">\n";
                pleiades = pleiades + "  <spatial:Feature rdf:about=\"" + url_string + "samian/findspots/" + findspotName + "\">\n";
                pleiades = pleiades + "    <rdfs:label>" + findspot.getName().toString() + "</rdfs:label>\n";
                pleiades = pleiades + "    <spatial:C rdf:resource=\"" + url_string + "samian/findspots/" + findspotName + "\" />" + "\n";
                pleiades = pleiades + "    <rdfs:comment>An ancient place.</rdfs:comment>" + "\n";
                pleiades = pleiades + "    <foaf:primaryTopicOf>" + "\n";
                pleiades = pleiades + "      <pleiades:Place rdf:about=\"" + url_string + "samian/findspots/" + findspotName + "\">\n";
                pleiades = pleiades + "        <rdf:type rdf:resource=\"http://www.w3.org/2004/02/skos/core#Concept\" />\n";
                if (findspot.getKilnsite() == true) {
                    pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/production\" />\n";
                    pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
                    pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
                } else {
                    pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
                    pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
                }
                for (int i = 0; i < findspots_list.size(); i++) {
                    if (findspots_list.get(i).getName().toString().contains("__")) {
                        String temp = findspots_list.get(i).getName().toString();
                        temp = temp.replace(" ", "_");
                        pleiades = pleiades + "        <pleiades:hasLocation rdf:resource=\"" + url_string + "samian/findspots/" + temp + "\" />\n";
                    }
                }
                pleiades = pleiades + "        <gn:name>" + findspot.getName().toString() + "</gn:name>\n";
                pleiades = pleiades + "        <geo:lat rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">" + String.valueOf(findspot.getLat()) + "</geo:lat>\n";
                pleiades = pleiades + "        <geo:long rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + String.valueOf(findspot.getLon()) + "</geo:long>\n";
                pleiades = pleiades + "        <osgeo:asGeoJSON>{\"type\": \"Point\", \"coordinates\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}</osgeo:asGeoJSON>\n";
                pleiades = pleiades + "        <osgeo:asWKT>POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")</osgeo:asWKT>\n";
                pleiades = pleiades + "        <time:during rdf:resource=\"http://pleiades.stoa.org/vocabularies/time-periods/roman\" />\n";
                pleiades = pleiades + "      </pleiades:Place>\n";
                pleiades = pleiades + "    </foaf:primaryTopicOf>\n";
                pleiades = pleiades + "  </spatial:Feature>\n";

                // Repräsentation der Links zu Fragments und Findspots als RDF

                //getFragmentsPottersFindspot(findspotNameWhitespace);
                fragment_list.clear();
                findspot_list.clear();
                potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);

                if (potter_list.size() > 0 || fragment_list.size() > 0) {

                    for (int i = 0; i < potter_list.size(); i++) {
                        String potter = potter_list.get(i).toString();
                        potter = potter.replace(" ", "_");
                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + findspotName + "-" + potter + "\">\n";
                        pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potter + "\" />\n";
                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                        pleiades = pleiades + "  </oa:Annotation>\n";
                    }

                    for (int i = 0; i < fragment_list.size(); i++) {
                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + findspotName + "-" + fragment_list.get(i).toString() + "\">\n";
                        pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragment_list.get(i).toString() + "\" />\n";
                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.</dcterms:description>\n";
                        pleiades = pleiades + "  </oa:Annotation>\n";
                    }

                    pleiades = pleiades + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
                    pleiades = pleiades + "    <foaf:name>Florian Thiery</foaf:name>\n";
                    pleiades = pleiades + "  </foaf:person>\n";

                }

                // Repräsentation der Links zu Pleiades

                try {

                    Pleiadesmatch pm = mgr.getPleiadesIDByName(findspotNameWhitespace);

                    pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + findspotName + "-" + pm.getPleiadesPlace().toString() + "\">\n";
                    pleiades = pleiades + "    <oa:motivatedBy rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                    pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#Mees\" />\n";
                    pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                    pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://pleiades.stoa.org/places/" + pm.getPleiadesPlace().toString() + "\" />\n";
                    pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                    pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                    pleiades = pleiades + "  </oa:Annotation>\n";

                    pleiades = pleiades + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#Mees\">\n";
                    pleiades = pleiades + "    <foaf:name>Allard Mees</foaf:name>\n";
                    pleiades = pleiades + "  </foaf:person>\n";

                } catch (Exception e) {
                    pleiades = pleiades;
                }

                pleiades = pleiades + "</rdf:RDF>\n";

            } else { // Location

                pleiades = pleiades + "<?xml version=\"1.0\"?>\n";
                pleiades = pleiades + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"" + url_string + "samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"" + url_string + "samian/findspots/\" xmlns:agent=\"" + url_string + "samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\" xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\" xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\" xmlns:gn=\"http://www.geonames.org/ontology#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:pleiades=\"http://pleiades.stoa.org/places/vocab#\" xmlns:osgeo=\"http://data.ordnancesurvey.co.uk/ontology/geometry/\" xmlns:pleiadestime=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:pleiadespt=\"http://pleiades.stoa.org/vocabularies/place-types/\">\n";
                pleiades = pleiades + "  <pleiades:Location rdf:about=\"" + url_string + "samian/findspots/" + findspot.getName().toString() + "\">";
                pleiades = pleiades + "    <rdf:type rdf:resource=\"http://www.w3.org/2004/02/skos/core#Concept\" />\n";
                if (findspot.getKilnsite() == true) {
                    pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/production\" />\n";
                    pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
                    pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
                } else {
                    pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
                    pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
                }
                pleiades = pleiades + "    <gn:name>" + findspot.getName().toString() + "</gn:name>\n";
                pleiades = pleiades + "    <geo:lat rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">" + String.valueOf(findspot.getLat()) + "</geo:lat>\n";
                pleiades = pleiades + "    <geo:long rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + String.valueOf(findspot.getLon()) + "</geo:long>\n";
                pleiades = pleiades + "    <osgeo:asGeoJSON>{\"type\": \"Point\", \"coordinates\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}</osgeo:asGeoJSON> ";
                pleiades = pleiades + "    <osgeo:asWKT>POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")</osgeo:asWKT>\n";
                pleiades = pleiades + "    <time:during rdf:resource=\"http://pleiades.stoa.org/vocabularies/time-periods/roman\" />\n";
                pleiades = pleiades + "  </pleiades:Location>\n";

                // Repräsentation der Links zu Fragments und Findspots als RDF

                //getFragmentsPottersFindspot(findspotNameWhitespace);
                fragment_list.clear();
                findspot_list.clear();
                potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);

                if (potter_list.size() > 0 || fragment_list.size() > 0) {

                    for (int i = 0; i < potter_list.size(); i++) {
                        String potter = potter_list.get(i).toString();
                        potter = potter.replace(" ", "_");
                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + findspotName + "-" + potter + "\">\n";
                        pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potter + "\" />\n";
                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                        pleiades = pleiades + "  </oa:Annotation>\n";
                    }

                    for (int i = 0; i < fragment_list.size(); i++) {
                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + findspotName + "-" + fragment_list.get(i).toString() + "\">\n";
                        pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragment_list.get(i).toString() + "\" />\n";
                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.</dcterms:description>\n";
                        pleiades = pleiades + "  </oa:Annotation>\n";
                    }

                    pleiades = pleiades + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
                    pleiades = pleiades + "    <foaf:name>Florian Thiery</foaf:name>\n";
                    pleiades = pleiades + "  </foaf:person>\n";

                }

                pleiades = pleiades + "</rdf:RDF>\n";

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");
            return "<error>no findspot with this name available</error>";
        }

        return pleiades;
    }

    public String getCIDOCRM(int fragmentID) {

        String frag = "";

        try {

            // Datenbank Data

            Hashtable<String, String> data = mgr.getPotterFindspotDataByFragmentID(fragmentID);

            String potter = data.get("potterName"); 
            String die = data.get("fragmentDie");
            String number = data.get("fragmentNumber");
            String potform = data.get("fragmentPotform");
            
            String findspotWhitespace = data.get("findspotName");
            String findspot = findspotWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
            findspot = findspot.replace("__", "#");
            findspot = findspot.replace(" ", "_");
            findspot = findspot.replace("#", "__");
            
            frag = frag + "<?xml version=\"1.0\"?>\n";
            frag = frag + "<rdf:RDF xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"http://localhost:8084/rest/samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"http://localhost:8084/rest/samian/findspots/\" xmlns:agent=\"http://localhost:8084/rest/samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\" xmlns:crm=\"http://purl.org/NET/crm-owl#\">\n";

            // Repräsentation als RDF
            
            frag = frag + "  <crm:E22_Man-Made_Object rdf:about=\"" + url_string + "samian/fragments/" + fragmentID + "\">\n";
            frag = frag + "    <crm:P102_has_title>Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers</crm:P102_has_title>\n";
            frag = frag + "    <crm:P2_has_type>"+potform+"</crm:P2_has_type>\n";
            frag = frag + "    <crm:P2_has_type>Samian Terra Sigillata</crm:P2_has_type>\n"; 
            frag = frag + "    <crm:P44_has_condition>\n";
            frag = frag + "      <E3_Condition_State>\n";
            frag = frag + "        <crm:P2_has_type rdf:resource=\"http://arachne.uni-koeln.de/vocabulary/condition#fragment-e\"/>\n";
            frag = frag + "      </E3_Condition_State>\n";
            frag = frag + "    </crm:P44_has_condition>\n";
            frag = frag + "    <crm:P45_consists_of>Ton</crm:P45_consists_of>\n";
            frag = frag + "    <crm:P56_bears_feature>"+die+"</crm:P56_bears_feature>\n";
            frag = frag + "    <crm:P57_has_number_of_parts>"+number+"</crm:P57_has_number_of_parts>\n";
            frag = frag + "    <rdfs:label>Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers</rdfs:label>\n";
            frag = frag + "  </crm:E22_Man-Made_Object>\n";

            // Repräsentation der Links zu Fragments und Findspots als RDF

            String findspotNameWhitespace = findspot;
            String findspotName = findspotNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
            findspotName = findspotName.replace("__", "#");
            findspotName = findspotName.replace(" ", "_");
            findspotName = findspotName.replace("#", "__");
                frag = frag + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + fragmentID + "-" + findspotName + "\">\n";
                frag = frag + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                frag = frag + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                frag = frag + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                frag = frag + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
                frag = frag + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragmentID + "\" />\n";
                frag = frag + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
                frag = frag + "  </oa:Annotation>\n";

            potter = potter.replace(" ", "_");
                frag = frag + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + fragmentID + "-" + potter + "\">\n";
                frag = frag + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
                frag = frag + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
                frag = frag + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
                frag = frag + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potter + "\" />\n";
                frag = frag + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragmentID + "\" />\n";
                frag = frag + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has stamped the fragment.</dcterms:description>\n";
                frag = frag + "  </oa:Annotation>\n";

            frag = frag + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
            frag = frag + "    <foaf:name>Florian Thiery</foaf:name>\n";
            frag = frag + "  </foaf:person>\n";

            frag = frag + "</rdf:RDF>\n";

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");
            return "<error>no fragment with this id available</error>";
        }

        return frag;

    }
    
// nicht benutzt    

//    public String getFragmentsList() {
//
//        String frag = "";
//
//        try {
//
//            List<Fragment> fragments = mgr.getFragments();
//
//            frag = frag + "<?xml version=\"1.0\"?>\n";
//            frag = frag + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"http://localhost:8084/rest/samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"http://localhost:8084/rest/samian/findspots/\" xmlns:agent=\"http://localhost:8084/rest/samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\">\n";
//
//            for (int i = 0; i < fragments.size(); i++) {
//
//                int fragmentID = fragments.get(i).getId();
//                findspot_list.clear();
//                potter_list.clear();
//
//                //getPottersFindspotsFragment(fragmentID);
//                Fragment fragment = mgr.getFragmentByID(fragmentID);
//
//                //for (int j = 0; j < findspot_list.size(); j++) {
//                    String findspotNameWhitespace = fragment.getFindspot().getName().toString();
//                String findspotName = findspotNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
//                findspotName = findspotName.replace("__", "#");
//                findspotName = findspotName.replace(" ", "_");
//                findspotName = findspotName.replace("#", "__");
//                    frag = frag + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + fragmentID + "-" + findspotName + "\">\n";
//                    frag = frag + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
//                    frag = frag + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
//                    frag = frag + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                    frag = frag + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspotName + "\" />\n";
//                    frag = frag + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragmentID + "\" />\n";
//                    frag = frag + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                    frag = frag + "  </oa:Annotation>\n";
//                //}
//
//                //for (int j = 0; j < potter_list.size(); j++) {
//                    String potter = fragment.getPotter().getName().toString();
//                potter = potter.replace(" ", "_");
//                    frag = frag + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + fragmentID + "-" + potter + "\">\n";
//                    frag = frag + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
//                    frag = frag + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
//                    frag = frag + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                    frag = frag + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potter + "\" />\n";
//                    frag = frag + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragmentID + "\" />\n";
//                    frag = frag + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has stamped the fragment.</dcterms:description>\n";
//                    frag = frag + "  </oa:Annotation>\n";
//                //}
//
//                frag = frag + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
//                frag = frag + "    <foaf:name>Florian Thiery</foaf:name>\n";
//                frag = frag + "  </foaf:person>\n";
//
//            }
//
//            frag = frag + "</rdf:RDF>\n";
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error");
//            return "<error>no fragment with this id available</error>";
//        }
//
//        return frag;
//
//    }
    
    //    /**
//     * Get Potter as WHOIS Turtle
//     *
//     * @param potterName
//     * @return
//     */
//    public String getWHOISPotters() {
//
//        String whois = "";
//
//        try {
//
//            List<Potter> potter = mgr.getPotters();
//
//            whois = whois + "<?xml version=\"1.0\"?>\n";
//            whois = whois + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"http://localhost:8084/rest/samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"http://localhost:8084/rest/samian/findspots/\" xmlns:agent=\"http://localhost:8084/rest/samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\">\n";
//
//            for (int i = 0; i < potter.size(); i++) {
//
//                String PotterNameWhitespace = potter.get(i).getName().toString();
//                String potterName = PotterNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
//                potterName = potterName.replace(" ", "_");
//
//                whois = whois + "  <foaf:Person rdf:about=\"potter:" + potterName + "\">\n";
//                whois = whois + "    <foaf:name>" + potterName + "</foaf:name>\n";
//                whois = whois + "    <foaf:gender>male</foaf:gender>\n";
//                whois = whois + "    <whois:career>\n";
//                whois = whois + "      <whois:Job>\n";
//                whois = whois + "        <rdfs:label>Potter</rdfs:label>\n";
//                whois = whois + "      </whois:Job>\n";
//                whois = whois + "     </whois:career>\n";
//                whois = whois + "     <time:inside rdf:resource=\"pleiadestp:roman\" />\n";
//                whois = whois + "   </foaf:Person>\n";
//
//                // Repräsentation der Links zu Fragments und Findspots als RDF
//
//                //getFragmentsFindspotsPotter(potterName);
//                fragment_list.clear();
//                findspot_list.clear();
//                fragment_list = mgr.getFragmentStringByPotterName(PotterNameWhitespace);
//                findspot_list = mgr.getFindspotsStringByPotterName(PotterNameWhitespace);
//
//                if (findspot_list.size() > 0 || fragment_list.size() > 0) {
//
//                    for (int j = 0; j < findspot_list.size(); j++) {
//                        String findspot = findspot_list.get(j).toString();
//                        findspot = findspot.replace(" ", "_");
//                        findspot = findspot.replace("__", "#");
//                        findspot = findspot.replace("_", " ");
//                        findspot = findspot.replace("#", "__");
//                        whois = whois + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + potterName + "-" + findspot + "\">\n";
//                        whois = whois + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
//                        whois = whois + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
//                        whois = whois + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                        whois = whois + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/findspots/" + findspot + "\" />\n";
//                        whois = whois + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potterName + "\" />\n";
//                        whois = whois + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                        whois = whois + "  </oa:Annotation>\n";
//                    }
//
//                    for (int j = 0; j < fragment_list.size(); j++) {
//                        whois = whois + "  <oa:Annotation rdf:about=\"http://localhost:8084/rest/samian/annotations#" + potterName + "-" + fragment_list.get(j).toString() + "\">\n";
//                        whois = whois + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
//                        whois = whois + "    <oa:annotatedBy rdf:resource=\"http://localhost:8084/rest/samian/agents#thiery\" />\n";
//                        whois = whois + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                        whois = whois + "    <oa:hasBody rdf:resource=\"http://localhost:8084/rest/samian/fragments/" + fragment_list.get(j).toString() + "\" />\n";
//                        whois = whois + "    <oa:hasTarget rdf:resource=\"http://localhost:8084/rest/samian/potters/" + potterName + "\" />\n";
//                        whois = whois + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                        whois = whois + "  </oa:Annotation>\n";
//                    }
//
//                    whois = whois + "  <foaf:person rdf:about=\"http://localhost:8084/rest/samian/agents#thiery\">\n";
//                    whois = whois + "    <foaf:name>Florian Thiery</foaf:name>\n";
//                    whois = whois + "  </foaf:person>\n";
//
//                }
//
//                whois = whois + "</rdf:RDF>\n";
//
//            }
//
//            return whois;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error");
//            return "<error>no potter with this name available</error>";
//        }
//
//    }
    
    //    public String getPleiadesFindspots() {
//
//        String pleiades = "";
//
//        try {
//
//            List<Findspot> findspots = mgr.getFindspots();
//            
//            pleiades = pleiades + "<?xml version=\"1.0\"?>\n";
//            pleiades = pleiades + "<rdf:RDF xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:spatial=\"http://geovocab.org/spatial#\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:whois=\"http://www.kanzaki.com/ns/whois#\" xmlns:potter=\"" + url_string + "samian/potters/\" xmlns:pleiadestp=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:anno=\"" + url_string + "samian/annotations#\"  xmlns:oa=\"http://www.openannotation.or4g/ns/\" xmlns:findspot=\"" + url_string + "samian/findspots/\" xmlns:agent=\"" + url_string + "samian/agents#\" xmlns:fragment=\"http://localhost:8084/rest/samian/fragments/\" xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\" xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\" xmlns:gn=\"http://www.geonames.org/ontology#\" xmlns:time=\"http://www.w3.org/2006/time#\" xmlns:pleiades=\"http://pleiades.stoa.org/places/vocab#\" xmlns:osgeo=\"http://data.ordnancesurvey.co.uk/ontology/geometry/\" xmlns:pleiadestime=\"http://pleiades.stoa.org/vocabularies/time-periods/\" xmlns:pleiadespt=\"http://pleiades.stoa.org/vocabularies/place-types/\">\n";
//
//            //for (int i = 0; i < findspots.size(); i++) {
//            for (int i = 0; i < 50; i++) {
//                
//                System.out.println("findspot:" + i + "/" + findspots.size());
//
//                String findspotNameWhitespace = findspots.get(i).getName().toString();
//                String findspotName = findspotNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
//                findspotName = findspotName.replace("__", "#");
//                findspotName = findspotName.replace(" ", "_");
//                findspotName = findspotName.replace("#", "__");
//                Findspot findspot = mgr.getFindspotByName(findspotNameWhitespace);
//                potter_list.clear();
//                fragment_list.clear();
//
//                //Get all Findspots with Prefix findspotName
//                List<Findspot> findspots_list = mgr.getFindspotsLocationsByFindspotName(findspotNameWhitespace);
//
//                //Ist das Angefragte ein Place?
//                boolean isPlace = false;
//                for (int j = 0; j < findspots_list.size(); j++) {
//                    if (!findspots_list.get(j).getName().toString().contains("__")) {
//                        isPlace = true;
//                        break;
//                    }
//                }
//
//                if (isPlace == true) { //Place
//
//                    pleiades = pleiades + "  <spatial:Feature rdf:about=\"" + url_string + "samian/findspots/" + findspotName + "\">\n";
//                    pleiades = pleiades + "    <rdfs:label>" + findspot.getName().toString() + "</rdfs:label>\n";
//                    pleiades = pleiades + "    <spatial:C rdf:resource=\"" + url_string + "samian/findspots/" + findspotName + "\" />" + "\n";
//                    pleiades = pleiades + "    <rdfs:comment>An ancient place.</rdfs:comment>" + "\n";
//                    pleiades = pleiades + "    <foaf:primaryTopicOf>" + "\n";
//                    pleiades = pleiades + "      <pleiades:Place rdf:about=\"" + url_string + "samian/findspots/" + findspotName + "\">\n";
//                    pleiades = pleiades + "        <rdf:type rdf:resource=\"http://www.w3.org/2004/02/skos/core#Concept\" />\n";
//                    if (findspot.getKilnsite() == true) {
//                        pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/production\" />\n";
//                        pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
//                        pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
//                    } else {
//                        pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
//                        pleiades = pleiades + "        <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
//                    }
//                    for (int j = 0; j < findspots_list.size(); j++) {
//                        if (findspots_list.get(j).getName().toString().contains("__")) {
//                            String tmp = findspots_list.get(j).getName().toString();
//                            tmp = tmp.replace("__", "#");
//                            tmp = tmp.replace(" ", "_");
//                            tmp = tmp.replace("#", "__");
//                            pleiades = pleiades + "        <pleiades:hasLocation rdf:resource=\"" + url_string + "samian/findspots/" + tmp + "\" />\n";
//                        }
//                    }
//                    pleiades = pleiades + "        <gn:name>" + findspot.getName().toString() + "</gn:name>\n";
//                    pleiades = pleiades + "        <geo:lat rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">" + String.valueOf(findspot.getLat()) + "</geo:lat>\n";
//                    pleiades = pleiades + "        <geo:long rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + String.valueOf(findspot.getLon()) + "</geo:long>\n";
//                    pleiades = pleiades + "        <osgeo:asGeoJSON>{\"type\": \"Point\", \"coordinates\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}</osgeo:asGeoJSON>\n";
//                    pleiades = pleiades + "        <osgeo:asWKT>POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")</osgeo:asWKT>\n";
//                    pleiades = pleiades + "        <time:during rdf:resource=\"http://pleiades.stoa.org/vocabularies/time-periods/roman\" />\n";
//                    pleiades = pleiades + "      </pleiades:Place>\n";
//                    pleiades = pleiades + "    </foaf:primaryTopicOf>\n";
//                    pleiades = pleiades + "  </spatial:Feature>\n";
//
//                    // Repräsentation der Links zu Fragments und Findspots als RDF
//
//                    //getFragmentsPottersFindspot(findspotNameWhitespace);
//                    fragment_list.clear();
//                    findspot_list.clear();
//                    potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
//                    fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);
//
//                    if (potter_list.size() > 0 || fragment_list.size() > 0) {
//
//                        for (int j = 0; j < potter_list.size(); j++) {
//                            System.out.println("findspot:" + i + "/" + findspots.size() + " potter:" + j + "/" + potter_list.size());
//                            String potter = potter_list.get(j).toString();
//                            potter = potter.replace(" ", "_");
//                            pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + potter + "\">\n";
//                            pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.org/ns/linking\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#thiery\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                            pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://143.93.114.104/rest/samian/potters/" + potter + "\" />\n";
//                            pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                            pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                            pleiades = pleiades + "  </oa:Annotation>\n";
//                        }
//
//                        for (int j = 0; j < fragment_list.size(); j++) {
//                            System.out.println("findspot:" + i + "/" + findspots.size() + " fragment:" + j + "/" + fragment_list.size());
//                            pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + fragment_list.get(j).toString() + "\">\n";
//                            pleiades = pleiades + "    <oa:motivatedBay rdf:resource=\"http://www.openannotation.org/ns/linking\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#thiery\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                            pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://143.93.114.104/rest/samian/fragments/" + fragment_list.get(j).toString() + "\" />\n";
//                            pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                            pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.</dcterms:description>\n";
//                            pleiades = pleiades + "  </oa:Annotation>\n";
//                        }
//
//                        pleiades = pleiades + "  <foaf:person rdf:about=\"http://143.93.114.104/rest/samian/agents#thiery\">\n";
//                        pleiades = pleiades + "    <foaf:name>Florian Thiery</foaf:name>\n";
//                        pleiades = pleiades + "  </foaf:person>\n";
//
//                    }
//
//                    // Repräsentation der Links zu Pleiades
//
//                    try {
//
//                        Pleiadesmatch pm = mgr.getPleiadesIDByName(findspotName);
//
//                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + pm.getPleiadesPlace().toString() + "\">\n";
//                        pleiades = pleiades + "    <oa:motivatedBy rdf:resource=\"http://www.openannotation.org/ns/linking\" />\n";
//                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#Mees\" />\n";
//                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://pleiades.stoa.org/places/" + pm.getPleiadesPlace().toString() + "\" />\n";
//                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                        pleiades = pleiades + "  </oa:Annotation>\n";
//
//                        pleiades = pleiades + "  <foaf:person rdf:about=\"http://143.93.114.104/rest/samian/agents#Mees\">\n";
//                        pleiades = pleiades + "    <foaf:name>Allard Mees</foaf:name>\n";
//                        pleiades = pleiades + "  </foaf:person>\n";
//
//                    } catch (Exception e) {
//                        pleiades = pleiades;
//                    }
//
//                } else { // Location
//
//                    pleiades = pleiades + "  <pleiades:Location rdf:about=\"" + url_string + "samian/findspots/" + findspot.getName().toString() + "\">";
//                    pleiades = pleiades + "    <rdf:type rdf:resource=\"http://www.w3.org/2004/02/skos/core#Concept\" />\n";
//                    if (findspot.getKilnsite() == true) {
//                        pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/production\" />\n";
//                        pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
//                        pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
//                    } else {
//                        pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/settlement\" />\n";
//                        pleiades = pleiades + "    <pleiades:hasFeatureType rdf:resource=\"http://pleiades.stoa.org/vocabularies/place-types/findspot\" />\n";
//                    }
//                    pleiades = pleiades + "    <gn:name>" + findspot.getName().toString() + "</gn:name>\n";
//                    pleiades = pleiades + "    <geo:lat rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">" + String.valueOf(findspot.getLat()) + "</geo:lat>\n";
//                    pleiades = pleiades + "    <geo:long rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">" + String.valueOf(findspot.getLon()) + "</geo:long>\n";
//                    pleiades = pleiades + "    <osgeo:asGeoJSON>{\"type\": \"Point\", \"coordinates\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}</osgeo:asGeoJSON> ";
//                    pleiades = pleiades + "    <osgeo:asWKT>POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")</osgeo:asWKT>\n";
//                    pleiades = pleiades + "    <time:during rdf:resource=\"http://pleiades.stoa.org/vocabularies/time-periods/roman\" />\n";
//                    pleiades = pleiades + "  </pleiades:Location>\n";
//
//                    // Repräsentation der Links zu Fragments und Findspots als RDF
//
//                    //getFragmentsPottersFindspot(findspotNameWhitespace);
//                    fragment_list.clear();
//                    findspot_list.clear();
//                    potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
//                    fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);
//
//                    if (potter_list.size() > 0 || fragment_list.size() > 0) {
//
//                        for (int j = 0; j < potter_list.size(); j++) {
//                            System.out.println("findspot:" + i + "/" + findspots.size() + " potter:" + j + "/" + potter_list.size());
//                            String potter = potter_list.get(j).toString();
//                            potter = potter.replace(" ", "_");
//                            pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + potter + "\">\n";
//                            pleiades = pleiades + "    <oa:motivatedBy rdf:resource=\"http://www.openannotation.org/ns/linking\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#thiery\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                            pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://143.93.114.104/rest/samian/potters/" + potter + "\" />\n";
//                            pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                            pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                            pleiades = pleiades + "  </oa:Annotation>\n";
//                        }
//
//                        for (int j = 0; j < fragment_list.size(); j++) {
//                            System.out.println("findspot:" + i + "/" + findspots.size() + " fragment:" + j + "/" + fragment_list.size());
//                            pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + fragment_list.get(j).toString() + "\">\n";
//                            pleiades = pleiades + "    <oa:motivatedBy rdf:resource=\"http://www.openannotation.org/ns/linking\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#thiery\" />\n";
//                            pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                            pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://143.93.114.104/rest/samian/fragments/" + fragment_list.get(j).toString() + "\" />\n";
//                            pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                            pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.</dcterms:description>\n";
//                            pleiades = pleiades + "  </oa:Annotation>\n";
//                        }
//
//                        pleiades = pleiades + "  <foaf:person rdf:about=\"http://143.93.114.104/rest/samian/agents#thiery\">\n";
//                        pleiades = pleiades + "    <foaf:name>Florian Thiery</foaf:name>\n";
//                        pleiades = pleiades + "  </foaf:person>\n";
//
//                    }
//
//                    // Repräsentation der Links zu Pleiades
//
//                    try {
//
//                        Pleiadesmatch pm = mgr.getPleiadesIDByName(findspotName);
//
//                        pleiades = pleiades + "  <oa:Annotation rdf:about=\"http://143.93.114.104/rest/samian/annotations#" + findspotName + "-" + pm.getPleiadesPlace().toString() + "\">\n";
//                        pleiades = pleiades + "    <oa:motivatedBy rdf:resource=\"http://www.openannotation.or4g/ns/linking\" />\n";
//                        pleiades = pleiades + "    <oa:annotatedBy rdf:resource=\"http://143.93.114.104/rest/samian/agents#Mees\" />\n";
//                        pleiades = pleiades + "    <oa:annotatedAt>" + new java.util.Date() + "</oa:annotatedAt>\n";
//                        pleiades = pleiades + "    <oa:hasBody rdf:resource=\"http://pleiades.stoa.org/places/" + pm.getPleiadesPlace().toString() + "\" />\n";
//                        pleiades = pleiades + "    <oa:hasTarget rdf:resource=\"http://143.93.114.104/rest/samian/findspots/" + findspotName + "\" />\n";
//                        pleiades = pleiades + "    <dcterms:description>Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.</dcterms:description>\n";
//                        pleiades = pleiades + "  </oa:Annotation>\n";
//
//                        pleiades = pleiades + "  <foaf:person rdf:about=\"http://143.93.114.104/rest/samian/agents#Mees\">\n";
//                        pleiades = pleiades + "    <foaf:name>Allard Mees</foaf:name>\n";
//                        pleiades = pleiades + "  </foaf:person>\n";
//
//
//                        //pleiades = pleiades + "@prefix pleiadesplace: <http://pleiades.stoa.org/places/> .\n";
//
//
//
//                    } catch (Exception e) {
//                        pleiades = pleiades;
//                    }
//
//                }
//
//            }
//
//            pleiades = pleiades + "</rdf:RDF>\n";
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error");
//            return "<error>no findspot with this name available</error>";
//        }
//
//        return pleiades;
//    }

}
