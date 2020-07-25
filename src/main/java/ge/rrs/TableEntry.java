// TableEntry.java
package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * An abstraction of an entry in the table.
 */
public interface TableEntry {
    /**
     * @return A reference to the DBConnection.
     */
    public DBConnection getConnection();

    /**
     * Returns all table entries which match
     * the search parameters.
     * @param parameters An array of search parameters.
     * @return A collection of table entries which
     *  satisfy the relations defined by search parameters.
     * @throws SQLException Should there be any SQL error.
     */
    public default Collection<TableEntry> filter(Collection<SearchParameter> parameters) throws SQLException {
        // Redirect to static call
        return filter(parameters, getConnection());
    }

    /**
     * Returns all table entries which match
     * the search parameters.
     * @param parameters An array of search parameters.
     * @param connection A reference to DBConnection
     * @return A collection of table entries which
     *  satisfy the relations defined by search parameters.
     * @throws SQLException Should there be any SQL error.
     */
    public static Collection<TableEntry> filter(Collection<SearchParameter> parameters, DBConnection connection) throws SQLException {
        // TODO: Implement-this;
        return null;
    }

    /**
     * Initializes Table entries by parsing
     * the result set.
     * @param rs Result set containing the
     *  raw SQL rows of this table entry.
     * @return An array of table entry instances.
     */
    public Collection<TableEntry> fromResultSet(ResultSet rs);

    /**
     * @return The name of the table to
     *  which this table entry corresponds to.
     */
    public String getTableName();

    /**
     * Saves any changes that have
     * been done to the table entry.
     */
    public void save();
}
