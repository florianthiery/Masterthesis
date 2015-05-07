package de.i3mainz.createOutput;

import de.i3mainz.dbutils.event.EventManager;
import de.i3mainz.dbutils.event.Fragment;
import de.i3mainz.dbutils.event.Potter;
import de.i3mainz.dbutils.event.Findspot;

import de.i3mainz.jaxb.level1.Collection;
import de.i3mainz.jaxb.level1.CollsColl;
import de.i3mainz.jaxb.level2.CollRes;
import de.i3mainz.jaxb.level2.Resources;
import de.i3mainz.jaxb.level3.CollResRes;
import de.i3mainz.jaxb.level3.Resource;
import de.i3mainz.jaxb.level3.Resources2;
import de.i3mainz.jaxb.level4.CollResResResFragment;
import de.i3mainz.jaxb.level4.FragmentRes;
import de.i3mainz.jaxb.level4.PotterRes;
import de.i3mainz.jaxb.level4.ResourcesFragment;
import de.i3mainz.jaxb.level4.FindspotRes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Klasse zum Erzeugen von JAXB XML Elementen
 *
 * @author Florian Thiery
 */
public class CreateXML {

    private String url_string = "";
    private String collection_string = "samian";
    private String resource_potters_string = "potters";
    private String resource_fragments_string = "fragments";
    private String resource_findspots_string = "findspots";
    private EventManager mgr = new EventManager();
    private String type = "";

    /**
     * Konstruktor [baseURI]
     */
    public CreateXML(String baseURI) {
        this.url_string = baseURI;
    }

    /**
     * Konstruktor [baseURI, Type]
     */
    public CreateXML(String baseURI, String type) {
        this.type = type;
    }

    /**
     * Creates JAXB Object with Collections and Collection Tag
     *
     * @return CollsCol
     */
    public CollsColl getCollsColl() {

        Collection coll = new Collection();
        coll.setName(collection_string);
        coll.setHREF(url_string + collection_string);

        CollsColl colls = new CollsColl();
        colls.setCollection(coll);

        return colls;

    }

    /**
     * Creates JAXB Object with Collection and Resources Tag
     *
     * @return CollRes[Resources]
     */
    public CollRes getCollRes() {

        ArrayList<Resources> resources = new ArrayList<Resources>();

        List<String> resourcesList = new ArrayList<String>();
        resourcesList.add("potters");
        resourcesList.add("findspots");
        resourcesList.add("fragments");

        for (int i = 0; i < resourcesList.size(); i++) {
            Resources res = new Resources();
            res.setID(resourcesList.get(i).toString());
            res.setHREF(url_string + collection_string + "/" + resourcesList.get(i).toString());
            resources.add(res);
        }

        CollRes cr = new CollRes();
        cr.setID(collection_string);
        cr.setHREF(url_string + collection_string);
        cr.setResources(resources);

        return cr;

    }

    /**
     * Creates JAXB Object with Collection and Resources and Resource Tag
     * [Potters]
     *
     * @return CollResRes[Potters]
     */
    public CollResRes getCollResResPotters() {

        ArrayList<Resource> resource_potter = new ArrayList<Resource>();

        //Verbindung mit der Datenbank get Liste von Potter-Objekten
        List<Potter> potters = mgr.getPotters();

        // Liste von Strings[Name] der Resource aus der Datenbank
        List<String> potterList = new ArrayList<String>();
        for (int i = 0; i < potters.size(); i++) {
            String temp = potters.get(i).getName().toString();
            temp = temp.replace(" ", "_");
            potterList.add(temp);
        }

        // Resource mit Namen füllen
        for (int i = 0; i < potterList.size(); i++) {
            Resource res = new Resource();
            res.setID(potterList.get(i).toString());
            res.setHREF(url_string + collection_string + "/" + resource_potters_string + "/" + potterList.get(i).toString());
            resource_potter.add(res);
        }

        Resources2 r = new Resources2();
        r.setID(resource_potters_string);
        r.setHREF(url_string + collection_string + "/" + resource_potters_string);
        r.setResource(resource_potter);

        CollResRes crr = new CollResRes();
        crr.setID(collection_string);
        crr.setHREF(url_string + collection_string);
        crr.setResources(r);

        return crr;

    }

    /**
     * Creates JAXB Object with Collection and Resources and Resource Tag
     * [Fragments]
     *
     * @return CollResRes[Fragments]
     */
    public CollResRes getCollResResFragments() {

        ArrayList<Resource> resource_fragments = new ArrayList<Resource>();

        //Verbindung mit der Datenbank get Liste von Fragment-Objekten
        EventManager mgr = new EventManager();
        List<Fragment> fragments = mgr.getFragments();

        // Liste von Strings[ID] der Resource aus der Datenbank
        List<String> fragmentList = new ArrayList<String>();
        for (int i = 0; i < fragments.size(); i++) {
            fragmentList.add(String.valueOf(fragments.get(i).getId()));
        }

        // Resource mit Namen füllen
        for (int i = 0; i < fragmentList.size(); i++) {
            Resource res = new Resource();
            res.setID(fragmentList.get(i).toString());
            res.setHREF(url_string + collection_string + "/" + resource_fragments_string + "/" + fragmentList.get(i).toString());
            resource_fragments.add(res);
        }

        Resources2 r = new Resources2();
        r.setID(resource_fragments_string);
        r.setHREF(url_string + collection_string + "/" + resource_fragments_string);
        r.setResource(resource_fragments);

        CollResRes crr = new CollResRes();
        crr.setID(collection_string);
        crr.setHREF(url_string + collection_string);
        crr.setResources(r);

        return crr;

    }

    /**
     * Creates JAXB Object with Collection and Resources and Resource Tag
     * [Findspots]
     *
     * @return CollResRes[Findspots]
     */
    public CollResRes getCollResResFindspots() {

        ArrayList<Resource> resource_findspots = new ArrayList<Resource>();

        //Verbindung mit der Datenbank get Liste von Fragment-Objekten
        EventManager mgr = new EventManager();
        List<Findspot> findspots = mgr.getFindspots();

        // Liste von Strings[Name] der Resource aus der Datenbank
        List<String> findspotList = new ArrayList<String>();
        for (int i = 0; i < findspots.size(); i++) {
            String temp = findspots.get(i).getName().toString();
            temp = temp = temp.replace(" ", "_");
            findspotList.add(temp);
        }

        // Resource mit Namen füllen
        for (int i = 0; i < findspotList.size(); i++) {
            Resource res = new Resource();
            res.setID(findspotList.get(i).toString());
            res.setHREF(url_string + collection_string + "/" + resource_findspots_string + "/" + findspotList.get(i).toString());
            resource_findspots.add(res);
        }

        Resources2 r = new Resources2();
        r.setID(resource_findspots_string);
        r.setHREF(url_string + collection_string + "/" + resource_findspots_string);
        r.setResource(resource_findspots);

        CollResRes crr = new CollResRes();
        crr.setID(collection_string);
        crr.setHREF(url_string + collection_string);
        crr.setResources(r);

        return crr;

    }

    /**
     * Creates MADS XML with Potter Content
     *
     * @return String[PotterXML]
     */
    public String getPotterMIDASXML(String potterName) {

        String temp = potterName;
        temp = temp.replace("_", " ");

        // einfacher XML output

//        String resource_string = mgr.getPotterByName(temp).getName();
//
//        PotterRes res = new PotterRes();
//        res.setID(potterName);
//        res.setHREF(url_string + collection_string + "/" + resource_potters_string + "/" + potterName);
//        res.setPottername(resource_string);
//
//        ResourcesPotter r = new ResourcesPotter();
//        r.setID(resource_potters_string);
//        r.setHREF(url_string + collection_string + "/" + resource_findspots_string);
//        r.setResource(res);
//
//        CollResResResPotter crrr = new CollResResResPotter();
//        crrr.setID(collection_string);
//        crrr.setHREF(url_string + collection_string);
//        crrr.setResources(r);

        // MADS

//        String potterXML = "";
//        
//        potterXML = potterXML + "<mads:mads version=\"2.0\" xsi:schemaLocation=\"http://www.loc.gov/mads/v2 http://www.loc.gov/standards/mads/mads-2-0.xsd\" xmlns:mads=\"http://www.loc.gov/mads/v2\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n";
//        potterXML = potterXML + "  <mads:identifier>"+temp+"</mads:identifier>\n";
//        potterXML = potterXML + "  <mads:authority>\n";
//        potterXML = potterXML + "    <mads:name type=\"personal\">\n";
//        potterXML = potterXML + "      <mads:namePart type=\"given\">"+temp+"</mads:namePart>\n";
//        potterXML = potterXML + "    </mads:name>\n";
//        potterXML = potterXML + "  </mads:authority>\n";
//        potterXML = potterXML + "  <mads:extension>\n";
//        potterXML = potterXML + "    <mads:profession>\n";
//        potterXML = potterXML + "      <mads:professionTerm>Potter</mads:professionTerm>\n";
//        potterXML = potterXML + "    </mads:profession>\n";
//        potterXML = potterXML + "  </mads:extension>\n";
//        potterXML = potterXML + "  <mads:extension>\n";
//        potterXML = potterXML + "    <mads:gender>\n";
//        potterXML = potterXML + "      <mads:genderTerm>male</mads:genderTerm>\n";
//        potterXML = potterXML + "    </mads:gender>\n";
//        potterXML = potterXML + "  </mads:extension>\n";
//        potterXML = potterXML + "</mads:mads>";

        // MIDAS

        String potterXML = "";

        potterXML = potterXML + "<actors schemaLocation=\"http://www.heritage-standards.org.uk/midas/schema/2.0 http://www.heritage-standards.org.uk/midas/schema/2.0/midas_actor.xsd\">\n";
        potterXML = potterXML + "  <actor>\n";
        potterXML = potterXML + "    <role>Potter</role>\n";
        potterXML = potterXML + "    <appellation>\n";
        potterXML = potterXML + "      <name preferred=\"id\" type=\"id\">" + temp + "</name>\n";
        potterXML = potterXML + "      <identifier type=\"uri\">" + url_string + "samian/potters/" + temp + "</identifier>\n";
        potterXML = potterXML + "    </appellation>\n";
        potterXML = potterXML + "    <contact/>\n";
        potterXML = potterXML + "    <temporal>\n";
        potterXML = potterXML + "      <span>\n";
        potterXML = potterXML + "        <display>\n";
        potterXML = potterXML + "          <appellation type=\"period\">Roman</appellation>\n";
        potterXML = potterXML + "        </display>\n";
        potterXML = potterXML + "        <start>\n";
        potterXML = potterXML + "          <appellation type=\"date\" qualifier=\"circa\">-30</appellation>\n";
        potterXML = potterXML + "        </start>\n";
        potterXML = potterXML + "        <end>\n";
        potterXML = potterXML + "          <appellation type=\"date\" qualifier=\"circa\">300</appellation>>\n";
        potterXML = potterXML + "        </end>\n";
        potterXML = potterXML + "      </span>\n";
        potterXML = potterXML + "    </temporal>\n";
        potterXML = potterXML + "  </actor>\n";
        potterXML = potterXML + "</actors>\n";

        return potterXML;

    }

    /**
     * Creates Costum JSON Potter
     *
     * @return String[JSON]
     */
    public String getPotterCostumJSON(String potterName) {

        String temp = potterName;
        temp = temp.replace("_", " ");

        String potterJSON = "";

        potterJSON = potterJSON + "{\n";
        potterJSON = potterJSON + "    \"potter\": {\n";
        potterJSON = potterJSON + "        \"name\": \"" + temp + "\",\n";
        potterJSON = potterJSON + "        \"uri\": \"" + url_string + "samian/potters/" + temp + "\",\n";
        potterJSON = potterJSON + "        \"attributes\": {\n";
        potterJSON = potterJSON + "            \"profession\": \"Potter\",\n";
        potterJSON = potterJSON + "            \"name\": \"" + temp + "\",\n";
        potterJSON = potterJSON + "            \"gender\": \"male\",\n";
        potterJSON = potterJSON + "            \"time\": {\n";
        potterJSON = potterJSON + "                \"period\": \"Roman\",\n";
        potterJSON = potterJSON + "                \"start\": \"-30\",\n";
        potterJSON = potterJSON + "                \"end\": \"300\"\n";
        potterJSON = potterJSON + "            }\n";
        potterJSON = potterJSON + "        }\n";
        potterJSON = potterJSON + "    }\n";
        potterJSON = potterJSON + "}";

        return potterJSON;

    }

    /**
     * Creates GML / geoJSON with Findspot Content from Geoserver
     *
     * @return String[FindspotGeometry]
     */
    public String getFindspotGeometry(String findspotName) {

        String temp = findspotName;
        temp = temp.replace("_", " ");

        // einfacher XML output

        //String resource_string = mgr.getFindspotByName(temp).getName();
        //FindspotRes res = new FindspotRes();
        //res.setID(findspotName);
        //res.setHREF(url_string + collection_string + "/" + resource_findspots_string + "/" + findspotName);
        //res.setName(resource_string);
        //res.setDateMax(mgr.getFindspotByName(resource_string).getDatemax());
        //res.setDateMin(mgr.getFindspotByName(resource_string).getDatemin());
        //res.setKilnsite(mgr.getFindspotByName(resource_string).getKilnsite());
        //res.setLocation(mgr.getFindspotByName(resource_string).getLocation().toText());

        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:findspot";

        if (type.equals("GML2")) {
            wfsurl = wfsurl + "&outputFormat=GML2";
        } else if (type.equals("GML3.1")) {
            wfsurl = wfsurl + "&outputFormat=text/xml%3B%20subtype=gml/3.1.1";
        } else if (type.equals("GML3.2")) {
            wfsurl = wfsurl + "&outputFormat=text/xml%3B%20subtype=gml/3.2";
        } else if (type.equals("geoJSON")) {
            wfsurl = wfsurl + "&outputFormat=json";
        }

        wfsurl = wfsurl + "&Filter=%3CFilter%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ename%3C/PropertyName%3E%3CLiteral%3E" + findspotName + "%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/Filter%3E";

        String geom = "";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geom = geom + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("made it here");
        }

        //res.setGML(text);
        //res.setGML(text);

        //ResourcesFindspot r = new ResourcesFindspot();
        //r.setID(resource_findspots_string);
        //r.setHREF(url_string + collection_string + "/" + resource_findspots_string);
        //r.setResource(res);

        //CollResResResFindspot crrr = new CollResResResFindspot();
        //crrr.setID(collection_string);
        //crrr.setHREF(url_string + collection_string);
        //crrr.setResources(r);

        //return crrr;

        return geom;

    }

//    public CollResResResFragment getCollResResResFragment(int fragmentID) {
//
//        // einfacher XML output
//
//        String resource_string = String.valueOf(mgr.getFragmentByID(fragmentID).getId());
//
//        // Potter Tag
//        PotterRes pres = new PotterRes();
//        String temp = mgr.getFragmentByID(fragmentID).getPotter().getName();
//        temp = temp.replace(" ", "_");
//        pres.setID(temp);
//        pres.setHREF(url_string + collection_string + "/" + resource_potters_string + "/" + temp);
//        pres.setPottername(mgr.getFragmentByID(fragmentID).getPotter().getName());
//
//        // Findspot Tag
//        FindspotRes sres = new FindspotRes();
//        String temp2 = mgr.getFragmentByID(fragmentID).getFindspot().getName();
//        temp2 = temp2.replace(" ", "_");
//        sres.setID(temp2);
//        sres.setHREF(url_string + collection_string + "/" + resource_findspots_string + "/" + temp2);
////        sres.setDateMax(mgr.getFragmentByID(fragmentID).getFindspot().getDatemax());
////        sres.setDateMin(mgr.getFragmentByID(fragmentID).getFindspot().getDatemin());
//        sres.setKilnsite(mgr.getFragmentByID(fragmentID).getFindspot().getKilnsite());
//        sres.setLocation(mgr.getFragmentByID(fragmentID).getFindspot().getLocation().toText());
//        sres.setName(mgr.getFragmentByID(fragmentID).getFindspot().getName());
//
//        FragmentRes res = new FragmentRes();
//        res.setID(resource_string);
//        res.setHREF(url_string + collection_string + "/" + resource_fragments_string + "/" + resource_string);
//        res.setDie(mgr.getFragmentByID(fragmentID).getDie());
//        res.setPotform(mgr.getFragmentByID(fragmentID).getPotform());
//        res.setPotter(pres);
//        res.setFindspot(sres);
//
//        ResourcesFragment r = new ResourcesFragment();
//        r.setID(resource_potters_string);
//        r.setHREF(url_string + collection_string + "/" + resource_fragments_string);
//        r.setResource(res);
//
//        CollResResResFragment crrr = new CollResResResFragment();
//        crrr.setID(collection_string);
//        crrr.setHREF(url_string + collection_string);
//        crrr.setResources(r);
//
//        return crrr;
//
//    }

    /**
     * Get MIDAS XML of Fragment
     * @param FragmentID
     * @return 
     */
    public String getFragmentMIDASXML(int FragmentID) {

        // Datenbank Data

        Hashtable<String, String> data = mgr.getPotterFindspotDataByFragmentID(FragmentID);

        String potter = data.get("potterName");
        String findspot = data.get("findspotName");
        String lat = data.get("findspotLat");
        String lon = data.get("findspotLon");
        String die = data.get("fragmentDie");
        String potform = data.get("fragmentPotform");

        // MIDAS XML

        String fragmentXML = "";

        fragmentXML = fragmentXML + "<objects xmlns=\"http://www.heritage-standards.org.uk/midas/schema/2.0\" xsi=\"http://www.w3.org/2001/XMLSchema-instance\" schemaLocation=\"http://www.heritage-standards.org.uk/midas/schema/2.0\n"
                + "http://www.heritage-standards.org.uk/midas/schema/2.0/midas_object.xsd\">\n";
        fragmentXML = fragmentXML + "  <object>\n";
        fragmentXML = fragmentXML + "    <appellation>\n";
        fragmentXML = fragmentXML + "      <name preferred=\"id\" type=\"id\">" + FragmentID + "</name>\n";
        fragmentXML = fragmentXML + "      <identifier type=\"uri\" namespace=\"samian\">" + url_string + "samian/fragments/" + FragmentID + "</identifier>\n";
        fragmentXML = fragmentXML + "    </appellation>\n";
        fragmentXML = fragmentXML + "    <character>\n";
        fragmentXML = fragmentXML + "    <objecttype certainty=\"certain\">Terra Sigillata</objecttype>\n";
        fragmentXML = fragmentXML + "      <descriptions>\n";
        fragmentXML = fragmentXML + "        <description>\n";
        fragmentXML = fragmentXML + "          <full/>\n";
        fragmentXML = fragmentXML + "          <summary/>\n";
        fragmentXML = fragmentXML + "        </description>\n";
        fragmentXML = fragmentXML + "      </descriptions>\n";
        fragmentXML = fragmentXML + "      <classifications>\n";
        fragmentXML = fragmentXML + "        <classification namespace=\"samian\">Potform</classification>\n";
        fragmentXML = fragmentXML + "        <classification>" + potform + "</classification>\n";
        fragmentXML = fragmentXML + "      </classifications>\n";
        fragmentXML = fragmentXML + "      <manufacture>\n";
        fragmentXML = fragmentXML + "        <materials>\n";
        fragmentXML = fragmentXML + "          <material>Pottery</material>\n";
        fragmentXML = fragmentXML + "          <material>Terra Sigillata</material>\n";
        fragmentXML = fragmentXML + "        </materials>\n";
        fragmentXML = fragmentXML + "        <technique>pottery</technique>\n";
        fragmentXML = fragmentXML + "        <temporal>\n";
        fragmentXML = fragmentXML + "          <span>\n";
        fragmentXML = fragmentXML + "            <display>\n";
        fragmentXML = fragmentXML + "              <appellation type=\"period\">Roman</appellation>\n";
        fragmentXML = fragmentXML + "            </display>\n";
        fragmentXML = fragmentXML + "            <start>\n";
        fragmentXML = fragmentXML + "              <appellation type=\"date\" qualifier=\"circa\">-30</appellation>\n";
        fragmentXML = fragmentXML + "            </start>\n";
        fragmentXML = fragmentXML + "            <end>\n";
        fragmentXML = fragmentXML + "              <appellation type=\"date\" qualifier=\"circa\">300</appellation>>\n";
        fragmentXML = fragmentXML + "            </end>\n";
        fragmentXML = fragmentXML + "          </span>\n";
        fragmentXML = fragmentXML + "        </temporal>\n";
        fragmentXML = fragmentXML + "        <spatial/>\n";
        fragmentXML = fragmentXML + "        <actor>\n";
        fragmentXML = fragmentXML + "          <role>Potter</role>\n";
        fragmentXML = fragmentXML + "          <appellation>\n";
        fragmentXML = fragmentXML + "            <name preferred=\"id\" type=\"id\">" + potter + "</name>\n";
        fragmentXML = fragmentXML + "            <identifier type=\"uri\">" + url_string + "samian/potters/" + potter + "</identifier>\n";
        fragmentXML = fragmentXML + "          </appellation>\n";
        fragmentXML = fragmentXML + "          <contact/>\n";
        fragmentXML = fragmentXML + "          <temporal>\n";
        fragmentXML = fragmentXML + "            <span>\n";
        fragmentXML = fragmentXML + "              <display>\n";
        fragmentXML = fragmentXML + "                <appellation type=\"period\">Roman</appellation>\n";
        fragmentXML = fragmentXML + "              </display>\n";
        fragmentXML = fragmentXML + "              <start>\n";
        fragmentXML = fragmentXML + "                <appellation type=\"date\" qualifier=\"circa\">-30</appellation>\n";
        fragmentXML = fragmentXML + "              </start>\n";
        fragmentXML = fragmentXML + "              <end>\n";
        fragmentXML = fragmentXML + "                <appellation type=\"date\" qualifier=\"circa\">300</appellation>\n";
        fragmentXML = fragmentXML + "              </end>\n";
        fragmentXML = fragmentXML + "            </span>\n";
        fragmentXML = fragmentXML + "          </temporal>\n";
        fragmentXML = fragmentXML + "        </actor>\n";
        fragmentXML = fragmentXML + "      </manufacture>\n";
        fragmentXML = fragmentXML + "      <measurements/>\n";
        fragmentXML = fragmentXML + "        <decorations>\n";
        fragmentXML = fragmentXML + "            <decoration type=\"die\">" + die + "</decoration>\n";
        fragmentXML = fragmentXML + "          <decoration>die</decoration>\n";
        fragmentXML = fragmentXML + "        </decorations>\n";
        fragmentXML = fragmentXML + "        </character>\n";
        fragmentXML = fragmentXML + "        <condition>\n";
        fragmentXML = fragmentXML + "        <state>good</state>\n";
        fragmentXML = fragmentXML + "        <completeness>fragment</completeness>\n";
        fragmentXML = fragmentXML + "        </condition>\n";
        fragmentXML = fragmentXML + "        <discovery>\n";
        fragmentXML = fragmentXML + "        <actor/>\n";
        fragmentXML = fragmentXML + "        <spatial>\n";
        fragmentXML = fragmentXML + "        <place>\n";
        fragmentXML = fragmentXML + "        <address>\n";
        fragmentXML = fragmentXML + "        <streetaddress/>\n";
        fragmentXML = fragmentXML + "        <city/>\n";
        fragmentXML = fragmentXML + "        <adminarea/>\n";
        fragmentXML = fragmentXML + "        <postcode/>\n";
        fragmentXML = fragmentXML + "        <country/>\n";
        fragmentXML = fragmentXML + "        </address>\n";
        fragmentXML = fragmentXML + "        <namedplace>\n";
        fragmentXML = fragmentXML + "        <location type=\"locality\">" + findspot + "</location>\n";
        fragmentXML = fragmentXML + "        </namedplace>\n";
        fragmentXML = fragmentXML + "        <gridref/>\n";
        fragmentXML = fragmentXML + "        <geopolitical/>\n";
        fragmentXML = fragmentXML + "        <cadastral/>\n";
        fragmentXML = fragmentXML + "        <directions/>\n";
        fragmentXML = fragmentXML + "        </place>\n";
        fragmentXML = fragmentXML + "        <geometry>\n";
        fragmentXML = fragmentXML + "        <boundingBox srs=\"EPSG:4326\" minx=\"" + lat + "\" maxy=\"" + lon + "\" miny=\"" + lon + "\" maxx=\"" + lat + "\"/>\n";
        fragmentXML = fragmentXML + "        <spatialappellation type=\"centrepoint\">\n";
        fragmentXML = fragmentXML + "        <quickpoint>\n";
        fragmentXML = fragmentXML + "        <srs>EPSG:4326</srs>\n";
        fragmentXML = fragmentXML + "        <x>8.933333397</x>\n";
        fragmentXML = fragmentXML + "        <y>51.28333282</y>\n";
        fragmentXML = fragmentXML + "        </quickpoint>\n";
        fragmentXML = fragmentXML + "        <entity spatialtype=\"Point\" uri=\"" + url_string + "samian/findspots/" + findspot + "\" namespace=\"samian\">\n";
        fragmentXML = fragmentXML + "        <wkt srs=\"EPSG:4326\">POINT(" + lat + " " + lon + ")</wkt>\n";
        fragmentXML = fragmentXML + "        </entity>\n";
        fragmentXML = fragmentXML + "        <capturemethod/>\n";
        fragmentXML = fragmentXML + "        <source/>\n";
        fragmentXML = fragmentXML + "        </spatialappellation>\n";
        fragmentXML = fragmentXML + "        </geometry>\n";
        fragmentXML = fragmentXML + "        </spatial>\n";
        fragmentXML = fragmentXML + "        <temporal/>\n";
        fragmentXML = fragmentXML + "        <method/>\n";
        fragmentXML = fragmentXML + "    <circumstance/>\n";
        fragmentXML = fragmentXML + "    </discovery>\n";
        fragmentXML = fragmentXML + "    <quantity>1</quantity>\n";
        fragmentXML = fragmentXML + "    <related/>\n";
        fragmentXML = fragmentXML + "    <objectannex/>\n";
        fragmentXML = fragmentXML + "  </object>\n";
        fragmentXML = fragmentXML + "</objects>\n";

        return fragmentXML;

    }
    
    /**
     * Creates Costum JSON
     *
     * @return String[JSON]
     */
    public String getFragmentCostumJSON(int FragmentID) {

        // Datenbank Data
        
        Hashtable<String, String> data = mgr.getPotterFindspotDataByFragmentID(FragmentID);

        String potterName = data.get("potterName");
        String findspotName = data.get("findspotName");
        String lat = data.get("findspotLat");
        String lon = data.get("findspotLon");
        String die = data.get("fragmentDie");
        String potform = data.get("fragmentPotform");

        String fragmentJSON = "";

        fragmentJSON = fragmentJSON + "{\n";
        fragmentJSON = fragmentJSON + "    \"fragment\": {\n";
        fragmentJSON = fragmentJSON + "        \"id\": \"" + FragmentID + "\",\n";
        fragmentJSON = fragmentJSON + "        \"uri\": \"" + url_string + "samian/fragments/" + FragmentID + "\",\n";
        fragmentJSON = fragmentJSON + "        \"attributes\": {\n";
        fragmentJSON = fragmentJSON + "            \"material\": \"pottery\",\n";
        fragmentJSON = fragmentJSON + "            \"classification\": \"samian ware\",\n";
        fragmentJSON = fragmentJSON + "            \"id\": \"" + FragmentID + "\",\n";
        fragmentJSON = fragmentJSON + "            \"die\": \"" + die + "\",\n";
        fragmentJSON = fragmentJSON + "            \"potform\": \"" + potform + "\",\n";
        fragmentJSON = fragmentJSON + "            \"time\": {\n";
        fragmentJSON = fragmentJSON + "                \"period\": \"Roman\",\n";
        fragmentJSON = fragmentJSON + "                \"start\": \"-30\",\n";
        fragmentJSON = fragmentJSON + "                \"end\": \"300\"\n";
        fragmentJSON = fragmentJSON + "            }\n";
        fragmentJSON = fragmentJSON + "            \"stampedBy\": \"" + potterName + "\",\n";
        fragmentJSON = fragmentJSON + "            \"findspot\": {\n";
        fragmentJSON = fragmentJSON + "                \"name\": \""+findspotName+"\",\n";
        fragmentJSON = fragmentJSON + "                \"lat\": \""+lat+"\",\n";
        fragmentJSON = fragmentJSON + "                \"lon\": \""+lon+"\"\n";
        fragmentJSON = fragmentJSON + "            }\n";
        fragmentJSON = fragmentJSON + "        }\n";
        fragmentJSON = fragmentJSON + "    }\n";
        fragmentJSON = fragmentJSON + "}";

        return fragmentJSON;

    }
}