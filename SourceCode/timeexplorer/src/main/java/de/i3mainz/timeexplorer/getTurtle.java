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
 * @author Florian Thiery
 */
public class getTurtle extends HttpServlet {

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

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String filepath_tmpttl = "/usr/share/apache-tomcat-7.0.41/webapps/share/tmp.ttl";
        String outstring = "";

        try {

            Triple t = new Triple();
            List<String> triplelist = t.getTripleList();

            out.println("@prefix time: <http://143.93.114.104/timeexplorer/timeVocabulary#> .");
            outstring = outstring + "@prefix time: <http://143.93.114.104/timeexplorer/timeVocabulary#> .";
            out.println("@prefix site: <http://143.93.114.104/rest/samian/findspots/> .");
            outstring = outstring + "@prefix site: <http://143.93.114.104/rest/samian/findspots/> .";

            for (int i = 0; i < triplelist.size(); i++) {

                out.println(triplelist.get(i));
                outstring = outstring + triplelist.get(i);

            }

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
            Logger.getLogger(getTurtle.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getTurtle.class.getName()).log(Level.SEVERE, null, ex);
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
