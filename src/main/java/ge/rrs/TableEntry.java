// TableEntry.java
package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstraction of an entry in the table.
 */
public abstract class TableEntry {
    /**
     * @return A reference to the DBConnection.
     */
    public abstract DBConnection getConnection();

    /**
     * Returns all table entries which match
     * the search parameters.
     * @param parameters An array of search parameters.
     * @return A collection of table entries which
     *  satisfy the relations defined by search parameters.
     * @throws SQLException Should there be any SQL error.
     */
    public Collection<? extends TableEntry> filter(Collection<SearchParameter> parameters) throws SQLException {
        DBConnection connection = getConnection();

        // Build an SQL command
        StringBuilder sqlCommand = new StringBuilder();

        // Arguments
        ArrayList<String> args = new ArrayList<>();
        
        int numAppended = 0;
        for (SearchParameter param : parameters) {
            if (param.isEmpty()) continue;

            // Try to construct search query
            String searchQuery = "";
            
            try {
                searchQuery =
                    param.getKey() +
                    param.getRelation() + "?";
                args.add(param.getValue());
            } catch (Exception _) {
                continue;
            }

            // Append search query
            if (numAppended > 0) sqlCommand.append(" AND ");
            sqlCommand.append(searchQuery);
            
            ++numAppended;
        }

        // Add where
        if (sqlCommand.length() > 0)
            sqlCommand.insert(0, " WHERE ");

        // Prepend all values
        sqlCommand.insert(0, "SELECT * FROM " + getTableName());

        System.out.println("Command is: " + sqlCommand.toString());

        // Execute query
        String[] argsArray = new String[args.size()];
        argsArray = args.toArray(argsArray);
        return fromResultSet(
            connection.executeQuery(sqlCommand.toString(), argsArray));
    }


    /**
     * Initializes Table entries by parsing
     * the result set.
     * @param rs Result set containing the
     *  raw SQL rows of this table entry.
     * @return An array of table entry instances.
     * @throws SQLException
     */
    public abstract Collection<? extends TableEntry> fromResultSet(ResultSet rs) throws SQLException;

    /**
     * @return The name of the table to
     *  which this table entry corresponds to.
     */
    public abstract String getTableName();

    /**
     * Saves any changes that have
     * been done to the table entry.
     * @throws Exception if unable to save.
     */
    public abstract void save() throws Exception;
}
