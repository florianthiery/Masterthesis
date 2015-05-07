package de.i3mainz.createOutput;

import de.i3mainz.dbutils.event.EventManager;
import de.i3mainz.dbutils.event.Findspot;
import de.i3mainz.sesameconnection.Sesame;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom.JDOMException;

/**
 * Klasse zum Erzeugen von JAXB XML Elementen
 *
 * @author Florian Thiery
 */
public class CreateEXPLORER {

    private String url_string = "";
    private List<Findspot> findspot_list = new ArrayList<Findspot>();
    private List<String> potterNames = new ArrayList<String>();
    private List<String> FragmentIDs = new ArrayList<String>();
    EventManager mgr = new EventManager();

    /**
     * Konstruktor [baseURI]
     */
    public CreateEXPLORER(String baseURI) {
        this.url_string = baseURI;
    }

    /**
     * Creates EXPLORER XML with Potter and Fragment Content out of Database
     *
     * @return String[explorerXML]
     */
    public String getPottersFragmentsDatabase(String findspotName) throws MalformedURLException, IOException, JDOMException {

        String xmlout = "";
        String findspotNameWhitespace = findspotName; // findspot Name mit Whitespace und Underscore (for Database)
        findspotNameWhitespace = findspotNameWhitespace.replace("__", "#");
        findspotNameWhitespace = findspotNameWhitespace.replace("_", " ");
        findspotNameWhitespace = findspotNameWhitespace.replace("#", "__");

        try {

            xmlout = xmlout + "<list>";

            potterNames = mgr.getPotterStringByFindspotName(findspotNameWhitespace);
            System.out.println("potterNames:" + potterNames.size());

            Map<String, List<String>> fragementsByPotter = mgr.getFragmentIDsByPotterList(potterNames);
            System.out.println("fragementsByPotter:" + fragementsByPotter.size());

            for (Iterator<Entry<String, List<String>>> itr = fragementsByPotter.entrySet().iterator(); itr.hasNext();) {

                Entry<String, List<String>> tmp = itr.next();

                String potter_name = tmp.getKey();
                potter_name = potter_name.replace(" ", "_");
                xmlout = xmlout + "  <potter uri=\"" + url_string + "samian/potters/" + potter_name + "\">";

                xmlout = xmlout + "    <fragment_p>";
                for (String fragId : tmp.getValue()) {
                    xmlout = xmlout + "      <uri>" + url_string + "samian/fragments/" + fragId + "</uri>";
                }
                xmlout = xmlout + "    </fragment_p>";

                xmlout = xmlout + "  </potter>";

            }

            FragmentIDs = mgr.getFragmentStringByFindspotName(findspotNameWhitespace);
            System.out.println("FragmentIDs:" + FragmentIDs.size());

            Map<String, String> potterByFragments = mgr.getPotterNamesByFragmentList(FragmentIDs);
            System.out.println("potterByFragments:" + potterByFragments.size());

            for (Iterator<Entry<String, String>> itr = potterByFragments.entrySet().iterator(); itr.hasNext();) {

                Entry<String, String> tmp = itr.next();

                String fragmentID = tmp.getKey();
                xmlout = xmlout + "  <fragment uri=\"" + url_string + "samian/fragments/" + fragmentID + "\">";

                xmlout = xmlout + "    <potter_f>";
                String potter = tmp.getValue();
                potter = potter.replace(" ", "_");
                xmlout = xmlout + "      <uri>" + url_string + "samian/potters/" + potter + "</uri>";
                xmlout = xmlout + "    </potter_f>";

                xmlout = xmlout + "  </fragment>";

            }

            xmlout = xmlout + "</list>";

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            String out = "";
            out = out + ex.toString() + "\n";
            out = out + xmlout + "\n";
            return out;
        }

        return xmlout;

    }

    /**
     * Creates EXPLORER XML with Potter and Fragment Content out of TripleStore
     *
     * @return String[explorerXML]
     */
    public String getPottersFragmentsTripleStore(String findspotName) throws MalformedURLException, IOException, JDOMException {

        String xmlout = "";

        try {

//            Sesame s = new Sesame("potter", "Acutillus", "findspot2");
//            s.getURIs();

            xmlout = xmlout + "<list>";

            //get potters of findspot findspotName from Triplestore
            Sesame s = new Sesame("findspot", findspotName, "potter");
            //s.getURIs();
            List<String> potters = s.getItems();

            //get fragments of potters
            for (int i = 0; i < potters.size(); i++) {

                xmlout = xmlout + "  <potter uri=\"" + url_string + "samian/potters/" + potters.get(i) + "\">";
                xmlout = xmlout + "    <fragment_p>";

                //fragments of single potter
                Sesame s2 = new Sesame("potter", potters.get(i), "fragment");
                s2.getURIs();
                List<String> fragments = s2.getItems();

                for (int j = 0; j < fragments.size(); j++) {
                    xmlout = xmlout + "      <uri>" + url_string + "samian/fragments/" + fragments.get(j) + "</uri>";
                }

                xmlout = xmlout + "    </fragment_p>";
                xmlout = xmlout + "  </potter>";

            }

            //get fragments of findspot findspotName from Triplestore
            Sesame s3 = new Sesame("findspot", findspotName, "fragment");
            s3.getURIs();
            List<String> fragments = s3.getItems();

            //get potters of fragments
            for (int i = 0; i < fragments.size(); i++) {

                xmlout = xmlout + "  <fragment uri=\"" + url_string + "samian/fragments/" + fragments.get(i) + "\">";
                xmlout = xmlout + "    <potter_f>";

                //potter of single fragment
                Sesame s4 = new Sesame("fragment", fragments.get(i), "potter");
                s4.getURIs();
                List<String> potter = s4.getItems();

                for (int j = 0; j < potter.size(); j++) {
                    xmlout = xmlout + "      <uri>" + url_string + "samian/potters/" + potter.get(j) + "</uri>";
                }

                xmlout = xmlout + "    </potter_f>";
                xmlout = xmlout + "  </fragment>";

            }

            xmlout = xmlout + "</list>";

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            String out = "";
            out = out + ex.toString() + "\n";
            out = out + xmlout + "\n";
            return out;
        }

        return xmlout;

    }

    /**
     * Creates EXPLORER GeoJSON with Findspots a Potter worked
     *
     * @return String[explorerGEOJSON]
     */
    public String getPottersGeoJSONDatabase(String potterName) {

        String jsonout = "";
        String potterNameWhitspace = potterName; // findspot Name mit Whitespace und Underscore (for Database)
        potterNameWhitspace = potterNameWhitspace.replace("_", " ");

        try {

            findspot_list = mgr.getFindspotsByPotterName(potterNameWhitspace);

            if (findspot_list.size() > 0) {

                jsonout = jsonout + "{\n";
                jsonout = jsonout + "\"type\": \"FeatureCollection\",\n";
                jsonout = jsonout + "\"features\": [\n";

                for (int i = 0; i < findspot_list.size(); i++) {

                    if (i == findspot_list.size() - 1) {
                        jsonout = jsonout + "{\"type\": \"Feature\",\n";
                        jsonout = jsonout + "\"properties\": {\n";
                        jsonout = jsonout + "\"kilnsite\": \"" + findspot_list.get(i).getKilnsite() + "\",\n";
                        jsonout = jsonout + "\"name\": \"" + findspot_list.get(i).getName() + "\"\n";
                        jsonout = jsonout + "},\n";
                        jsonout = jsonout + "\"geometry\": {\n";
                        jsonout = jsonout + "\"type\": \"Point\",\n";
                        jsonout = jsonout + "\"coordinates\": [" + findspot_list.get(i).getLon() + ", " + findspot_list.get(i).getLat() + "]\n"; //lon lat
                        jsonout = jsonout + "}\n";
                        jsonout = jsonout + "}\n";
                    } else {
                        jsonout = jsonout + "{\"type\": \"Feature\",\n";
                        jsonout = jsonout + "\"properties\": {\n";
                        jsonout = jsonout + "\"kilnsite\": \"" + findspot_list.get(i).getKilnsite() + "\",\n";
                        jsonout = jsonout + "\"name\": \"" + findspot_list.get(i).getName() + "\"\n";
                        jsonout = jsonout + "},\n";
                        jsonout = jsonout + "\"geometry\": {\n";
                        jsonout = jsonout + "\"type\": \"Point\",\n";
                        jsonout = jsonout + "\"coordinates\": [" + findspot_list.get(i).getLon() + ", " + findspot_list.get(i).getLat() + "]\n"; //lon lat
                        jsonout = jsonout + "}\n";
                        jsonout = jsonout + "},\n";
                    }

                }

                jsonout = jsonout + "]";
                jsonout = jsonout + "}";

            } else {
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");
            return "an error occured";
        }

        return jsonout;

    }

    /**
     * Creates EXPLORER GeoJSON with Findspots a Potter worked
     *
     * @return String[explorerGEOJSON]
     */
    public String getPottersGeoJSONTripleStore(String potterName) {

        String jsonout = "";

        try {

            Sesame s = new Sesame("potter", potterName, "findspot2", "findspots");
            List<String[]> findspots = s.getFindspots();

            if (findspots.size() > 0) {

                jsonout = jsonout + "{\n";
                jsonout = jsonout + "\"type\": \"FeatureCollection\",\n";
                jsonout = jsonout + "\"features\": [\n";

                for (int i = 0; i < findspots.size(); i++) {

                    if (i == findspots.size() - 1) {
                        jsonout = jsonout + "{\"type\": \"Feature\",\n";
                        jsonout = jsonout + "\"properties\": {\n";
                        if (findspots.get(i)[1].equals("production")) {
                            jsonout = jsonout + "\"kilnsite\": \"" + "true" + "\",\n";
                        } else {
                            jsonout = jsonout + "\"kilnsite\": \"" + "false" + "\",\n";
                        }
                        jsonout = jsonout + "\"name\": \"" + findspots.get(i)[0] + "\"\n";
                        jsonout = jsonout + "},\n";
                        jsonout = jsonout + "\"geometry\": {\n";
                        jsonout = jsonout + "\"type\": \"Point\",\n";
                        jsonout = jsonout + "\"coordinates\": [" + findspots.get(i)[3] + ", " + findspots.get(i)[2] + "]\n"; //lon lat
                        jsonout = jsonout + "}\n";
                        jsonout = jsonout + "}\n";
                    } else {
                        jsonout = jsonout + "{\"type\": \"Feature\",\n";
                        jsonout = jsonout + "\"properties\": {\n";
                        if (findspots.get(i)[1].equals("production")) {
                            jsonout = jsonout + "\"kilnsite\": \"" + "true" + "\",\n";
                        } else {
                            jsonout = jsonout + "\"kilnsite\": \"" + "false" + "\",\n";
                        }
                        jsonout = jsonout + "\"name\": \"" + findspots.get(i)[0] + "\"\n";
                        jsonout = jsonout + "},\n";
                        jsonout = jsonout + "\"geometry\": {\n";
                        jsonout = jsonout + "\"type\": \"Point\",\n";
                        jsonout = jsonout + "\"coordinates\": [" + findspots.get(i)[3] + ", " + findspots.get(i)[2] + "]\n"; //lon lat
                        jsonout = jsonout + "}\n";
                        jsonout = jsonout + "},\n";
                    }

                }

                jsonout = jsonout + "]";
                jsonout = jsonout + "}";

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");
            return "an error occured";
        }

        return jsonout;

    }
}
