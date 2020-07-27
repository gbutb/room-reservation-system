package ge.rrs;

import java.util.Collection;

public interface SearchParameters {

    /**
     * Adds given parameter to all saved parameters
     * @param parameter user given parameter
     */
    void addParameter(SearchParameter parameter);

    /**
     * Returns a complete collection of user added parameters
     * @return returns a complete collection of user added parameters
     */
    Collection<SearchParameter> getParameters();
}
