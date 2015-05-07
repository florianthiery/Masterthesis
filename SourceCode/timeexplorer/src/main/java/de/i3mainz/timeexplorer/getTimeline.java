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
 * Servlet zum Erhalten einer Timeline
 *
 * @author Florian Thiery
 */
public class getTimeline extends HttpServlet {

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

            Triple t = new Triple();
            List<String[]> coordlist = t.getCoordinateList();

            out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            out.println("<html xmlns=\"http://www.w3.org/1999/xhtm\">");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            out.println("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['timeline']}]}\"></script>");
            out.println("<title>Timeline | Google</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\">");
            out.println("  google.setOnLoadCallback(drawChart);");
            out.println("  function drawChart() {");
            out.println("    var container = document.getElementById('timeline');");
            out.println("    var chart = new google.visualization.Timeline(container);");
            out.println("    var dataTable = new google.visualization.DataTable();");
            out.println("    dataTable.addColumn({ type: 'string', id: 'Term' });");
            out.println("    dataTable.addColumn({ type: 'string', id: 'Name' });");
            out.println("    dataTable.addColumn({ type: 'date', id: 'Start' });");
            out.println("    dataTable.addColumn({ type: 'date', id: 'End' });");
            out.println("    dataTable.addRows([");

//            System.out.println("-----");
            for (int i = 0; i < coordlist.size(); i++) {

                String[] temparray = coordlist.get(i);
                if (i != coordlist.size() - 1) {
                    out.println("    [ 't', '" + temparray[0] + "', new Date(" + temparray[1] + ",1,0), new Date(" + temparray[2] + ",1,0) ],");
//                    System.out.println("    [ 't', '" + temparray[0] + "', new Date(" + temparray[1] + ",1,0), new Date(" + temparray[2] + ",1,0) ],");
                } else {
                    out.println("    [ 't', '" + temparray[0] + "', new Date(" + temparray[1] + ",1,0), new Date(" + temparray[2] + ",1,0) ]");
//                    System.out.println("    [ 't', '" + temparray[0] + "', new Date(" + temparray[1] + ",1,0), new Date(" + temparray[2] + ",1,0) ]");
                }

            }

            out.println("    ]);");

            out.println("    var options = {");
            out.println("      timeline: { showRowLabels: false }");
            out.println("    };");

            out.println("    chart.draw(dataTable);");
            out.println("  }");
            out.println("</script>");
            out.println("<center><div id=\"timeline\" style=\"width: 1200px; height: 800px;\"></div></center>");
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
            Logger.getLogger(getTimeline.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getTimeline.class.getName()).log(Level.SEVERE, null, ex);
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
