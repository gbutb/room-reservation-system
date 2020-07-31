// DBConnection.java
package ge.rrs.database;


import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.List;

public class DBConnection {

    private static final String SERVER = "localhost";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String DB_NAME = "reservations_db";
    private static DBConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public DBConnection() throws SQLException {
        connect();
    }

    public DBConnection(String server, String user, String password, String db_name) throws SQLException {
        connect(server, user, password, db_name);
    }

    private void connect(String server, String user, String password, String db_name) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + server, user, password);
        statement = connection.createStatement();
        statement.executeQuery("USE " + db_name);
    }

    /**
     * makes database connection
     *
     * @throws SQLException
     */
    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + SERVER, USER, PASSWORD);
        statement = connection.createStatement();
        statement.executeQuery("USE " + DB_NAME);
    }

    /**
     * If connection doesn't exist, new connection is created.
     *
     * @return DBConnection
     * @throws SQLException
     */
    public static DBConnection getDBConnection() throws SQLException {

        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    /**
     * closes connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * executes query
     *
     * @param command - sql command
     * @param args    - arguments for prepared statement
     * @return result set
     * @throws SQLException
     */
    public ResultSet executeQuery(String command, List<String> args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(command);
        for (int i = 0; i < args.size(); i++) {
            ps.setString(i + 1, args.get(i));
        }
        return ps.executeQuery();
    }

    /**
     * executes update
     *
     * @param command - sql command
     * @param args    - arguments for prepared statement
     * @throws SQLException
     */
    public void executeUpdate(String command, List<String> args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(command);
        for (int i = 0; i < args.size(); i++) {
            ps.setString(i + 1, args.get(i));
        }
        ps.executeUpdate();
    }

    /**
     * Reads given .sql file and executes contained commands.
     *
     * @param filename .sql file
     * @throws FileNotFoundException file doesn't exist or is inaccessible
     */
    public void executeSQLFrom(String filename) throws FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader(filename));

        scriptRunner.runScript(reader);
    }
}
