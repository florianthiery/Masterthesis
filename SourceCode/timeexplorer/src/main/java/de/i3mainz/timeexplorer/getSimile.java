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
public class getSimile extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            // get Coordinates
            Triple t = new Triple();
            List<String[]> coordlist = t.getCoordinateList();

            // get Minimum und Maximum
            String[] temparray3 = coordlist.get(0);
            int min = Integer.parseInt(temparray3[1]);
            int max = Integer.parseInt(temparray3[2]);
            for (int i = 0; i < coordlist.size(); i++) {
                String[] temparray2 = coordlist.get(i);
                if (Integer.parseInt(temparray2[1]) < min) {
                    min = Integer.parseInt(temparray2[1]);
                }
                if (Integer.parseInt(temparray2[2]) > max) {
                    max = Integer.parseInt(temparray2[2]);
                }
            }

            int mw = ((max - min) / 2) + min;
            int span = (max - min) + 10;
            double pxl = 7500 * (1.0 / span);
            int pxl2 = (int) pxl;


            out.println("<html>");
            out.println("<head>");

            out.println("<script src=\"http://simile.mit.edu/timeline/api/timeline-api.js\" type=\"text/javascript\"></script>");

            out.println("<script>");
            //out.println("var oldFillInfoBubble = Timeline.DefaultEventSource.Event.prototype.fillInfoBubble;");
            //out.println("Timeline.DefaultEventSource.Event.prototype.fillInfoBubble = function(elmt, theme, labeller) {");
            //out.println("}");
            out.println("</script>");

            out.println("<script>");

            out.println("var event_data = ");
            out.println("{");
            out.println("'dateTimeFormat': 'iso8601',");
            out.println("'wikiURL': \"http://simile.mit.edu/shelf/\",");
            out.println("'wikiSection': \"Simile Cubism Timeline\",");

            out.println("'events' : [");

            String textcolor = "";
            
            for (int i = 0; i < coordlist.size(); i++) {

                textcolor = "black";
                String[] temparray = coordlist.get(i);

                String color = "";
                if (temparray[3].equals("meets") || temparray[3].equals("met-by")) {
                    color = "#E5CD47";
                } else if (temparray[3].equals("before") || temparray[3].equals("after")) {
                    color = "#BCC3CD";
                } else if (temparray[3].equals("starts") || temparray[3].equals("started-by")) {
                    color = "#89E6A3";
                } else if (temparray[3].equals("finishes") || temparray[3].equals("finished-by")) {
                    color = "#89E6A3";
                } else if (temparray[3].equals("during") || temparray[3].equals("contains")) {
                    color = "#8CB6F0";
                } else if (temparray[3].equals("overlaps") || temparray[3].equals("overlapped-by")) {
                    color = "#8CB6F0";
                } else if (temparray[3].equals("equals")) {
                    color = "#8CB6F0";
                } else {
                    color = "#f28292";
                }

                if (i != coordlist.size() - 1) {
                    out.println("{'start': '" + temparray[1] + "',");
                    out.println("'end': '" + temparray[2] + "',");
                    out.println("'title': '" + temparray[0] + "',");
                    out.println("'description': '',");
                    out.println("'image': '',");
                    out.println("'link': '',");
                    out.println("'color': '" + color + "',");

                    out.println("'textColor': '"+textcolor+"',");
                    out.println("'caption': '"+temparray[0]+"',");
                    out.println("'trackNum': "+(i+1)+",");
                    out.println("'classname': '"+temparray[0]+"',");
                    out.println("'description': '"+temparray[0]+"',");

                    out.println("'isDuration' :false");
                    out.println("},");
                } else {
                    out.println("{'start': '" + temparray[1] + "',");
                    out.println("'end': '" + temparray[2] + "',");
                    out.println("'title': '" + temparray[0] + "',");
                    out.println("'description': '',");
                    out.println("'image': '',");
                    out.println("'link': '',");
                    out.println("'color': '" + color + "',");
                    
                    out.println("'textColor': '"+textcolor+"',");
                    out.println("'caption': '"+temparray[0]+"',");
                    out.println("'trackNum': "+(i+1)+",");
                    out.println("'classname': '"+temparray[0]+"',");
                    out.println("'description': '"+temparray[0]+"',");
                    
                    out.println("'isDuration' :false");
                    out.println("}");
                }

            }

            out.println("]");
            out.println("}");

            out.println("var tl;");
            out.println("function onLoad() {");

            out.println("var eventSource = new Timeline.DefaultEventSource(0);");

            out.println("var theme = Timeline.ClassicTheme.create();");
            out.println("theme.event.bubble.width = 250;");
            out.println("theme.event.bubble.height = 130;");
            out.println("var d = Timeline.DateTime.parseGregorianDateTime(\"" + mw + "\");");

            out.println("var bandInfos = [");
            out.println("Timeline.createBandInfo({");
            out.println(" width:          \"100%\", ");
            out.println(" intervalUnit:   Timeline.DateTime.DECADE, ");
            out.println(" intervalPixels: " + pxl2 + ",");
            out.println(" eventSource:    eventSource,");
            out.println(" date:           d,");
            out.println(" theme:          theme");
            out.println("})");
            out.println("];");



            out.println(" tl = Timeline.create(document.getElementById(\"tl\"), bandInfos, Timeline.HORIZONTAL);");
            out.println(" eventSource.loadJSON(event_data, document.location.href);");
            out.println("//tl.loadJSON(\"cub2.js\", function(json, url) {");
            out.println("    //eventSource.loadJSON(json, url);");
            out.println(" //});");
            out.println(" }");
            out.println("  var resizeTimerID = null;");
            out.println("function onResize() {");
            out.println("  if (resizeTimerID == null) {");
            out.println("   resizeTimerID = window.setTimeout(function() {");
            out.println("     resizeTimerID = null;");
            out.println("    tl.layout();");
            out.println(" }, 500);");
            out.println(" }");
            out.println(" }");


            out.println(" </script>");
            out.println(" </head>");
            out.println(" <body onload=\"onLoad();\" onresize=\"onResize();\">");
            out.println(" <div id=\"tl\" class=\"timeline-default\" style=\"height: 440px; width: 750px;\">");
            out.println(" </div>");
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
            Logger.getLogger(getSimile.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getSimile.class.getName()).log(Level.SEVERE, null, ex);
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
