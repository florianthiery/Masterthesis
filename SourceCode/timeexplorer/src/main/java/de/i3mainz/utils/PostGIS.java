package de.i3mainz.utils;

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
    List<String> triplesIDs = new ArrayList<String>();

    /**
     * Konstruktor
     */
    public PostGIS() {
    }

    /**
     * Clear Triple Table
     */
    public void clearTripleTable() throws IOException, SQLException {

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {
            String query = "DELETE FROM triples";
            setUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Write Original Triples in Triple Table
     *
     * @param triplesOrig
     */
    public void resetTriples(List<String> triplesOrig) throws IOException, SQLException {

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {
            for (int i = 0; i < triplesOrig.size(); i++) {
                String query = "INSERT INTO triples VALUES (" + i + ",'" + triplesOrig.get(i) + "');";
                setUpdate(query);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
    }

    private int getLastTripleID() throws IOException, SQLException {

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        int newID = 1000;

        try {

            ResultSet TripleID_ResultSet = null;

            try {
                TripleID_ResultSet = this.getTriplesResultSet();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                StringOut = e.toString() + "from sendTriple()";
                throw new SQLException(StringOut);
            }

            //Anzahl der Spalten
            int lauf = 0;
            int rows = 0;
            try {
                TripleID_ResultSet.last();
                rows = TripleID_ResultSet.getRow();
                TripleID_ResultSet.beforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();
                StringOut = e.toString() + "from sendTriple()";
                throw new SQLException(StringOut);
            }

            while (TripleID_ResultSet.next() && lauf < rows) {
                newID = Integer.parseInt(TripleID_ResultSet.getString(1));
                lauf++;
            }

        } catch (Exception e) {
            System.out.println(e);
            StringOut = "<error>" + e.toString() + "</error>";
        } finally {
            closeConnection();
        }

        newID = newID + 1;
        System.out.println("newID: " + newID);

        return newID;

    }

    private void sendTripleDatabase(int newID, String triple) throws SQLException, IOException {

        List<String> triplelist = new ArrayList<String>();
        triplelist = getTriples();

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {

            int listid = -1;

            String temp = triple;
            temp = temp.replace("site:", "");
            temp = temp.replace("time:", "");
            temp = temp.replace(" .", "");
            String[] temparray = temp.split(" "); //[0]subject/site [1]prädikat/time [2]object/site

            for (int i = 0; i < triplelist.size(); i++) {

                String temp2 = triplelist.get(i);
                temp2 = temp2.replace("site:", "");
                temp2 = temp2.replace("time:", "");
                temp2 = temp2.replace(" .", "");
                String[] temparray2 = temp2.split(" "); //[0]subject/site [1]prädikat/time [2]object/site

                if (temparray2[0].equals(temparray[0])) { //wenn subject = subject dann ändern
                    listid = new Integer(triplesIDs.get(i));
                    String query = "UPDATE triples SET triple = '" + triple + "' WHERE id = " + listid + ";";
                    setUpdate(query);
                    String query2 = "UPDATE triples SET id = " + newID + " WHERE triple = '" + triple + "';";
                    setUpdate(query2);
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println(e);
            StringOut = "<error>" + e.toString() + "</error>";
        } finally {
            closeConnection();
        }

    }

    /**
     * Send Triple to Database
     *
     * @param triple
     */
    public void sendTriple(String triple) throws IOException, SQLException {

        int newID = getLastTripleID();
        System.out.println(newID);

        sendTripleDatabase(newID, triple);

    }

    /**
     * Get Triples from Database
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public List<String> getTriples() throws SQLException, IOException {

        List<String> triples = new ArrayList<String>();

        try {
            
            

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

            triples = getTriplesDatabase();

        } catch (Exception e) {
            StringOut = "<error>" + e.toString() + "</error>";
        } finally {
            closeConnection();
        }

        return triples;

    }

    /**
     * Get Triples as String List from Database
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTriplesDatabase() throws SQLException {

        ResultSet Triples_ResultSet = null;
        List<String> triples = new ArrayList<String>();

        try {
            Triples_ResultSet = this.getTriplesResultSet();
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getTriplesDatabase()";
            throw new SQLException(StringOut);
        }

        //Anzahl der Spalten
        int lauf = 0;
        int rows = 0;
        try {
            Triples_ResultSet.last();
            rows = Triples_ResultSet.getRow();
            Triples_ResultSet.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getTriplesDatabase()";
            throw new SQLException(StringOut);
        }

        while (Triples_ResultSet.next() && lauf < rows) {
            triples.add(Triples_ResultSet.getString(2));
            triplesIDs.add(Triples_ResultSet.getString(1));
            lauf++;
        }

        return triples;

    }

    /**
     * Get Triples from Database
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public List<String> getTriplesOrig() throws SQLException, IOException {

        List<String> triples = new ArrayList<String>();

        setDBParameterFromConfig();

        loadJdbcDriver();
        openConnection();

        try {

            triples = getTriplesOrigDatabase();

        } catch (Exception e) {
            StringOut = "<error>" + e.toString() + "</error>";
        } finally {
            closeConnection();
        }

        return triples;

    }

    /**
     * Get Triples as String List from Database
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTriplesOrigDatabase() throws SQLException {

        ResultSet Triples_ResultSet = null;
        List<String> triples = new ArrayList<String>();

        try {
            Triples_ResultSet = this.getTriplesOrigResultSet();
        } catch (Exception e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getTriplesOrigDatabase()";
            throw new SQLException(StringOut);
        }

        //Anzahl der Spalten
        int lauf = 0;
        int rows = 0;
        try {
            Triples_ResultSet.last();
            rows = Triples_ResultSet.getRow();
            Triples_ResultSet.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            StringOut = e.toString() + "from getTriplesOrigDatabase()";
            throw new SQLException(StringOut);
        }

        while (Triples_ResultSet.next() && lauf < rows) {
            triples.add(Triples_ResultSet.getString(2));
            lauf++;
        }

        return triples;

    }

    /**
     * Get Triples from triple table
     *
     * @return
     * @throws SQLException
     */
    private ResultSet getTriplesResultSet() throws SQLException {
        String querySelect = "SELECT * FROM triples ORDER BY id ASC";
        ResultSet resultSetSelect = getQueryResult(querySelect);
        return resultSetSelect;
    }

    /**
     * Get Triples from triple original table
     *
     * @return
     * @throws SQLException
     */
    private ResultSet getTriplesOrigResultSet() throws SQLException {
        String querySelect = "SELECT * FROM triples_orig ORDER BY id ASC";
        ResultSet resultSetSelect = getQueryResult(querySelect);
        return resultSetSelect;
    }

    /**
     * Get Triples from triple original table
     *
     * @return
     * @throws SQLException
     */
    private ResultSet getLastTripleIDResultSet() throws SQLException {
        String querySelect = "SELECT id FROM triples ORDER BY id DESC LIMIT 1";
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

    /**
     * Methode um ein Uüdate zu übergeben und kein ResultSet zu erhalten
     *
     * @param query
     */
    private void setUpdate(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
// </editor-fold>
}
