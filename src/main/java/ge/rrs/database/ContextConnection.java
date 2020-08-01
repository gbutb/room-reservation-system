package ge.rrs.database;

public class ContextConnection {
    private static DBConnection connection;
    static {
        try {
            connection = new DBConnection();
        } catch (Exception e) {
            connection = null;
        }
    }

    public static DBConnection getContextConnection() {
        return connection;
    }
}
