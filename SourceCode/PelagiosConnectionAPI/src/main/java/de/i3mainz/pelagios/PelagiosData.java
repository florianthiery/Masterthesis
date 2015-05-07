package de.i3mainz.pelagios;

import de.i3mainz.database.PostGIS;
import de.i3mainz.sesameconnection.Sesame;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;

/**
 *
 * @author Florian Thiery
 */
public class PelagiosData extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException, JDOMException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");

        PrintWriter out = response.getWriter();

        try {

            String PleiadesID = getStringValue(request, "PleiadesID", "");
            String Findspot = getStringValue(request, "Findspot", "");
            String datasets = getStringValue(request, "datasets", "");
            String type = getStringValue(request, "type", "");

            String findspotNameWhitespace = Findspot; // findspot Name mit Whitespace und Underscore (for Database)
            findspotNameWhitespace = findspotNameWhitespace.replace("__", "#");
            findspotNameWhitespace = findspotNameWhitespace.replace("_", " ");
            findspotNameWhitespace = findspotNameWhitespace.replace("#", "__");

            if (type.equals("xml")) {

                response.setContentType("application/xml");

                if (!PleiadesID.equals("")) { //nur Pleiades ID

                    Pelagios p = new Pelagios();
                    List<String> PelagiosURIs = new ArrayList<String>();
                    List<String> PelagiosConnections = new ArrayList<String>();

                    // get Pleagios Data
                    PelagiosURIs = p.getPelagiosURIs(PleiadesID);
                    PelagiosConnections = p.getPelagiosConnections(PleiadesID);

                    if (datasets.equals("false") || datasets.equals("")) { // Nur URIs

                        out.println("<list>");
                        if (PelagiosURIs.size() > 0 && PelagiosURIs != null) {
                            out.println("  <link>" + "http://pleiades.stoa.org/places/" + PleiadesID + "</link>");
                            out.println("  <link>" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesID + "</link>");
                        }
                        for (int i = 0; i < PelagiosURIs.size(); i++) {
                            out.println("  <link>" + PelagiosURIs.get(i).toString() + "</link>");
                        }
                        out.println("</list>");

                    } else { // Nur Datensets

                        out.println("<list>");
                        for (int i = 0; i < PelagiosConnections.size(); i++) {
                            out.println("  <link>" + PelagiosConnections.get(i).toString() + "</link>");
                        }
                        out.println("</list>");
                    }

                } else if (!Findspot.equals("")) {

                    // Abfrage mit der Datenbank
                    // PostGIS pg = new PostGIS();
                    // String pleiadesID = pg.getPleiadesID(findspotNameWhitespace);

                    // Abfrage mit dem Triplestore
                    Sesame s = new Sesame(Findspot);
                    String pleiadesID = s.getID();

                    Pelagios p = new Pelagios();
                    List<String> PelagiosURIs = new ArrayList<String>();
                    List<String> PelagiosConnections = new ArrayList<String>();

                    // get Pleagios Data
                    PelagiosURIs = p.getPelagiosURIs(pleiadesID);
                    PelagiosConnections = p.getPelagiosConnections(pleiadesID);

                    if (datasets.equals("false") || datasets.equals("")) { // Nur URIs

                        out.println("<list>");
                        if (PelagiosURIs.size() > 0 && PelagiosURIs != null) {
                            out.println("  <link>" + "http://pleiades.stoa.org/places/" + pleiadesID + "</link>");
                            out.println("  <link>" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + pleiadesID + "</link>");
                        }
                        for (int i = 0; i < PelagiosURIs.size(); i++) {
                            out.println("  <link>" + PelagiosURIs.get(i).toString() + "</link>");
                        }
                        out.println("</list>");

                    } else { // Nur Datasets

                        out.println("<list>");
                        for (int i = 0; i < PelagiosConnections.size(); i++) {
                            out.println("  <link>" + PelagiosConnections.get(i).toString() + "</link>");
                        }
                        out.println("</list>");
                    }

                }

            }

        } catch (Exception e) {
            System.out.println(e);
            out.println("<list>");
            out.println("</list>");
        } finally {
            out.close();
        }
    }

    /**
     * Get String Value of Request
     *
     * @param request
     * @param paramName
     * @param defaultVaue
     * @return
     */
    public static String getStringValue(HttpServletRequest request, String paramName, String defaultVaue) {
        if (request.getParameter(paramName) != null) {
            return request.getParameter(paramName);
        } else {
            return defaultVaue;
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);


        } catch (JDOMException ex) {
            Logger.getLogger(PelagiosData.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);


        } catch (JDOMException ex) {
            Logger.getLogger(PelagiosData.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
 // <editor-fold defaultstate="collapsed" desc="Nicht verwendeter aber nützlicher Code.">
    
    //            if (type.equals("text")) {
//
//                response.setContentType("text/plain");
//
//                if (!PleiadesID.equals("")) {
//
//                    Pelagios p = new Pelagios();
//                    List<String> PelagiosURIs = new ArrayList<String>();
//                    List<String> PelagiosConnections = new ArrayList<String>();
//
//                    // Mainz = 109169 // Aardenburg = 108722
//                    PelagiosURIs = p.getPelagiosURIs(PleiadesID);
//                    PelagiosConnections = p.getPelagiosConnections(PleiadesID);
//
//                    if (datasets.equals("false") || datasets.equals("")) {
//
//                        out.println("URIs");
//                        out.println("----");
//                        out.println("http://pleiades.stoa.org/places/" + PleiadesID);
//                        out.println("http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesID);
//                        for (int i = 0; i < PelagiosURIs.size(); i++) {
//                            out.println(PelagiosURIs.get(i).toString());
//                        }
//
//                    } else {
//
//                        out.println("Datasets");
//                        out.println("--------");
//                        for (int i = 0; i < PelagiosConnections.size(); i++) {
//                            out.println(PelagiosConnections.get(i).toString());
//                        }
//                    }
//
//                } else if (!Findspot.equals("")) {
//
//                    PostGIS pg = new PostGIS();
//                    String pleiadesID = pg.getPleiadesID(findspotNameWhitespace);
//
//                    Pelagios p = new Pelagios();
//                    List<String> PelagiosURIs = new ArrayList<String>();
//                    List<String> PelagiosConnections = new ArrayList<String>();
//
//                    // Mainz = 109169 // Aardenburg = 108722
//                    PelagiosURIs = p.getPelagiosURIs(pleiadesID);
//                    PelagiosConnections = p.getPelagiosConnections(pleiadesID);
//
//                    if (datasets.equals("false") || datasets.equals("")) {
//
//                        out.println("URIs");
//                        out.println("----");
//                        out.println("http://pleiades.stoa.org/places/" + PleiadesID);
//                        out.println("http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesID);
//                        for (int i = 0; i < PelagiosURIs.size(); i++) {
//                            out.println(PelagiosURIs.get(i).toString());
//                        }
//
//                    } else {
//
//                        out.println("Datasets");
//                        out.println("--------");
//                        for (int i = 0; i < PelagiosConnections.size(); i++) {
//                            out.println(PelagiosConnections.get(i).toString());
//                        }
//                    }
//
//                }
//
//            } else if (type.equals("html")) {
//
//                response.setContentType("text/html;charset=UTF-8");
//
//                out.println("<html>");
//                out.println("  <head>");
//                out.println("    <title>PelagiosConnectionAPI</title>");
//                out.println("     <style type=\"text/css\">");
//                out.println("     <style type=\"text/css\">");
//                out.println("       a:link {color:blue; text-decoration: none}");
//                out.println("       a:visited {color:blue; text-decoration: none}");
//                out.println("       a:hover {color:blue; text-decoration: none}");
//                out.println("     </style>");
//                out.println("  </head>");
//                out.println("  <body>");
//
//
//                if (!PleiadesID.equals("")) {
//
//                    Pelagios p = new Pelagios();
//                    List<String> PelagiosURIs = new ArrayList<String>();
//                    List<String> PelagiosConnections = new ArrayList<String>();
//
//                    // Mainz = 109169 // Aardenburg = 108722
//                    PelagiosURIs = p.getPelagiosURIs(PleiadesID);
//                    PelagiosConnections = p.getPelagiosConnections(PleiadesID);
//
//                    if (datasets.equals("false") || datasets.equals("")) {
//
//                        out.println("    <h2>URIs</h2>");
//                        out.println("    <ul>");
//                        out.println("      <li>");
//                        out.println("        <a href=\"" + "http://pleiades.stoa.org/places/" + PleiadesID + "\" target=\"_blank\">" + "http://pleiades.stoa.org/places/" + PleiadesID + "</a><br>");
//                        out.println("      </li>");
//                        out.println("      <li>");
//                        out.println("        <a href=\"" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesID + "\" target=\"_blank\">" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + PleiadesID + "</a><br>");
//                        out.println("      </li>");
//                        for (int i = 0; i < PelagiosURIs.size(); i++) {
//                            out.println("      <li>");
//                            out.println("        <a href=\"" + PelagiosURIs.get(i).toString() + "\" target=\"_blank\">" + PelagiosURIs.get(i).toString() + "</a><br>");
//                            out.println("      </li>");
//                        }
//                        out.println("    </ul>");
//
//                    } else {
//
//                        out.println("    <h2>Datasets</h2>");
//                        out.println("    <ul>");
//                        for (int i = 0; i < PelagiosConnections.size(); i++) {
//                            out.println("      <li>");
//                            out.println("        <span>" + PelagiosConnections.get(i).toString() + "</span>");
//                            out.println("      </li>");
//                        }
//                        out.println("    </ul>");
//                    }
//
//                } else if (!Findspot.equals("")) {
//
//                    PostGIS pg = new PostGIS();
//                    String pleiadesID = pg.getPleiadesID(findspotNameWhitespace);
//
//                    Pelagios p = new Pelagios();
//                    List<String> PelagiosURIs = new ArrayList<String>();
//                    List<String> PelagiosConnections = new ArrayList<String>();
//
//                    // Mainz = 109169 // Aardenburg = 108722
//                    PelagiosURIs = p.getPelagiosURIs(pleiadesID);
//                    PelagiosConnections = p.getPelagiosConnections(pleiadesID);
//
//                    if (datasets.equals("false") || datasets.equals("")) {
//
//                        out.println("    <h2>URIs</h2>");
//                        out.println("    <ul>");
//                        out.println("      <li>");
//                        out.println("        <a href=\"" + "http://pleiades.stoa.org/places/" + pleiadesID + "\" target=\"_blank\">" + "http://pleiades.stoa.org/places/" + pleiadesID + "</a><br>");
//                        out.println("      </li>");
//                        out.println("      <li>");
//                        out.println("        <a href=\"" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + pleiadesID + "\" target=\"_blank\">" + "http://pelagios.dme.ait.ac.at/api/places/http%3A%2F%2Fpleiades.stoa.org%2Fplaces%2F" + pleiadesID + "</a><br>");
//                        out.println("      </li>");
//                        for (int i = 0; i < PelagiosURIs.size(); i++) {
//                            out.println("      <li>");
//                            out.println("        <a href=\"" + PelagiosURIs.get(i).toString() + "\" target=\"_blank\">" + PelagiosURIs.get(i).toString() + "</a><br>");
//                            out.println("      </li>");
//                        }
//                        out.println("    </ul>");
//
//                    } else {
//
//                        out.println("    <h2>Datasets</h2>");;
//                        out.println("    <ul>");
//                        for (int i = 0; i < PelagiosConnections.size(); i++) {
//                            out.println("      <li>");
//                            out.println("        <span>" + PelagiosConnections.get(i).toString() + "</span>");
//                            out.println("      </li>");
//                        }
//                        out.println("    </ul>");
//                    }
//
//                }
//
//                out.println("  </body>");
//                out.println("</html>");

//            } else 
    
    
 //</editor-fold>
}
