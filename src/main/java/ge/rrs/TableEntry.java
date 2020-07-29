// TableEntry.java
package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An abstraction of an entry in the table.
 */
public abstract class TableEntry {

    public TableEntry() {
    }

    public TableEntry(ResultSet rSet, DBConnection connection) {
    }

    /**
     * @return A reference to the DBConnection.
     */
    public abstract DBConnection getConnection();

    /**
     * Returns all table entries which match
     * the search parameters.
     *
     * @param parameters An array of search parameters.
     * @return A collection of table entries which
     * satisfy the relations defined by search parameters.
     * @throws SQLException Should there be any SQL error.
     */
    protected static ResultSet filter(SearchParameters parameters, DBConnection connection,
                                      String tableName) throws SQLException {

        // Build an SQL command
        StringBuilder sqlCommand = new StringBuilder();

        // Append search query
        sqlCommand.append(parameters.getParametersStatement());

        // Add where
        if (sqlCommand.length() > 0)
            sqlCommand.insert(0, " WHERE ");

        // Prepend all values
        sqlCommand.insert(0, "SELECT * FROM " + tableName);

        // Execute query
        return connection.executeQuery(sqlCommand.toString(), parameters.getArguments());
    }

    /**
     * @return The name of the table to
     * which this table entry corresponds to.
     */
    public abstract String getTableName();

    /**
     * Saves any changes that have
     * been done to the table entry.
     *
     * @throws Exception if unable to save.
     */
    public abstract void save() throws Exception;
}
