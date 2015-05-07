/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.i3mainz.timeexplorer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FT984059
 */
public class timeVocabulary extends HttpServlet {

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
            throws ServletException, IOException {
        
        response.setContentType("application/xml");
        PrintWriter out = response.getWriter();
        
        try {
            
            out.println("<relations>");
           
            // Allen (english)
            out.println("  <relation>"+"before"+"</relation>");
            out.println("  <relation>"+"after"+"</relation>");
            out.println("  <relation>"+"during"+"</relation>");
            out.println("  <relation>"+"contains"+"</relation>");
            out.println("  <relation>"+"overlaps"+"</relation>");
            out.println("  <relation>"+"overlapped-by"+"</relation>");
            out.println("  <relation>"+"meets"+"</relation>");
            out.println("  <relation>"+"met-by"+"</relation>");
            out.println("  <relation>"+"starts"+"</relation>");
            out.println("  <relation>"+"started-by"+"</relation>");
            out.println("  <relation>"+"finishes"+"</relation>");
            out.println("  <relation>"+"finished-by"+"</relation>");
            out.println("  <relation>"+"equals"+"</relation>");
            out.println("</relations>");
            
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
        processRequest(request, response);
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
        processRequest(request, response);
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
