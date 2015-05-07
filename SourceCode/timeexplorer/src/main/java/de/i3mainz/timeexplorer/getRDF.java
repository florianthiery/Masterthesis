/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.i3mainz.timeexplorer;

import de.i3mainz.utils.Triple;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FT984059
 */
public class getRDF extends HttpServlet {

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
            throws ServletException, IOException, SQLException {

        response.setContentType("application/xml");
        PrintWriter out = response.getWriter();

        String filepath_tmpttl = "/usr/share/apache-tomcat-7.0.41/webapps/share/tmp.rdf";
        String outstring = "";

        try {

            Triple t = new Triple();
            List<String> triplelist = t.getTripleList();

            out.println("<?xml version=\"1.0\"?>");
            out.println("<rdf:RDF xmlns:site=\"http://www.mysite.de/\" xmlns:time=\"http://www.mytime.de/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">");
            
            outstring = outstring + "<?xml version=\"1.0\"?>";
            outstring = outstring + "<rdf:RDF xmlns:site=\"http://www.mysite.de/\" xmlns:time=\"http://www.mytime.de/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";
 
            for (int i = 0; i < triplelist.size(); i++) {

                String temp = triplelist.get(i);
                temp = temp.replace("site:", "");
                temp = temp.replace("time:", "");
                temp = temp.replace(" .", "");
                String[] temparray = temp.split(" "); //[0]subject/site [1]prädikat/time [2]object/site
                
                out.println("  <rdf:Description rdf:about=\"http://www.mysite.de/"+temparray[0]+"\">");
                out.println("    <time:"+temparray[1]+" rdf:resource=\"http://www.mysite.de/"+temparray[2]+"\" />");
                out.println("  </rdf:Description>");
                
                outstring = outstring + "  <rdf:Description rdf:about=\"http://www.mysite.de/"+temparray[0]+"\">";
                outstring = outstring + "    <time:"+temparray[1]+" rdf:resource=\"http://www.mysite.de/"+temparray[2]+"\" />";
                outstring = outstring + "  </rdf:Description>";

            }
            
            out.println("</rdf:RDF>");
            outstring = outstring + "</rdf:RDF>";

            // Schreibe alle Elemente als Textzeilen in die Datei:

            try {

                BufferedWriter file = new BufferedWriter(
                        new OutputStreamWriter(
                        new FileOutputStream(filepath_tmpttl)));

                file.write(outstring);
                file.close();

            } catch (IOException ex) {
                System.out.println(ex);
            }

        } finally {
            out.close();
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(getRDF.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(getRDF.class.getName()).log(Level.SEVERE, null, ex);
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
}
