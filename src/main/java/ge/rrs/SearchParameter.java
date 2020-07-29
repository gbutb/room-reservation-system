// SearchParameters.java
package ge.rrs;

import java.util.List;

/**
 * An abstraction of an search
 * parameter.
 */
public interface SearchParameter {
    /**
     * @return the key/column.
     * @throws Exception: If empty.
     */
    String getKey() throws Exception;

    /**
     * @return the value to which
     *      the key corresponds to.
     * @throws Exception: If empty.
     */
    String getValue() throws Exception;

    /**
     * @return Value where all arguments
     *  are replaced with placeholders.
     * @throws Exception
     */
    String getValueExpression() throws Exception;

    /**
     * @return Arguments which are to be
     *  placed into the value expression.
     * @throws Exception
     */
    List<String> getValueArgs() throws Exception;

    /**
     * @return an operator that corresponds
     *  to the relation between the key and
     *  the value to be used for search. 
     * @throws Exception: If empty.
     */
    String getRelation() throws Exception;

    /**
     * @return true if the parameter
     *  is empty and should be ignored.
     */
    default boolean isEmpty() {
        return false;
    }
}
