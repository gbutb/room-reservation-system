// SearchParameters.java
package ge.rrs;

/**
 * An abstraction of an search
 * parameter.
 */
public interface SearchParameter {
    /**
     * @return the key/column.
     */
    public String getKey();

    /**
     * @return the value to which
     *      the key corresponds to.
     */
    public String getValue();

    /**
     * @return an operator that corresponds
     *  to the relation between the key and
     *  the value to be used for search. 
     */
    public String getRelation();

    /**
     * @return true if the parameter
     *  is empty and should be ignored.
     */
    public default boolean isEmpty() {
        return false;
    }
}
