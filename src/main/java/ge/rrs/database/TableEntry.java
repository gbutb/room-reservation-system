// TableEntry.java
package ge.rrs.database;

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
     * @deprecated
     * Saves any changes that have
     * been done to the table entry.
     *
     * @throws Exception if unable to save.
     */
    public void save() throws Exception {
        throw new Exception("Deprecated");
    }

    /**
     * Adds a new entry to the database.
     * @throws Exception if entry already exists.
     */
    public abstract void insertEntry() throws Exception;

    /**
     * Updates an existing entry in the database.
     * @throws Exception if the entry doesn't exist.
     */
    public abstract void updateEntry() throws Exception;

    /**
     * @return the primary key of the entry if it
     *  exists in database, null otherwise.
     */
    public abstract Integer getPrimaryKey();
}
