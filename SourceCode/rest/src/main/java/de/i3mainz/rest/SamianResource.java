package de.i3mainz.rest;

import de.i3mainz.createOutput.CreateEXPLORER;
import de.i3mainz.createOutput.CreateHTML;
import de.i3mainz.createOutput.CreateRDFXML;
import de.i3mainz.createOutput.CreateXML;
import de.i3mainz.createOutput.CreateRDFturtle;
import de.i3mainz.jaxb.level2.CollRes;
import de.i3mainz.jaxb.level3.CollResRes;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * ReST-Klasse der Ressource "Samian"
 *
 * @author Florian Thiery
 */
@Path("/samian")
public class SamianResource {

    @Context
    UriInfo uriInfo;
    
    String filepath_pottersttl = "/usr/share/apache-tomcat-7.0.41/webapps/share/potters.ttl";
    String filepath_fragmentsttl = "/usr/share/apache-tomcat-7.0.41/webapps/share/fragments.ttl";   
    String filepath_findspotsttl = "/usr/share/apache-tomcat-7.0.41/webapps/share/findspots.ttl";

// Liste von Resourcen {host}/{rest}/{samian}
// <editor-fold defaultstate="collapsed" desc="Level2">
    /**
     * XML/JSON list of Resources of Collection Samian
     *
     * @return XML/JSON list of Resources of Collection Samian
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLevel2XMLJSON() {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            CollRes xml = cr.getCollRes();
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * HTML list of Resources of Collection Samian
     *
     * @return XML/JSON list of Resources of Collection Samian
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getLevel2HTML() {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel2();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

// </editor-fold>
// Liste von Resourcen {host}/{rest}/{resources}
// <editor-fold defaultstate="collapsed" desc="Level3">
    /**
     * XML/JSON list of Items[Potters]
     *
     * @return XML/JSON list of Items[Potters]
     */
    @GET
    @Path("potters")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLevel3XMLJSON_Potters() {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            CollResRes xml = cr.getCollResResPotters();
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * HTML list of Items[Potters]
     *
     * @return HTML list of Items[Potters]
     */
    @GET
    @Path("potters")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel3HTML_Potters() {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel3_Potters();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * Produces Turtle File of Items[Potters]
     *
     * @return Produces Turtle File of Items[Potters]
     */
    @GET
    @Path("potters.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String producePottersTTL() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getWHOISPotters();
            
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(filepath_pottersttl)));

            out.write(ttlout);
            out.close();
            System.out.println(filepath_pottersttl + "written");
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        // Schreibe alle Elemente als Textzeilen in die Datei:
        try {

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(filepath_pottersttl)));

            out.write(ttlout);
            out.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return ttlout;

    }

    /**
     * XML/JSON list of Items[Fragments]
     *
     * @return XML/JSON list of Items[Fragments]
     */
    @GET
    @Path("fragments")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLevel3XMLJSON_Fragments() {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            CollResRes xml = cr.getCollResResFragments();
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * HTML list of Items[Fragments]
     *
     * @return HTML list of Items[Fragments]
     */
    @GET
    @Path("fragments")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel3HTML_Fragments() {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            //htmlout = cr.getHTMLlevel3_Fragments();
            htmlout = cr.getHTMLlevel3_Fragments_Warning();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * Produces Turtle File of Items[Fragments]
     *
     * @return Produces Turtle File of Items[Fragments]
     */
    @GET
    @Path("fragments.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String produceFragmentsTTL() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getCIDOCCRMFragments();
            
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(filepath_fragmentsttl)));

            out.write(ttlout);
            out.close();
            System.out.println(filepath_fragmentsttl + "written");
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }
        
        // Schreibe alle Elemente als Textzeilen in die Datei:
        try {

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(filepath_fragmentsttl)));

            out.write(ttlout);
            out.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return ttlout;

    }

    /**
     * XML/JSON list of Items[Findspots]
     *
     * @return XML/JSON list of Items[Findspots]
     */
    @GET
    @Path("findspots")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLevel3XMLJSON_Findspots() {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            CollResRes xml = cr.getCollResResFindspots();
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * HTML list of Items[Findspots]
     *
     * @return HTML list of Items[Findspots]
     */
    @GET
    @Path("findspots")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel3HTML_Findspots() {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel3_Findspots();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * Produces Turtle File of Items[Findspots]
     *
     * @return Produces Turtle File of Items[Findspots]
     */
    @GET
    @Path("findspots.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String produceFindspotsTTL() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getPleiadesFindspots();
            
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(filepath_findspotsttl)));

            out.write(ttlout);
            out.close();
            System.out.println(filepath_findspotsttl + "written");
            
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return "export to /usr/share/apache-tomcat-7.0.41/webapps/share/findspots.ttl done";

    }


    /**
     * Turtle Annotations of findspots to Pleiades
     * @return Turtle Annotations of findspots to Pleiades
     */
    @GET
    @Path("pleiades")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPleiadesAnnotations() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getPleiadesAnnotations();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }

    /**
     * Turtle Annotations of agents
     * @return 
     */
    @GET
    @Path("agents")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAgents() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getAgents();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }
    
    /**
     * All Annotations
     * @return All Annotations
     */
    @GET
    @Path("annotations")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAnnotations() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getAnnotations();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }
    
    /**
     * All Annotations
     * @return All Annotations
     */
    @GET
    @Path("void.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String getVoid() {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getVoid();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }

// </editor-fold>
// Liste von Resourcen {host}/{rest}/{resources}/{resource}
// <editor-fold defaultstate="collapsed" desc="Level4:Potter">
    /**
     * XML Representation [Potter]
     *
     * @return XML Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4XML_PotterXML(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getPotterMIDASXML(potterName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * XML Representation [Potter]
     *
     * @return XML Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}.xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4XML_PotterXML2(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getPotterMIDASXML(potterName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * JSON Representation [Potter]
     *
     * @return JSON Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4XML_PotterJSON(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getPotterCostumJSON(potterName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * JSON Representation [Potter]
     *
     * @return JSON Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4XML_PotterJSON2(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getPotterCostumJSON(potterName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * HTML Representation [Potter]
     *
     * @return HTML Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel4HTML_Potter(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel4_Potter(potterName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * Turtle Representation [Potter]
     *
     * @return Turtle Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLevel4TTL_Potter(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getWHOIS(potterName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }

    /**
     * RDF/XML Representation [Potter]
     *
     * @return RDF/XML Representation [Potter]
     */
    @GET
    @Path("potters/{potterName}.rdf")
    @Produces(MediaType.APPLICATION_XML)
    public String getLevel4RDF_Potter(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String rdfout = "";

        try {
            CreateRDFXML rdf = new CreateRDFXML(baseURI);
            rdfout = rdf.getWHOIS(potterName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            rdfout = cr.getHTMLerror(error404);
        }

        return rdfout;

    }

    /**
     * GeoJSON of Findspots where Potters worked/exported
     *
     * @return GeoJSON of Findspots where Potters worked/exported
     */
    @GET
    @Path("potters/{potterName}.exp2")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLevel4EXP2_Potter(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String expout = "";

        try {
            CreateEXPLORER exp = new CreateEXPLORER(baseURI);
            expout = exp.getPottersGeoJSONDatabase(potterName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            expout = cr.getHTMLerror(error404);
        }

        return expout;

    }
    
    /**
     * GeoJSON of Findspots where Potters worked/exported
     *
     * @return GeoJSON of Findspots where Potters worked/exported
     */
    @GET
    @Path("potters/{potterName}.exp")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLevel4EXP_Potter(@PathParam("potterName") String potterName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String expout = "";

        try {
            CreateEXPLORER exp = new CreateEXPLORER(baseURI);
            expout = exp.getPottersGeoJSONTripleStore(potterName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            expout = cr.getHTMLerror(error404);
        }

        return expout;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Level4:Findspot">
    /**
     * XML Representation [Findspot]
     *
     * @return XML Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4GML_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI, "GML3.1");
            String xml = cr.getFindspotGeometry(findspotName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * XML Representation [Findspot]
     *
     * @return XML Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}.xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4GML_Findspot2(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI, "GML3.1");
            String xml = cr.getFindspotGeometry(findspotName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * JSON Representation [Findspot]
     *
     * @return JSON Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4GeoJSON_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI, "geoJSON");
            String xml = cr.getFindspotGeometry(findspotName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * JSON Representation [Findspot]
     *
     * @return JSON Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4GeoJSON_Findspot2(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI, "geoJSON");
            String xml = cr.getFindspotGeometry(findspotName);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * Turtle Representation [Findspot]
     *
     * @return Turtle Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLevel4TTL_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getPlaiades(findspotName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }

    /**
     * RDF/XML Representation [Findspot]
     *
     * @return RDF/XML Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}.rdf")
    @Produces(MediaType.APPLICATION_XML)
    public String getLevel4RDF_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String rdfout = "";

        try {
            CreateRDFXML rdf = new CreateRDFXML(baseURI);
            rdfout = rdf.getPlaiades(findspotName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            rdfout = cr.getHTMLerror(error404);
        }

        return rdfout;

    }

    /**
     * HTML Representation [Findspot]
     *
     * @return HTML Representation [Findspot]
     */
    @GET
    @Path("findspots/{findspotName}")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel4HTML_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel4_Findspot(findspotName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * Get Findspot -Potters and their Fragments, -Fragments and its potter
     *
     * @return Explorer XML
     */
    @GET
    @Path("findspots/{findspotName}.exp2")
    @Produces(MediaType.APPLICATION_XML)
    public String getLevel4EXP2_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String expout = "";

        try {
            CreateEXPLORER exp = new CreateEXPLORER(baseURI);
            expout = exp.getPottersFragmentsDatabase(findspotName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            expout = cr.getHTMLerror(error404);
        }

        return expout;

    }
    
    /**
     * Get Findspot -Potters and their Fragments, -Fragments and its potter
     *
     * @return Explorer XML
     */
    @GET
    @Path("findspots/{findspotName}.exp")
    @Produces(MediaType.APPLICATION_XML)
    public String getLevel4EXP_Findspot(@PathParam("findspotName") String findspotName) {

        String baseURI = uriInfo.getBaseUri().toString();
        String expout = "";

        try {
            CreateEXPLORER exp = new CreateEXPLORER(baseURI);
            expout = exp.getPottersFragmentsTripleStore(findspotName);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            expout = cr.getHTMLerror(error404);
        }

        return expout;

    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Level4:Fragment">
    /**
     * HTML Representation [Fragment]
     *
     * @return HTML Representation [Fragment]
     */
    @GET
    @Path("fragments/{fragmentID}")
    @Produces(MediaType.TEXT_HTML)
    public String getLevel4HTML_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        String htmlout = "";

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel4_Fragment(fragmentID);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }

    /**
     * JSON Representation [Findspot]
     *
     * @return JSON Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4JSON2_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getFragmentCostumJSON(fragmentID);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * JSON Representation [Findspot]
     *
     * @return JSON Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLevel4JSON_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getFragmentCostumJSON(fragmentID);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * XML Representation [Findspot]
     *
     * @return XML Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}.xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4XML2_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getFragmentMIDASXML(fragmentID);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * XML Representation [Findspot]
     *
     * @return XML Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getLevel4XML_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        Response res = null;

        try {
            CreateXML cr = new CreateXML(baseURI);
            String xml = cr.getFragmentMIDASXML(fragmentID);
            res = Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            res = Response.status(404).build();
        }

        return res;

    }

    /**
     * Turtle Representation [Findspot]
     *
     * @return Turtle Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}.ttl")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLevel4TTL_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        String ttlout = "";

        try {
            CreateRDFturtle ttl = new CreateRDFturtle(baseURI);
            ttlout = ttl.getCIDOCCRMFragment(fragmentID);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            ttlout = cr.getHTMLerror(error404);
        }

        return ttlout;

    }

    /**
     * RDF/XML Representation [Findspot]
     *
     * @return RDF/XML Representation [Findspot]
     */
    @GET
    @Path("fragments/{fragmentID}.rdf")
    @Produces(MediaType.APPLICATION_XML)
    public String getLevel4RDF_Fragment(@PathParam("fragmentID") int fragmentID) {

        String baseURI = uriInfo.getBaseUri().toString();
        String rdfout = "";

        try {
            CreateRDFXML rdf = new CreateRDFXML(baseURI);
            rdfout = rdf.getCIDOCRM(fragmentID);
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            rdfout = cr.getHTMLerror(error404);
        }

        return rdfout;

    }

// </editor-fold>    
}
