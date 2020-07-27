package ge.rrs;

import java.util.Collection;

public interface SearchParameters {

    void addParameter(SearchParameter parameter);

    Collection<SearchParameter> getParameters();
}
