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
    // Datenbankverbindungsparameter (loaded from config)
    private Properties config = new Properties();
    private String host = "";
    private String port = "";
    private String database = "";
    private String user = "";
    private String password = "";
    private Connection connection = null;
    private String driver = "org.postgresql.Driver";

    /**
     * Konstruktor
     */
    public PostGIS() {

    }
    
    /**
     * Get Pleiades ID
     * @param findspot
     * @throws SQLException
     * @throws IOException 
     */
    public String getPleiadesID(String findspot) throws SQLException, IOException {

        String pleiadesID = "";
        
        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {

            pleiadesID = getPleiadesIDdatabase(findspot);

        } catch (Exception e) {
            StringOut = "<error>" + e.toString() + "</error>";
        } finally {
            closeConnection();
        }

        return pleiadesID;

    }

    /**
     * get Match PleiadesIF from Findspot
     * @param findspot
     * @return PleiadesID
     * @throws SQLException 
     */
    private String getPleiadesIDdatabase(String findspot) throws SQLException {

        ResultSet PleiadesID_ResultSet = null;

        try {
            PleiadesID_ResultSet = this.getPleiadesIDResultSet(findspot);
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getPleiadesID()";
            throw new SQLException(StringOut);
        }

        //Anzahl der Spalten
        int lauf = 0;
        int rows = 0;
        try {
            PleiadesID_ResultSet.last();
            rows = PleiadesID_ResultSet.getRow();
            PleiadesID_ResultSet.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getPleiadesID()";
            throw new SQLException(StringOut);
        }

        String PleiadesID = "";
        while (PleiadesID_ResultSet.next() && lauf < rows) {
            PleiadesID = PleiadesID_ResultSet.getString(1);
            lauf++;
        }
        
        StringOut = StringOut + PleiadesID;
        
        return PleiadesID;

    }

    /**
     * Get PleiadesID of Findspot match
     * @param findspot
     * @return
     * @throws SQLException 
     */
    private ResultSet getPleiadesIDResultSet(String findspot) throws SQLException {
        String querySelect = "SELECT pleiadesplace FROM pleiadesmatch WHERE findspot = '"+findspot+"'";
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
