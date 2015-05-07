package de.i3mainz.timeexplorer;

import de.i3mainz.utils.PostGIS;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Send Triple
 *
 * @author Florian Thiery
 */
public class sendTriple extends HttpServlet {

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
        try {

            // Triple ohne Punkt am Ende
            String triple = request.getParameter("triple");
            
            if (triple.contains("(=)")==true) {
                triple = triple.replace("gleich (=)", "equals");
            } else if (triple.contains("(d)")==true) {
                triple = triple.replace("waehrend (d)", "during");
            } else if (triple.contains("(di)")==true) {
                triple = triple.replace("beinhaltet (di)", "contains");
            } else if (triple.contains("(o)")==true) {
                triple = triple.replace("startet vor und endet in (o)", "overlaps");
            } else if (triple.contains("(oi)")==true) {
                triple = triple.replace("startet in und endet nach (oi)", "overlapped-by");
            } else if (triple.contains("(m)")==true) {
                triple = triple.replace("startet vor und endet mit (m)", "meets");
            } else if (triple.contains("(mi)")==true) {
                triple = triple.replace("startet mit und endet nach (mi)", "met-by");
            } else if (triple.contains("(s)")==true) {
                triple = triple.replace("startet mit und endet in (s)", "starts");
            } else if (triple.contains("(si)")==true) {
                triple = triple.replace("startet in und endet nach (si)", "started-by");
            } else if (triple.contains("(f)")==true) {
                triple = triple.replace("startet in und endet mit (f)", "finishes");
            } else if (triple.contains("(fi)")==true) {
                triple = triple.replace("staret vor und endet nach (fi)", "finished-by");
            } else if (triple.contains("(<)")==true) {
                triple = triple.replace("vor (<)", "before");
            } else if (triple.contains("(>)")==true) {
                triple = triple.replace("nach (>)", "after");
            }
            
            triple = triple + " .";

            PostGIS pg = new PostGIS();
            pg.sendTriple(triple);

            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sendTriple</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + triple + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sendTriple</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + e + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(sendTriple.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(sendTriple.class.getName()).log(Level.SEVERE, null, ex);
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
