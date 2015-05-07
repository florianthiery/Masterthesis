package de.i3mainz.getfeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Get GeoJSON as String from GeoServer
 * @author Florian Thiery
 */
public class GeoserverConnection {
    
    /**
     * Konstruktor
     */
    public GeoserverConnection() {
        
    }
    
    /**
     * Get all Findspots from Geoserver [Samian:findspot]
     * @return String [GeoJSON]
     */
    public String getAllFindspots() {
        
        String geojson = "";
        
        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:findspot";
        wfsurl = wfsurl + "&outputFormat=json";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geojson = geojson + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        
        return geojson;
        
    }
    
    /**
     * Get Kilnsite Findspots from Geoserver [Samian:findspot]
     * @return String [GeoJSON]
     */
    public String getKilnsiteFindspots() {
        
        String geojson = "";
        
        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:findspot";
        wfsurl = wfsurl + "&outputFormat=json";
        wfsurl = wfsurl + "&Filter=%3CFilter%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3E" + "kilnsite" + "%3C/PropertyName%3E%3CLiteral%3E" + "true" + "%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/Filter%3E";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geojson = geojson + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        
        return geojson;
        
    }
    
    /**
     * Get Export Findspots from Geoserver [Samian:findspot]
     * @return String [GeoJSON]
     */
    public String getExportFindspots() {
        
        String geojson = "";
        
        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:findspot";
        wfsurl = wfsurl + "&outputFormat=json";
        wfsurl = wfsurl + "&Filter=%3CFilter%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3E" + "kilnsite" + "%3C/PropertyName%3E%3CLiteral%3E" + "false" + "%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/Filter%3E";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geojson = geojson + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        
        return geojson;
        
    }
    
    /**
     * Get Places Findspots from Geoserver [Samian:places]
     * @return String [GeoJSON]
     */
    public String getPlacesFindspots() {
        
        String geojson = "";
        
        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:places";
        wfsurl = wfsurl + "&outputFormat=json";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geojson = geojson + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        
        return geojson;
        
    }
    
    /**
     * Get Location Findspots from Geoserver [Samian:locations]
     * @return String [GeoJSON]
     */
    public String getLocationsFindspots() {
        
        String geojson = "";
        
        String wfsurl = "http://143.93.114.104/geoserver/Samian/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=Samian:locations";
        wfsurl = wfsurl + "&outputFormat=json";

        try {

            URL url = new URL(wfsurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                geojson = geojson + line;
            }

            conn.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        
        return geojson;
        
    }
    
}
