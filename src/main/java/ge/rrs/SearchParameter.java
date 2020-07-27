// SearchParameters.java
package ge.rrs;

/**
 * An abstraction of an search
 * parameter.
 */
public interface SearchParameter {
    /**
     * @return the key/column.
     * @throws Exception: If empty.
     */
    public String getKey() throws Exception;

    /**
     * @return the value to which
     *      the key corresponds to.
     * @throws Exception: If empty.
     */
    public String getValue() throws Exception;

    /**
     * @return Value where all arguments
     *  are replaced with placeholders.
     * @throws Exception
     */
    public String getValueExpression() throws Exception;

    /**
     * @return Arguments which are to be
     *  placed into the value expression.
     * @throws Exception
     */
    public String[] getValueArgs() throws Exception;

    /**
     * @return an operator that corresponds
     *  to the relation between the key and
     *  the value to be used for search. 
     * @throws Exception: If empty.
     */
    public String getRelation() throws Exception;

    /**
     * @return true if the parameter
     *  is empty and should be ignored.
     */
    public default boolean isEmpty() {
        return false;
    }
}
