package ge.rrs;

import java.util.List;

public interface SearchParameters {
    /*
    NOTE
    For enhanced security, parameters are saved with value expression's arguments
    replaced with question marks and value expression's arguments are stored
    separately in a string array
     */

    /**
     * Processes and adds given parameter to all saved parameters
     * Given parameter (if not the first parameter) is logically
     * connected with previous parameters with "AND" operator
     *
     * @param parameter user given parameter
     */
    void addParameter(SearchParameter parameter) throws Exception;

    /**
     * Processes and adds given parameter to all saved parameters
     * Given parameter (if not the first parameter) is logically
     * connected with previous parameters with the user given operator
     *
     * @param parameter user given parameter
     * @param operator  user given operator
     */
    void addParameter(String operator, SearchParameter parameter) throws Exception;

    /**
     * Processes, encloses in brackets and adds given parameters
     * to all saved parameters
     * A given parameters clause (if not the first parameter) is logically
     * connected with previous parameters with " AND " operator
     *
     * @param inputParameters user given parameters
     */
    void addParametersClause(SearchParameter... inputParameters) throws Exception;

    /**
     * Processes, encloses in brackets and adds given parameters
     * to all saved parameters
     * A given parameters clause (if not the first parameter) is logically
     * connected with previous parameters with the user given operator
     *
     * @param inputParameters user given parameters
     */
    void addParametersClause(String operator, SearchParameter... inputParameters) throws Exception;

    /**
     * Returns a complete collection of user added parameters
     *
     * @return returns a complete collection of user added parameters
     */
    String getParametersStatement();

    /**
     * Returns a string list of value expressions' arguments
     *
     * @return returns a string list of value expressions' arguments
     */
    List<String> getArguments();
}
