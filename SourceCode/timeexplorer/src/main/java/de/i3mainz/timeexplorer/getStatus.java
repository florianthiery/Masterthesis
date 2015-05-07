/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.i3mainz.timeexplorer;

import de.i3mainz.utils.Triple;
import java.io.IOException;
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
public class getStatus extends HttpServlet {

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

        String outstring = "";

        try {
            Triple t = new Triple();
            List<String> triplelist = t.getTripleList();

            for (int i = 0; i < triplelist.size(); i++) {

                String triple = triplelist.get(i);

                if (triple.contains("equals") == true) {
                    triple = triple.replace("equals", "gleich (=)");
                } else if (triple.contains("during") == true) {
                    triple = triple.replace("during", "waehrend (d)");
                } else if (triple.contains("contains") == true) {
                    triple = triple.replace("contains", "beinhaltet (di)");
                } else if (triple.contains("overlaps") == true) {
                    triple = triple.replace("overlaps", "startet vor und endet in (o)");
                } else if (triple.contains("overlapped-by") == true) {
                    triple = triple.replace("overlapped-by", "startet in und endet nach (oi)");
                } else if (triple.contains("meets") == true) {
                    triple = triple.replace("meets", "startet vor und endet mit (m)");
                } else if (triple.contains("met-by") == true) {
                    triple = triple.replace("met-by", "startet mit und endet nach (mi)");
                } else if (triple.contains("starts") == true) {
                    triple = triple.replace("starts", "startet mit und endet in (s)");
                } else if (triple.contains("started-by") == true) {
                    triple = triple.replace("started-by", "startet in und endet nach (si)");
                } else if (triple.contains("(f)") == true) {
                    triple = triple.replace("finishes", "startet in und endet mit (f)");
                } else if (triple.contains("finished-by") == true) {
                    triple = triple.replace("finished-by", "staret vor und endet nach (fi)");
                } else if (triple.contains("before") == true) {
                    triple = triple.replace("before", "vor (<)");
                } else if (triple.contains("after") == true) {
                    triple = triple.replace("after", "nach (>)");
                }

                out.println(triple);
                outstring = triple;

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
            Logger.getLogger(getStatus.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getStatus.class.getName()).log(Level.SEVERE, null, ex);
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
