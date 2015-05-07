package de.i3mainz.createOutput;

import de.i3mainz.dbutils.event.EventManager;
import de.i3mainz.dbutils.event.Fragment;
import de.i3mainz.dbutils.event.Potter;
import de.i3mainz.dbutils.event.Findspot;
import de.i3mainz.html.HTML;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasse zum Erzeugen von HTML Elementen
 *
 * @author Florian Thiery
 */
public class CreateHTML {

    private String url_string = "";
    private String collection_string = "samian";
    private String resource_potters_string = "potters";
    private String resource_fragments_string = "fragments";
    private String resource_findspots_string = "findspots";
    private EventManager mgr = new EventManager();

    /**
     * Konstruktor
     */
    public CreateHTML(String baseURI) {
        this.url_string = baseURI;
    }

    
    /**
     * Creates HTML Object with Collections and Collection
     *
     * @return String[HTML]
     */
    public String getHTMLlevel1() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string);
        html.setResourcesURL(url_string + collection_string, "Samian");

        htmlout = htmlout + html.Header(url_string);
        htmlout = htmlout + html.Body();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources
     *
     * @return String[HTML]
     */
    public String getHTMLlevel2() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/potters", "Potters");
        html.setResourcesURL(url_string + collection_string + "/findspots", "Findspots");
        html.setResourcesURL(url_string + collection_string + "/fragments", "Fragments");

        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.Body();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource
     *
     * @return String[HTML]
     */
    public String getHTMLlevel3_Potters() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/potters", "Potters");
        
        List<Potter> potters = mgr.getPotters();
        List<String> potterList = new ArrayList<String>();
        for (int i = 0; i < potters.size(); i++) {
            String temp = potters.get(i).getName().toString();
            temp = temp.replace(" ", "_");
            potterList.add(temp);
        }
        for (int i = 0; i < potterList.size(); i++) {
            html.setResourceURL(url_string + collection_string + "/potters/" + potterList.get(i).toString(), potterList.get(i).toString());
        }
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.Body();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource
     *
     * @return String[HTML]
     */
    public String getHTMLlevel3_Fragments() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/fragments", "Fragments");
        
        List<Fragment> fragments = mgr.getFragments();
        List<String> fragmentList = new ArrayList<String>();
        for (int i = 0; i < fragments.size(); i++) {
            fragmentList.add(String.valueOf(fragments.get(i).getId()));
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            html.setResourceURL(url_string + collection_string + "/fragments/" + fragmentList.get(i).toString(), fragmentList.get(i).toString());
        }
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.Body();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with a Warning
     *
     * @return String[HTML]
     */
    public String getHTMLlevel3_Fragments_Warning() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/fragments", "Fragments");
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.BodyFragmentWarning();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource
     *
     * @return String[HTML]
     */
    public String getHTMLlevel3_Findspots() {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/findspots", "Findspots");
        
        List<Findspot> potters = mgr.getFindspots();
        List<String> findspotList = new ArrayList<String>();
        for (int i = 0; i < potters.size(); i++) {
            String temp = potters.get(i).getName().toString();
            temp = temp.replace(" ", "_");
            findspotList.add(temp);
        }
        for (int i = 0; i < findspotList.size(); i++) {
            html.setResourceURL(url_string + collection_string + "/findspots/" + findspotList.get(i).toString(), findspotList.get(i).toString());
        }
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.Body();
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource and Content
     *
     * @return String[HTML]
     */
    public String getHTMLlevel4_Potter(String potterName) {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/potters", "Potters");
        html.setResourceURL(url_string + collection_string + "/potters/" + potterName, potterName);
        
        String temp = potterName;
        temp = temp.replace("_", " ");
        html.setPotterName(mgr.getPotterByName(temp).getName());
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.BodyPotter();
        htmlout = htmlout + html.Representations(url_string + collection_string + "/potters/" + potterName);
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource and Content
     *
     * @return String[HTML]
     */
    public String getHTMLlevel4_Findspot(String findspotName) {

        String htmlout = "";
        HTML html = new HTML();
        String findspotNameWhitespace = findspotName; // findspot Name mit Whitespace und Underscore (for Database)
        findspotNameWhitespace = findspotNameWhitespace.replace("__", "#");
        findspotNameWhitespace = findspotNameWhitespace.replace("_", " ");
        findspotNameWhitespace = findspotNameWhitespace.replace("#", "__");

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/findspots", "Findspots");
        html.setResourceURL(url_string + collection_string + "/findspots/" + findspotName, findspotName);
        
        html.setFindspotName(mgr.getFindspotByName(findspotNameWhitespace).getName());
        html.setFindspotLocation(mgr.getFindspotByName(findspotNameWhitespace).getLocation().toText());
        html.setFindspotKilnsite(mgr.getFindspotByName(findspotNameWhitespace).getKilnsite());
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.BodyFindspot();
        htmlout = htmlout + html.Representations(url_string + collection_string + "/findspots/" + findspotName);
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
    
    /**
     * Creates HTML Object with Collection and Resources and Resource and Content
     *
     * @return String[HTML]
     */
    public String getHTMLlevel4_Fragment(int fragmentID) {

        String htmlout = "";
        HTML html = new HTML();

        html.setPageURL(url_string + collection_string);
        html.setResourcesURL(url_string + collection_string + "/fragments", "Fragments");
        html.setResourceURL(url_string + collection_string + "/fragments/" + fragmentID, String.valueOf(fragmentID));
        
        Hashtable<String, String> data = mgr.getPotterFindspotDataByFragmentID(fragmentID);
        
        html.setFragmentID(data.get("fragmentID"));
        html.setFragmentDie(data.get("fragmentDie"));
        html.setFragmentPotform(data.get("fragmentPotform"));
        html.setFragmentNumber(Integer.parseInt(data.get("fragmentNumber")));
        
        html.setPotterName(data.get("potterName"));
        
        html.setFindspotName(data.get("findspotName"));
        html.setFindspotLocation(data.get("findspotLocation"));
        html.setFindspotKilnsite(Boolean.valueOf(data.get("findspot")));
        
        htmlout = htmlout + html.Header(url_string + collection_string);
        htmlout = htmlout + html.BodyFragment();
        htmlout = htmlout + html.Representations(url_string + collection_string + "/fragments/" + fragmentID);
        htmlout = htmlout + html.Footer();

        return htmlout;

    }

    /**
     * Create Error String
     * @param error
     * @return 
     */
    public String getHTMLerror(String error) {

        HTML html = new HTML();
        String htmlout = "";

        htmlout = htmlout + html.Header("Error");
        htmlout = htmlout + html.BodyError(error);
        htmlout = htmlout + html.Footer();

        return htmlout;

    }
}
