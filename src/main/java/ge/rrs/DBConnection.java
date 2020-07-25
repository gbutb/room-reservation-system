// DBConnection.java
package ge.rrs;

import java.sql.*;

public class DBConnection{
    private static final  String SERVER = "localhost";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String DB_NAME = "reservations_db";
    private static DBConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public DBConnection(){
        connect();
    }

    /**
     *If connection doesn't exist, new connection is created.
     * @return DBConnection
     */
    public static DBConnection getDBConnection(){
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }


    /**
     * makes database connection
     */
    private void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + SERVER, USER, PASSWORD);
            statement = connection.createStatement();
            statement.executeQuery("USE " + DB_NAME);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * closes connection
     * @throws SQLException
     */
    public void closeConnection() throws SQLException{
        connection.close();
    }


    /**
     * executes query
     * @param command - sql command
     * @param args - arguments for prepared statement
     * @return result set
     */
    public ResultSet executeQuery(String command, String[] args){
        try{
            PreparedStatement ps = connection.prepareStatement(command);
            for (int i = 0; i < args.length; i++){
                ps.setString(i+1, args[i]);
            }
            ResultSet set = ps.executeQuery();
            return set;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     * executes update
     * @param command - sql command
     * @param args - arguments for prepared statement
     */
    public void executeUpdate(String command, String[] args){
        try{
            PreparedStatement ps = connection.prepareStatement(command);
            for (int i = 0; i < args.length; i++){
                ps.setString(i+1, args[i]);
            }
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}