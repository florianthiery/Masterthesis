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
 * Klasse zum Erzeugen von RDF Turtle Elementen
 *
 * @author Florian Thiery
 */
public class CreateRDFturtle {

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
    public CreateRDFturtle(String baseURI) {
        this.url_string = baseURI;
    }

    /**
     * Get Potter as WHOIS Turtle
     *
     * @param potterName
     * @return WHOIS Turtle
     */
    public String getWHOIS(String potterName) {

        String whois = "";
        String PotterNameWhitespace = potterName; // findspot Name mit Whitespace und Underscore (for Database)
        PotterNameWhitespace = PotterNameWhitespace.replace("_", " ");

        try {

            // Repräsentation als RDF

            String potter = mgr.getPotterByName(PotterNameWhitespace).getName();

            whois = whois + "#Representation in WHOIS Syntax\n";
            whois = whois + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n";
            whois = whois + "@prefix foaf: <http://xmlns.com/foaf/0.1/>.\n";
            whois = whois + "@prefix whois: <http://www.kanzaki.com/ns/whois#>.\n";
            whois = whois + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n";
            whois = whois + "@prefix time: <http://www.w3.org/2006/time#>. \n";
            whois = whois + "@prefix pleiadestp: <http://pleiades.stoa.org/vocabularies/time-periods/>.\n";
            whois = whois + "@prefix potter: <" + url_string + "samian/potters/>.\n";
            whois = whois + "potter:" + potterName + " whois:career _:blanknode;\n";
            whois = whois + "  a foaf:Person;\n";
            whois = whois + "  foaf:name \"" + PotterNameWhitespace + "\";\n";
            whois = whois + "  foaf:gender \"male\";\n";
            whois = whois + "  time:inside pleiadestp:roman.\n";
            whois = whois + "_:blanknode a whois:Job;\n";
            whois = whois + "rdfs:label \"Potter\".\n";
            whois = whois + "\n";

            // Repräsentation der Links zu Fragments und Findspots als RDF

            //getFragmentsFindspotsPotter(potterName);
            fragment_list.clear();
            findspot_list.clear();
            fragment_list = mgr.getFragmentStringByPotterName(PotterNameWhitespace);
            findspot_list = mgr.getFindspotsStringByPotterName(PotterNameWhitespace);

            if (findspot_list.size() > 0 || fragment_list.size() > 0) {

                whois = whois + "#Links to Fragments and Findspots\n";
                whois = whois + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
                whois = whois + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
                whois = whois + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                whois = whois + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
                whois = whois + "@prefix agent: <" + url_string + "samian/agents#>.\n";
                whois = whois + "@prefix potter: <" + url_string + "samian/potters/>.\n";
                whois = whois + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
                whois = whois + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";

                for (int i = 0; i < findspot_list.size(); i++) {
                    String findspot = findspot_list.get(i).toString();
                    findspot = findspot.replace("__", "#");
                    findspot = findspot.replace(" ", "_");
                    findspot = findspot.replace("#", "__");
                    whois = whois + "anno:" + potterName + "-" + findspot + " a oa:Annotation;\n";
                    whois = whois + "  oa:motivatedBay oa:linking;\n";
                    whois = whois + "  oa:annotatedBy agent:thiery;\n";
                    whois = whois + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                    whois = whois + "  oa:hasBody findspot:" + findspot + ";\n";
                    whois = whois + "  oa:hasTarget potter:" + potterName + ";\n";
                    whois = whois + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                }

                for (int i = 0; i < fragment_list.size(); i++) {
                    whois = whois + "anno:" + potterName + "-" + fragment_list.get(i).toString() + " a oa:Annotation;\n";
                    whois = whois + "  oa:motivatedBay oa:linking;\n";
                    whois = whois + "  oa:annotatedBy agent:thiery;\n";
                    whois = whois + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                    whois = whois + "  oa:hasBody fragment:" + fragment_list.get(i).toString() + ";\n";
                    whois = whois + "  oa:hasTarget potter:" + potterName + ";\n";
                    whois = whois + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has stamped the fragment.\".\n";
                }

                whois = whois + "agent:thiery a foaf:person;\n";
                whois = whois + "  foaf:name \"Florian Thiery\".\n";
                whois = whois + "\n";

            }

            return whois;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            error = error + "no potter with this name available";
            error = error + ex.toString() + whois;
            return error;
        }

    }

    /**
     * Get Potters and Annotations as WHOIS Turtle
     *
     * @param potterName
     * @return WHOIS Turtle
     */
    public String getWHOISPotters() {

        String whois = "";

        try {

            List<Potter> potter = mgr.getPotters();

            whois = whois + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n";
            whois = whois + "@prefix foaf: <http://xmlns.com/foaf/0.1/>.\n";
            whois = whois + "@prefix whois: <http://www.kanzaki.com/ns/whois#>.\n";
            whois = whois + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n";
            whois = whois + "@prefix time: <http://www.w3.org/2006/time#>. \n";
            whois = whois + "@prefix pleiadestp: <http://pleiades.stoa.org/vocabularies/time-periods/>.\n";
            whois = whois + "@prefix potter: <" + url_string + "samian/potters/>.\n";
            whois = whois + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
            whois = whois + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            whois = whois + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            whois = whois + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
            whois = whois + "@prefix agent: <" + url_string + "samian/agents#>.\n";
            whois = whois + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
            whois = whois + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";

            for (int i = 0; i < potter.size(); i++) {
                //for (int i = 0; i < 100; i++) {

                boolean out = false;
                if (i % 10 == 0) {
                    System.out.println("potter:" + (i + 1) + "/" + potter.size());
                    out = true;
                }

                // Repräsentation als RDF

                String PotterNameWhitespace = potter.get(i).getName().toString();
                String potterName = PotterNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
                potterName = potterName.replace(" ", "_");

                whois = whois + "#Representation in WHOIS Syntax\n";
                whois = whois + "potter:" + potterName + " whois:career _:blanknode;\n";
                whois = whois + "  a foaf:Person;\n";
                whois = whois + "  foaf:name \"" + PotterNameWhitespace + "\";\n";
                whois = whois + "  foaf:gender \"male\";\n";
                whois = whois + "  time:inside pleiadestp:roman.\n";
                whois = whois + "_:blanknode a whois:Job;\n";
                whois = whois + "rdfs:label \"Potter\".\n";
                whois = whois + "\n";

                // Repräsentation der Links zu Fragments und Findspots als RDF

                //getFragmentsFindspotsPotter(potterName);
                fragment_list.clear();
                findspot_list.clear();
                fragment_list = mgr.getFragmentStringByPotterName(PotterNameWhitespace);
                findspot_list = mgr.getFindspotsStringByPotterName(PotterNameWhitespace);

                if (findspot_list.size() > 0 || fragment_list.size() > 0) {

                    whois = whois + "#Links to Fragments and Findspots\n";

                    for (int j = 0; j < findspot_list.size(); j++) {
                        if (out == true) {
                            System.out.println("potter:" + (i + 1) + "/" + potter.size() + " findspot:" + (j + 1) + "/" + findspot_list.size());
                        }
                        String findspot = findspot_list.get(j).toString();
                        findspot = findspot.replace("__", "#");
                        findspot = findspot.replace(" ", "_");
                        findspot = findspot.replace("#", "__");
                        whois = whois + "anno:" + potterName + "-" + findspot + " a oa:Annotation;\n";
                        whois = whois + "  oa:motivatedBay oa:linking;\n";
                        whois = whois + "  oa:annotatedBy agent:thiery;\n";
                        whois = whois + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        whois = whois + "  oa:hasBody findspot:" + findspot + ";\n";
                        whois = whois + "  oa:hasTarget potter:" + potterName + ";\n";
                        whois = whois + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                    }

                    for (int j = 0; j < fragment_list.size(); j++) {
                        if (out == true) {
                            System.out.println("potter:" + (i + 1) + "/" + potter.size() + " fragment:" + (j + 1) + "/" + fragment_list.size());
                        }
                        whois = whois + "anno:" + potterName + "-" + fragment_list.get(j).toString() + " a oa:Annotation;\n";
                        whois = whois + "  oa:motivatedBay oa:linking;\n";
                        whois = whois + "  oa:annotatedBy agent:thiery;\n";
                        whois = whois + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        whois = whois + "  oa:hasBody fragment:" + fragment_list.get(j).toString() + ";\n";
                        whois = whois + "  oa:hasTarget potter:" + potterName + ";\n";
                        whois = whois + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has stamped the fragment.\".\n";
                    }

                    whois = whois + "agent:thiery a foaf:person;\n";
                    whois = whois + "  foaf:name \"Florian Thiery\".\n";
                    whois = whois + "\n";

                }

            }

            return whois;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return ex.toString();
        }

    }

    /**
     * Get Findspot as Plaiades Turtle
     *
     * @param findspotName
     * @return Plaides Turtle
     */
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

                pleiades = pleiades + "#Representation like Pleiades Syntax\n";
                pleiades = pleiades + "@prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n";
                pleiades = pleiades + "@prefix geovocab: <http://geovocab.org/spatial#> .\n";
                pleiades = pleiades + "@prefix osgeo: <http://data.ordnancesurvey.co.uk/ontology/geometry/> .\n";
                pleiades = pleiades + "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\n";
                pleiades = pleiades + "@prefix gn: <http://www.geonames.org/ontology#> .\n";
                pleiades = pleiades + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n";
                pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                pleiades = pleiades + "@prefix time: <http://www.w3.org/2006/time#> .\n";
                pleiades = pleiades + "@prefix pleiades: <http://pleiades.stoa.org/places/vocab#> .\n";
                pleiades = pleiades + "@prefix pleiadespt: <http://pleiades.stoa.org/vocabularies/place-types/> .\n";
                pleiades = pleiades + "@prefix pleiadestime: <http://pleiades.stoa.org/vocabularies/time-periods/> .\n";
                pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/> .\n";

                pleiades = pleiades + "findspot:" + findspotName + " a geovocab:Feature;\n";
                pleiades = pleiades + "  rdfs:label " + "\"" + findspotNameWhitespace + "\"" + ";\n";
                pleiades = pleiades + "  rdfs:comment " + "\"An ancient place\"" + ";\n";
                pleiades = pleiades + "  foaf:primaryTopicOf " + "findspot:" + findspotName + ".\n";

                pleiades = pleiades + "findspot:" + findspotName + " a pleiades:Place,\n";
                pleiades = pleiades + "  skos:Concept;\n";
                if (findspot.getKilnsite() == true) {
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:production;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                } else {
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                }
                for (int i = 0; i < findspots_list.size(); i++) {
                    if (findspots_list.get(i).getName().toString().contains("__")) {
                        String temp = findspots_list.get(i).getName().toString();
                        temp = temp.replace(" ", "_");
                        pleiades = pleiades + "  pleiades:hasLocation findspot:" + temp + ";\n";
                    }
                }
                pleiades = pleiades + "  gn:name \"" + findspot.getName().toString() + "\";\n";
                pleiades = pleiades + "  geo:lat " + String.valueOf(findspot.getLat()) + ";\n";
                pleiades = pleiades + "  geo:long " + String.valueOf(findspot.getLon()) + ";\n";
                pleiades = pleiades + "  osgeo:asGeoJSON \"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}\";\n";
                pleiades = pleiades + "  osgeo:asWKT \"POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")\";\n";
                pleiades = pleiades + "  time:during pleiadestime:roman.\n";
                pleiades = pleiades + "\n";

                // Repräsentation der Links zu Fragments und Findspots als RDF

                //getFragmentsPottersFindspot(findspotNameWhitespace);
                fragment_list.clear();
                findspot_list.clear();
                potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);


                if (potter_list.size() > 0 || fragment_list.size() > 0) {

                    pleiades = pleiades + "#Links to Fragments and Potters\n";
                    pleiades = pleiades + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
                    pleiades = pleiades + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
                    pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                    pleiades = pleiades + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
                    pleiades = pleiades + "@prefix agent: <" + url_string + "samian/agents#>.\n";
                    pleiades = pleiades + "@prefix potter: <" + url_string + "samian/potters/>.\n";
                    pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
                    pleiades = pleiades + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";

                    for (int i = 0; i < potter_list.size(); i++) {
                        String potter = potter_list.get(i).toString();
                        potter = potter.replace(" ", "_");
                        pleiades = pleiades + "anno:" + findspotName + "-" + potter + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBy oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody potter:" + potter + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                    }

                    for (int i = 0; i < fragment_list.size(); i++) {
                        pleiades = pleiades + "anno:" + findspotName + "-" + fragment_list.get(i).toString() + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBy oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody fragment:" + fragment_list.get(i).toString() + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.\".\n";
                    }

                    pleiades = pleiades + "agent:thiery a foaf:person;\n";
                    pleiades = pleiades + "  foaf:name \"Florian Thiery\".\n";
                    pleiades = pleiades + "\n";

                }

                // Repräsentation der Links zu Pleiades

                try {

                    Pleiadesmatch pm = mgr.getPleiadesIDByName(findspotNameWhitespace);

                    if (pm != null) {

                        pleiades = pleiades + "#Links to Pleiades\n";
                        pleiades = pleiades + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
                        pleiades = pleiades + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
                        pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                        pleiades = pleiades + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
                        pleiades = pleiades + "@prefix pleiadesplace: <http://pleiades.stoa.org/places/> .\n";
                        pleiades = pleiades + "@prefix agent: <" + url_string + "samian/agents#>.\n";
                        pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";

                        pleiades = pleiades + "anno:" + findspotName + "-" + pm.getPleiadesPlace().toString() + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBy oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:Mees;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody pleiadesplace:" + pm.getPleiadesPlace().toString() + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";

                        pleiades = pleiades + "agent:Mees a foaf:person;\n";
                        pleiades = pleiades + "  foaf:name \"Allard Mees\".\n";
                        pleiades = pleiades + "\n";

                    }

                } catch (Exception e) {
                    pleiades = pleiades;
                }


            } else { // Location

                pleiades = pleiades + "#Representation like Pleiades Syntax\n";
                pleiades = pleiades + "@prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n";
                pleiades = pleiades + "@prefix osgeo: <http://data.ordnancesurvey.co.uk/ontology/geometry/> .\n";
                pleiades = pleiades + "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\n";
                pleiades = pleiades + "@prefix gn: <http://www.geonames.org/ontology#> .\n";
                pleiades = pleiades + "@prefix time: <http://www.w3.org/2006/time#> .\n";
                pleiades = pleiades + "@prefix pleiades: <http://pleiades.stoa.org/places/vocab#> .\n";
                pleiades = pleiades + "@prefix pleiadespt: <http://pleiades.stoa.org/vocabularies/place-types/> .\n";
                pleiades = pleiades + "@prefix pleiadestime: <http://pleiades.stoa.org/vocabularies/time-periods/> .\n";
                pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/> .\n";

                pleiades = pleiades + "findspot:" + findspotName + " a pleiades:Location,\n";
                pleiades = pleiades + "  skos:Concept;\n";
                if (findspot.getKilnsite() == true) {
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:production;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                } else {
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                    pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                }
                pleiades = pleiades + "  gn:name \"" + findspot.getName().toString() + "\";\n";
                pleiades = pleiades + "  geo:lat " + String.valueOf(findspot.getLat()) + ";\n";
                pleiades = pleiades + "  geo:long " + String.valueOf(findspot.getLon()) + ";\n";
                pleiades = pleiades + "  osgeo:asGeoJSON \"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}\";\n";
                pleiades = pleiades + "  osgeo:asWKT \"POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")\";\n";
                pleiades = pleiades + "  time:during pleiadestime:roman.\n";
                pleiades = pleiades + "\n";

                // Repräsentation der Links zu Fragments und Findspots als RDF

                //getFragmentsPottersFindspot(findspotNameWhitespace);
                fragment_list.clear();
                findspot_list.clear();
                potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);

                if (potter_list.size() > 0 || fragment_list.size() > 0) {

                    pleiades = pleiades + "#Links to Fragments and Potters\n";
                    pleiades = pleiades + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
                    pleiades = pleiades + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
                    pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                    pleiades = pleiades + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
                    pleiades = pleiades + "@prefix agent: <" + url_string + "samian/agents#>.\n";
                    pleiades = pleiades + "@prefix potter: <" + url_string + "samian/potters/>.\n";
                    pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
                    pleiades = pleiades + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";

                    for (int i = 0; i < potter_list.size(); i++) {
                        String potter = potter_list.get(i).toString();
                        potter = potter.replace(" ", "_");
                        pleiades = pleiades + "anno:" + findspotName + "-" + potter + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBay oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody potter:" + potter + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                    }

                    for (int i = 0; i < fragment_list.size(); i++) {
                        pleiades = pleiades + "anno:" + findspotName + "-" + fragment_list.get(i).toString() + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBay oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody fragment:" + fragment_list.get(i).toString() + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.\".\n";
                    }

                    pleiades = pleiades + "agent:thiery a foaf:person;\n";
                    pleiades = pleiades + "  foaf:name \"Florian Thiery\".\n";
                    pleiades = pleiades + "\n";

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            error = error + "no findspot with this name available - ";
            error = error + ex.toString() + pleiades;
            return error;
        }

        return pleiades;
    }

    /**
     * Get Findspots as Plaiades Turtle
     *
     * @param findspotName
     * @return Plaides Turtle
     */
    public String getPleiadesFindspots() {

        String pleiades = "";

        try {

            List<Findspot> findspots = mgr.getFindspots();
            pleiades = pleiades + "#Findspots\n";
            pleiades = pleiades + "@prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .\n";
            pleiades = pleiades + "@prefix spatial: <http://geovocab.org/spatial#> .\n";
            pleiades = pleiades + "@prefix osgeo: <http://data.ordnancesurvey.co.uk/ontology/geometry/> .\n";
            pleiades = pleiades + "@prefix skos: <http://www.w3.org/2004/02/skos/core#> .\n";
            pleiades = pleiades + "@prefix gn: <http://www.geonames.org/ontology#> .\n";
            pleiades = pleiades + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n";
            pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            pleiades = pleiades + "@prefix time: <http://www.w3.org/2006/time#> .\n";
            pleiades = pleiades + "@prefix pleiades: <http://pleiades.stoa.org/places/vocab#> .\n";
            pleiades = pleiades + "@prefix pleiadespt: <http://pleiades.stoa.org/vocabularies/place-types/> .\n";
            pleiades = pleiades + "@prefix pleiadestime: <http://pleiades.stoa.org/vocabularies/time-periods/> .\n";
            pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/> .\n";
            pleiades = pleiades + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
            pleiades = pleiades + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            pleiades = pleiades + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
            pleiades = pleiades + "@prefix agent: <" + url_string + "samian/agents#>.\n";
            pleiades = pleiades + "@prefix potter: <" + url_string + "samian/potters/>.\n";
            pleiades = pleiades + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";
            pleiades = pleiades + "\n";

            for (int i = 0; i < findspots.size(); i++) {
                //for (int i = 0; i < 50; i++) {

                boolean out = false;
                if (i % 10 == 0) {
                    System.out.println("findspot:" + (i + 1) + "/" + findspots.size());
                    out = true;
                }

                String findspotNameWhitespace = findspots.get(i).getName().toString();
                String findspotName = findspotNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
                findspotName = findspotName.replace("__", "#");
                findspotName = findspotName.replace(" ", "_");
                findspotName = findspotName.replace("#", "__");
                Findspot findspot = mgr.getFindspotByName(findspotNameWhitespace);
                potter_list.clear();
                fragment_list.clear();

                //Get all Findspots with Prefix findspotName
                List<Findspot> findspots_list = mgr.getFindspotsLocationsByFindspotName(findspotNameWhitespace);

                //Ist das Angefragte ein Place?
                boolean isPlace = false;
                for (int j = 0; j < findspots_list.size(); j++) {
                    if (!findspots_list.get(j).getName().toString().contains("__")) {
                        isPlace = true;
                        break;
                    }
                }

                if (isPlace == true) { //Place

                    pleiades = pleiades + "#Representation like Pleiades Syntax\n";

                    pleiades = pleiades + "findspot:" + findspotName + " a spatial:Feature;\n";
                    pleiades = pleiades + "  rdfs:label " + "\"" + findspotNameWhitespace + "\"" + ";\n";
                    pleiades = pleiades + "  rdfs:comment " + "\"An ancient place\"" + ";\n";
                    pleiades = pleiades + "  foaf:primaryTopicOf " + "findspot:" + findspotName + ".\n";

                    pleiades = pleiades + "findspot:" + findspotName + " a pleiades:Place,\n";
                    pleiades = pleiades + "  skos:Concept;\n";
                    if (findspot.getKilnsite() == true) {
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:production;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                    } else {
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                    }
                    for (int j = 0; j < findspots_list.size(); j++) {
                        if (findspots_list.get(j).getName().toString().contains("__")) {
                            String tmp = findspots_list.get(j).getName().toString();
                            tmp = tmp.replace("__", "#");
                            tmp = tmp.replace(" ", "_");
                            tmp = tmp.replace("#", "__");
                            pleiades = pleiades + "  pleiades:hasLocation findspot:" + tmp + ";\n";
                        }
                    }
                    pleiades = pleiades + "  gn:name \"" + findspot.getName().toString() + "\";\n";
                    pleiades = pleiades + "  geo:lat " + String.valueOf(findspot.getLat()) + ";\n";
                    pleiades = pleiades + "  geo:long " + String.valueOf(findspot.getLon()) + ";\n";
                    pleiades = pleiades + "  osgeo:asGeoJSON \"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}\";\n";
                    pleiades = pleiades + "  osgeo:asWKT \"POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")\";\n";
                    pleiades = pleiades + "  time:during pleiadestime:roman.\n";
                    pleiades = pleiades + "\n";

                    // Repräsentation der Links zu Fragments und Findspots als RDF

                    //getFragmentsPottersFindspot(findspotNameWhitespace);
                    fragment_list.clear();
                    findspot_list.clear();
                    potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                    fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);

                    if (potter_list.size() > 0 || fragment_list.size() > 0) {

                        pleiades = pleiades + "#Links to Fragments and Potters\n";

                        for (int j = 0; j < potter_list.size(); j++) {
                            if (out == true) {
                                System.out.println("findspot:" + (i + 1) + "/" + findspots.size() + " potter:" + (j + 1) + "/" + potter_list.size());
                            }
                            String potter = potter_list.get(j).toString();
                            potter = potter.replace(" ", "_");
                            pleiades = pleiades + "anno:" + findspotName + "-" + potter + " a oa:Annotation;\n";
                            pleiades = pleiades + "  oa:motivatedBy oa:linking;\n";
                            pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                            pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                            pleiades = pleiades + "  oa:hasBody potter:" + potter + ";\n";
                            pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                            pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                        }

                        for (int j = 0; j < fragment_list.size(); j++) {
                            if (out == true) {
                                System.out.println("findspot:" + (i + 1) + "/" + findspots.size() + " fragment:" + (j + 1) + "/" + fragment_list.size());
                            }
                            pleiades = pleiades + "anno:" + findspotName + "-" + fragment_list.get(j).toString() + " a oa:Annotation;\n";
                            pleiades = pleiades + "  oa:motivatedBay oa:linking;\n";
                            pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                            pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                            pleiades = pleiades + "  oa:hasBody fragment:" + fragment_list.get(j).toString() + ";\n";
                            pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                            pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.\".\n";
                        }

                        pleiades = pleiades + "agent:thiery a foaf:person;\n";
                        pleiades = pleiades + "  foaf:name \"Florian Thiery\".\n";
                        pleiades = pleiades + "\n";

                    }

                } else { // Location

                    pleiades = pleiades + "#Representation like Pleiades Syntax\n";

                    pleiades = pleiades + "findspot:" + findspotName + " a pleiades:Location,\n";
                    pleiades = pleiades + "  skos:Concept;\n";
                    if (findspot.getKilnsite() == true) {
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:production;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                    } else {
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:settlement;\n";
                        pleiades = pleiades + "  pleiades:hasFeatureType pleiadespt:findspot;\n";
                    }
                    pleiades = pleiades + "  gn:name \"" + findspot.getName().toString() + "\";\n";
                    pleiades = pleiades + "  geo:lat " + String.valueOf(findspot.getLat()) + ";\n";
                    pleiades = pleiades + "  geo:long " + String.valueOf(findspot.getLon()) + ";\n";
                    pleiades = pleiades + "  osgeo:asGeoJSON \"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [" + String.valueOf(findspot.getLon()) + ", " + String.valueOf(findspot.getLat()) + "]}\";\n";
                    pleiades = pleiades + "  osgeo:asWKT \"POINT (" + String.valueOf(findspot.getLon()) + " " + String.valueOf(findspot.getLat()) + ")\";\n";
                    pleiades = pleiades + "  time:during pleiadestime:roman.\n";
                    pleiades = pleiades + "\n";

                    // Repräsentation der Links zu Fragments und Findspots als RDF

                    //getFragmentsPottersFindspot(findspotNameWhitespace);
                    fragment_list.clear();
                    findspot_list.clear();
                    potter_list = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
                    fragment_list = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);

                    if (potter_list.size() > 0 || fragment_list.size() > 0) {

                        pleiades = pleiades + "#Links to Fragments and Potters\n";

                        for (int j = 0; j < potter_list.size(); j++) {
                            if (out == true) {
                                System.out.println("findspot:" + (i + 1) + "/" + findspots.size() + " potter:" + (j + 1) + "/" + potter_list.size());
                            }
                            String potter = potter_list.get(j).toString();
                            potter = potter.replace(" ", "_");
                            pleiades = pleiades + "anno:" + findspotName + "-" + potter + " a oa:Annotation;\n";
                            pleiades = pleiades + "  oa:motivatedBay oa:linking;\n";
                            pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                            pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                            pleiades = pleiades + "  oa:hasBody potter:" + potter + ";\n";
                            pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                            pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";
                        }

                        for (int j = 0; j < fragment_list.size(); j++) {
                            if (out == true) {
                                System.out.println("findspot:" + (i + 1) + "/" + findspots.size() + " fragment:" + (j + 1) + "/" + fragment_list.size());
                            }
                            pleiades = pleiades + "anno:" + findspotName + "-" + fragment_list.get(j).toString() + " a oa:Annotation;\n";
                            pleiades = pleiades + "  oa:motivatedBay oa:linking;\n";
                            pleiades = pleiades + "  oa:annotatedBy agent:thiery;\n";
                            pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                            pleiades = pleiades + "  oa:hasBody fragment:" + fragment_list.get(j).toString() + ";\n";
                            pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                            pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Fragment was found, produced or exported to Findspot.\".\n";
                        }

                        pleiades = pleiades + "agent:thiery a foaf:person;\n";
                        pleiades = pleiades + "  foaf:name \"Florian Thiery\".\n";
                        pleiades = pleiades + "\n";

                    } //if

                } // else

                // Repräsentation der Links zu Pleiades

                try {

                    Pleiadesmatch pm = mgr.getPleiadesIDByName(findspotNameWhitespace);

                    if (pm != null) {

                        pleiades = pleiades + "#Link to Pleiades\n";
                        pleiades = pleiades + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
                        pleiades = pleiades + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
                        pleiades = pleiades + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
                        pleiades = pleiades + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
                        pleiades = pleiades + "@prefix pleiadesplace: <http://pleiades.stoa.org/places/> .\n";
                        pleiades = pleiades + "@prefix agent: <" + url_string + "samian/agents#>.\n";
                        pleiades = pleiades + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";

                        pleiades = pleiades + "anno:" + findspotName + "-" + pm.getPleiadesPlace().toString() + " a oa:Annotation;\n";
                        pleiades = pleiades + "  oa:motivatedBy oa:linking;\n";
                        pleiades = pleiades + "  oa:annotatedBy agent:Mees;\n";
                        pleiades = pleiades + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                        pleiades = pleiades + "  oa:hasBody pleiadesplace:" + pm.getPleiadesPlace().toString() + ";\n";
                        pleiades = pleiades + "  oa:hasTarget findspot:" + findspotName + ";\n";
                        pleiades = pleiades + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";

                        pleiades = pleiades + "agent:Mees a foaf:person;\n";
                        pleiades = pleiades + "  foaf:name \"Allard Mees\".\n";
                        pleiades = pleiades + "\n";

                    }

                } catch (Exception e) {
                    pleiades = pleiades;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no findspot with this name available";
        }
        return pleiades;
    }

    /**
     * Get Fragment as CIDOC CRM dialect
     *
     * @param fragmentID
     * @return Fragment Turtle
     */
    public String getCIDOCCRMFragment(int fragmentID) {

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

            // Repräsentation als RDF

            frag = frag + "#Representation like CIDOC CRM (Claros) Syntax\n";
            frag = frag + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n";
            frag = frag + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n";
            frag = frag + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";
            frag = frag + "@prefix crm: <http://purl.org/NET/crm-owl#> .\n";
            frag = frag + "@prefix arachne: <http://arachne.uni-koeln.de/vocabulary/objectType#>.\n";
            frag = frag + "@prefix claros: <http://id.clarosnet.org/vocab/>.\n";
            frag = frag + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n";

            frag = frag + "fragment:" + fragmentID + " a crm:E22_Man-Made_Object;\n";
            frag = frag + "  rdfs:label \"Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers\";\n";
            frag = frag + "  crm:P102_has_title \"Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers\";\n";
            frag = frag + "  crm:P2_has_type \"Samian Terra Sigillata\"; \n";
            frag = frag + "  crm:P2_has_type \"" + potform + "\"; \n";
            frag = frag + "  crm:P57_has_number_of_parts \"" + number + "\";\n";
            frag = frag + "  crm:P56_bears_feature \"" + die + "\";\n";
            frag = frag + "  crm:P44_has_condition [ a crm:E3_Condition_State;\n";
            frag = frag + "    crm:P2_has_type \"fragment\"];\n";
            frag = frag + "  crm:P45_consists_of \"Ton\".\n";

            // Repräsentation der Links zu Fragments und Findspots als RDF

            frag = frag + "\n#Links to Findspots and Potters\n";
            frag = frag + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
            frag = frag + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            frag = frag + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            frag = frag + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
            frag = frag + "@prefix agent: <" + url_string + "samian/agents#>.\n";
            frag = frag + "@prefix potter: <" + url_string + "samian/potters/>.\n";
            frag = frag + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
            frag = frag + "@prefix fragment: <" + url_string + "samian/fragments/>.\n";

            String findspotNameWhitespace = findspot;
            String findspotName = findspotNameWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
            findspotName = findspotName.replace("__", "#");
            findspotName = findspotName.replace(" ", "_");
            findspotName = findspotName.replace("#", "__");
            frag = frag + "anno:" + fragmentID + "-" + findspotName + " a oa:Annotation;\n";
            frag = frag + "  oa:motivatedBay oa:linking;\n";
            frag = frag + "  oa:annotatedBy agent:thiery;\n";
            frag = frag + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
            frag = frag + "  oa:hasBody findspot:" + findspotName + ";\n";
            frag = frag + "  oa:hasTarget fragment:" + fragmentID + ";\n";
            frag = frag + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has worked in Findspot or exported to it.\".\n";

            potter = potter.replace(" ", "_");
            frag = frag + "anno:" + fragmentID + "-" + potter + " a oa:Annotation;\n";
            frag = frag + "  oa:motivatedBay oa:linking;\n";
            frag = frag + "  oa:annotatedBy agent:thiery;\n";
            frag = frag + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
            frag = frag + "  oa:hasBody potter:" + potter + ";\n";
            frag = frag + "  oa:hasTarget fragment:" + fragmentID + ";\n";
            frag = frag + "  dcterms:description \"Link made by Heinz/Mees/Thiery. Database by RGZM. Potter has stamped the fragment.\".\n";

            frag = frag + "agent:thiery a foaf:person;\n";
            frag = frag + "  foaf:name \"Florian Thiery\".\n";
            frag = frag + "\n";

            return frag;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no potter with this name available";
        }

    }

    /**
     * Get Fragments as CIDOC CRM dialect
     *
     * @return Fragments Turtle
     */
    public String getCIDOCCRMFragments() {

        String frag = "";

        try {

            // Datenbank Data
            List<Fragment> fragments = mgr.getFragments();

            frag = frag + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n";
            frag = frag + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n";
            frag = frag + "@prefix crm: <http://purl.org/NET/crm-owl#> .\n";
            frag = frag + "@prefix arachne: <http://arachne.uni-koeln.de/vocabulary/objectType#>.\n";
            frag = frag + "@prefix claros: <http://id.clarosnet.org/vocab/>.\n";
            frag = frag + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n";
            frag = frag + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
            frag = frag + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            frag = frag + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            frag = frag + "@prefix anno: <http://143.93.114.104/rest/samian/annotations#>.\n";
            frag = frag + "@prefix agent: <http://143.93.114.104/rest/samian/agents#>.\n";
            frag = frag + "@prefix potter: <http://143.93.114.104/rest/samian/potters/>.\n";
            frag = frag + "@prefix findspot: <http://143.93.114.104/rest/samian/findspots/>.\n";
            frag = frag + "@prefix fragment: <http://143.93.114.104/rest/samian/fragments/>.\n";

            //for (int i = 0; i < 500; i++) {
            for (int i = 0; i < fragments.size(); i++) {

                if (i % 100 == 0) {
                    System.out.println("fragment:" + (i + 1) + "/" + fragments.size());
                }

                // Datenbank Data

                int fragmentID = fragments.get(i).getId();
                Hashtable<String, String> data = mgr.getPotterFindspotDataByFragmentID(fragmentID);

                String potterWhitespace = data.get("potterName");
                String potter = potterWhitespace;
                potter = potter.replace(" ", "_");
                String die = data.get("fragmentDie");
                String number = data.get("fragmentNumber");
                String potform = data.get("fragmentPotform");

                String findspotWhitespace = data.get("findspotName");
                String findspot = findspotWhitespace; // findspot Name mit Whitespace und Underscore (for Database)
                findspot = findspot.replace("__", "#");
                findspot = findspot.replace(" ", "_");
                findspot = findspot.replace("#", "__");

                // Repräsentation als RDF

                frag = frag + "#Representation like CIDOC CRM (Claros) Syntax\n";

                frag = frag + "fragment:" + fragmentID + " a crm:E22_Man-Made_Object;\n";
                frag = frag + "  rdfs:label \"Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers\";\n";
                frag = frag + "  crm:P102_has_title \"Fragment einer Terra Sigillata Scherbe mit Die eines Toepfers\";\n";
                frag = frag + "  crm:P2_has_type \"Samian Terra Sigillata\"; \n";
                frag = frag + "  crm:P2_has_type \"" + potform + "\"; \n";
                frag = frag + "  crm:P57_has_number_of_parts \"" + number + "\";\n";
                frag = frag + "  crm:P56_bears_feature \"" + die + "\";\n";
                frag = frag + "  crm:P44_has_condition [ a crm:E3_Condition_State;\n";
                frag = frag + "    crm:P2_has_type \"fragment\"];\n";
                frag = frag + "  crm:P45_consists_of \"Ton\".\n";

                frag = frag + "#Links to Findspots and Potters\n";

                frag = frag + "anno:" + fragmentID + "-" + findspot + " a oa:Annotation;\n";
                frag = frag + "  oa:motivatedBy oa:linking;\n";
                frag = frag + "  oa:annotatedBy agent:thiery;\n";
                frag = frag + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                frag = frag + "  oa:hasBody findspot:" + findspot + ";\n";
                frag = frag + "  oa:hasTarget fragment:" + fragmentID + ";\n";
                frag = frag + "  dcterms:description \"Link made by Heinz/Mees/Thiery.\".\n";

                frag = frag + "anno:" + fragmentID + "-" + potter + " a oa:Annotation;\n";
                frag = frag + "  oa:motivatedBy oa:linking;\n";
                frag = frag + "  oa:annotatedBy agent:thiery;\n";
                frag = frag + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                frag = frag + "  oa:hasBody potter:" + potter + ";\n";
                frag = frag + "  oa:hasTarget fragment:" + fragmentID + ";\n";
                frag = frag + "  dcterms:description \"Link made by Heinz/Mees/Thiery.\".\n";

                frag = frag + "agent:thiery a foaf:person;\n";
                frag = frag + "  foaf:name \"Florian Thiery\".\n";
                frag = frag + "\n";

            }

            return frag;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "error";
        }

    }

    /**
     * Get all Agents
     *
     * @return Agents in RDF (turtle)
     */
    public String getAgents() {

        String agents = "";

        try {

            // Repräsentation als RDF
            agents = agents + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            agents = agents + "@prefix agent: <" + url_string + "samian/agents#>.\n";
            agents = agents + "\n";
            agents = agents + "agent:thiery a foaf:person;\n";
            agents = agents + "  foaf:name \"Florian Thiery\".\n";
            agents = agents + "\n";
            agents = agents + "agent:Mees a foaf:person;\n";
            agents = agents + "  foaf:name \"Allard Mees\".\n";
            agents = agents + "\n";

            return agents;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no agent available";
        }

    }

    /**
     * Get all Annotations
     *
     * @return Annotations Error Message
     */
    public String getAnnotations() {

        String anno = "";

        try {

            anno = anno + "It is not possible to Show all annotations.\n";
            anno = anno + "Please look at the following files.\n";
            anno = anno + "- http://143.93.114.104/share/potters.ttl\n";
            anno = anno + "- http://143.93.114.104/share/fragments.ttl\n";
            anno = anno + "- http://143.93.114.104/share/findspots.ttl\n";

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no annotation available";
        } finally {
            return anno;
        }

    }

    /**
     * Get Pleiades Annotations
     *
     * @return Annotations in RDF (turtle)
     */
    public String getPleiadesAnnotations() {

        String anno = "";

        try {

            // Repräsentation der Links zu Pleiades

            match_list = mgr.getPleiadesmatch();
            anno = anno + "#Links to Pleiades\n";
            anno = anno + "#Prefix\n";
            anno = anno + "@prefix oa: <http://www.openannotation.or4g/ns/> .\n";
            anno = anno + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            anno = anno + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";
            anno = anno + "@prefix pleiadesplace: <http://pleiades.stoa.org/places/> .\n";
            anno = anno + "@prefix anno: <" + url_string + "samian/annotations#>.\n";
            anno = anno + "@prefix agent: <" + url_string + "samian/agents#>.\n";
            anno = anno + "@prefix findspot: <" + url_string + "samian/findspots/>.\n";
            anno = anno + "\n";

            for (int j = 0; j < match_list.size(); j++) {

                Pleiadesmatch pm = match_list.get(j);

                anno = anno + "anno:" + pm.getFindspot().toString() + "-" + pm.getPleiadesPlace().toString() + " a oa:Annotation;\n";
                anno = anno + "  oa:motivatedBy oa:linking;\n";
                anno = anno + "  oa:annotatedBy agent:Mees;\n";
                anno = anno + "  oa:annotatedAt \"" + new java.util.Date() + "\";\n";
                anno = anno + "  oa:hasBody pleiadesplace:" + pm.getPleiadesPlace().toString() + ";\n";
                anno = anno + "  oa:hasTarget findspot:" + pm.getFindspot().toString() + ";\n";
                anno = anno + "  dcterms:description \"Link made by Heinz/Mees/Thiery.\".\n";

                anno = anno + "agent:Mees a foaf:person;\n";
                anno = anno + "  foaf:name \"Allard Mees\".\n";

            }

            return anno;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no annotation available";
        }

    }

    /**
     * Get all Annotations
     *
     * @return Annotations Error Message
     */
    public String getVoid() {

        String void_string = "";

        try {

            void_string = void_string + "@prefix : <http://143.93.114.104/rest/samian/pleiades> .\n";
            void_string = void_string + "@prefix void: <http://rdfs.org/ns/void#> .\n";
            void_string = void_string + "@prefix dcterms: <http://purl.org/dc/terms/> .\n";
            void_string = void_string + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n";

            void_string = void_string + ":oc a void:Dataset;\n";
            void_string = void_string + "# Title and description\n";
            void_string = void_string + "dcterms:title \"Sinked Samian Ware Data\";\n";
            void_string = void_string + "dcterms:description \"Potter, Pots and Places of Samian Ware\";\n";

            void_string = void_string + "# License\n";
            void_string = void_string + "dcterms:license <http://creativecommons.org/licenses/by-nc-nd/3.0/deed.de>;\n";

            void_string = void_string + "# Dump file location and serialization format\n";
            void_string = void_string + "void:dataDump <http://143.93.114.104/rest/samian/pleiades>;\n";
            void_string = void_string + "void:feature <http://www.w3.org/ns/formats/Turtle>;\n";

            void_string = void_string + "# A homepage with information ABOUT the data\n";
            void_string = void_string + "foaf:homepage <http://www.rgzm.de/samian/>;\n";


        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return "no annotation available";
        } finally {
            return void_string;
        }


    }
}
