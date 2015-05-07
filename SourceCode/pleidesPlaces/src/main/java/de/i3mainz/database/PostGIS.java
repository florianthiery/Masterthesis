package de.i3mainz.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Florian Thiery
 */
public class PostGIS {

    public String StringOut = "";
    private List<String> findspots = new ArrayList<String>();
    // Datenbankverbindungsparameter (loaded from config)
    private Properties config = new Properties();
    private String host = "";
    private String port = "";
    private String database = "";
    private String user = "";
    private String password = "";
    private Connection connection = null;
    private String driver = "org.postgresql.Driver";

    public PostGIS() throws SQLException, IOException {

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {

            getFindspotList();
            getDistances();

        } catch (Exception e) {
            StringOut = "<error>" + e.toString() + "</error>";
        }

        closeConnection();

    }

    private void getFindspotList() throws SQLException {

        // SELECT Daten erhalten

        ResultSet Findspots_ResultSet = null;

        try {
            Findspots_ResultSet = this.getFindspotName();
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getFindspotList()";
            throw new SQLException(StringOut);
        }

        //Anzahl der Spalten
        int lauf = 0;
        int rows = 0;
        try {
            Findspots_ResultSet.last();
            rows = Findspots_ResultSet.getRow();
            Findspots_ResultSet.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getFindspotList()";
            throw new SQLException(StringOut);
        }

//        String Features = "";

        while (Findspots_ResultSet.next() && lauf < rows) {
            //Features = Features + FEATURE_ResultSet.getString(1) + "\n";
            findspots.add(Findspots_ResultSet.getString(1));
            lauf++;
        }

        //Ausgabe
//        if (Features.equals("null")) {
//            StringOut = StringOut + "error";
//        } else {
//            StringOut = StringOut + Features;
//        }
    }

    private void getDistances() throws SQLException {

        // SELECT Daten erhalten

        ResultSet Distances_ResultSet = null;
        String[][] distances = new String[findspots.size()][4];

        for (int i = 0; i < findspots.size(); i++) {

            String findspot = findspots.get(i).toString();
            
            if (findspot.contains("|") == false) {

                System.out.println(i);

                try {
                    Distances_ResultSet = this.getDistanceUTM32Findspot5NearestPleiadesPlaces(findspots.get(i).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    StringOut = e.toString() + "from getDistances()";
                    throw new SQLException(StringOut);
                }

                //Anzahl der Spalten
                int lauf = 0;
                int rows = 0;
                try {
                    Distances_ResultSet.last();
                    rows = Distances_ResultSet.getRow();
                    Distances_ResultSet.beforeFirst();
                } catch (SQLException e) {
                    e.printStackTrace();
                    StringOut = e.toString() + "from getDistances()";
                    throw new SQLException(StringOut);
                }

                while (Distances_ResultSet.next() && lauf < rows) {
                    distances[lauf][0] = Distances_ResultSet.getString(1);
                    distances[lauf][1] = Distances_ResultSet.getString(2);
                    distances[lauf][2] = Distances_ResultSet.getString(3);
                    distances[lauf][3] = Distances_ResultSet.getString(4);
                    lauf++;
                }

                StringOut = StringOut + "Findspot(Place): " + findspots.get(i).toString() + "\n";
                StringOut = StringOut + "Place                                 \t\t" + "Distance\t\t" + "Era\t" + "ID             \t" + "Pleiades-Link\t\t\t\t\t\tMatch(<5km)\n";
                StringOut = StringOut + "------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";

                boolean match = false;
                boolean match2 = false;

                for (int j = 0; j < distances[0].length + 1; j++) {

                    double distance = Double.parseDouble(distances[j][0]);
                    if (match == true) {
                        match2 = false;
                    } else {
                        if (distance < 5000) {
                            match = true;
                            match2 = true;
                        }
                    }

                    String name = distances[j][1];
                    String id = distances[j][3];
                    String link = "http://pleiades.stoa.org/places/" + distances[j][3];

                    for (int k = 0; k < 40; k++) {

                        if (distances[j][1].length() < k) {
                            name = name + " ";
                        }

                    }

                    for (int k = 0; k < 15; k++) {

                        if (distances[j][3].length() < k) {
                            id = id + " ";
                        }

                    }

                    for (int k = 0; k < 50; k++) {

                        if (link.length() < k) {
                            link = link + " ";
                        }

                    }

                    StringOut = StringOut + name + " \t" + distances[j][0] + "m \t" + distances[j][2] + " \t" + id + "\t" + link + "\t" + match2 + "\n";

                }

                StringOut = StringOut + "==================================================================================================================================================================\n\n";

            }

        }

    }

    private ResultSet getFindspotName() throws SQLException {
        String querySelect = "SELECT findspot.name FROM findspot";
        ResultSet resultSetSelect = getQueryResult(querySelect);
        return resultSetSelect;
    }

    private ResultSet getDistanceUTM32Findspot5NearestPleiadesPlaces(String findspot) throws SQLException {
        String querySelect = "SELECT ST_Distance(ST_Transform(p.geom, 32632), ST_Transform(f.geom , 32632)) AS dist, p.place_name, p.era, p.id FROM placrefer p, findspot f WHERE f.name = '" + findspot + "' ORDER BY ST_Distance(ST_Transform(p.geom, 32632), ST_Transform(f.geom , 32632))  ASC LIMIT 5";
        ResultSet resultSetSelect = getQueryResult(querySelect);
        return resultSetSelect;
    }

// <editor-fold defaultstate="collapsed" desc="Datenbank">
    /**
     * get Database Config Parameter
     *
     * @throws IOException
     */
    private void setDBParameterFromConfig() throws IOException {

        // load config Class
        config.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        //get data
        host = config.getProperty("db_host");
        port = config.getProperty("db_port");
        database = config.getProperty("db_database");
        user = config.getProperty("db_user");
        password = config.getProperty("db_password");

    }

    /**
     * loading the JDBC driver
     */
    private void loadJdbcDriver() throws SQLException {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from loadJdbcDriver()";
            throw new SQLException(StringOut);
            //System.exit(1);
        }
        System.out.println("driver loaded");
    }

    /**
     * @return Url-string for postgreSQL-database connection
     */
    private String getUrl() {
        // PostgreSQL takes one of the following url-forms:
        // ================================================
        // jdbc:postgresql:database
        // jdbc:postgresql://host/database
        // jdbc:postgresql://host:port/database
        return ("jdbc:postgresql:" + (host != null ? ("//" + host) + (port != null ? ":" + port : "") + "/" : "") + database);
    }

    /**
     * opening the connection
     */
    private void openConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(getUrl(), user, password);
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from openConnection()";
            throw new SQLException(StringOut);
            //System.exit(1);
        }
        System.out.println("connection opened");
    }

    /**
     * close the connection
     */
    private void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from closeConnection()";
            throw new SQLException(StringOut);
            //System.exit(1);
        }
        System.out.println("connection closed");
    }

    /**
     * Methode um eine Query zu übergeben und ein ResultSet zu erhalten
     *
     * @param query
     * @return ResultSet
     */
    private ResultSet getQueryResult(String query) throws SQLException {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getQueryResult()";
            throw new SQLException(StringOut);
            //System.exit(1);
        }
        return resultSet;
    }
// </editor-fold>
}
