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
     * @return An elements which correspond to the table entry.
     * @throws SQLException Should there be any SQL errors.
     */
    public default Collection<TableEntry> filter(Collection<SearchParameter> parameters) throws SQLException {
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
