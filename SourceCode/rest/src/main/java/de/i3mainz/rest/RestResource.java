package de.i3mainz.rest;

import de.i3mainz.createOutput.CreateHTML;
import de.i3mainz.createOutput.CreateXML;
import de.i3mainz.jaxb.level1.CollsColl;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * ReST-Klasse der Ressource "Rest"
 * @author Florian Thiery
 */
@Path("/")
public class RestResource {
    
    @Context UriInfo uriInfo;
    
    /**
     * XML/JSON list of Collections
     * @return XML/JSON list of Collections
     */
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLevel1XML() {
        
        String baseURI = uriInfo.getBaseUri().toString();
        
        CreateXML cr = new CreateXML(baseURI);
        CollsColl xml = cr.getCollsColl();
        
        return Response.ok(xml).header("Access-Control-Allow-Origin", "*").build();
        
    }
    
    /**
     * HTML list of Collections
     *
     * @return HTML list of Collections
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getLevel1HTML() {

        String htmlout = "";
        String baseURI = uriInfo.getBaseUri().toString();

        try {
            CreateHTML cr = new CreateHTML(baseURI);
            htmlout = cr.getHTMLlevel1();
        } catch (Exception e) {
            CreateHTML cr = new CreateHTML(baseURI);
            String error404 = "<html><head><title>Apache Tomcat/7.0.34 - Error report</title><style><!--H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}--></style> </head><body><h1>HTTP Status 404 - Not Found</h1><HR size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> <u>Not Found</u></p><p><b>description</b> <u>The requested resource is not available.</u></p><HR size=\"1\" noshade=\"noshade\"><h3>Apache Tomcat/7.0.34</h3></body></html>";
            htmlout = cr.getHTMLerror(error404);
        }

        return htmlout;

    }
}
