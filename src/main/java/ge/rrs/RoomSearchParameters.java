package ge.rrs;

import java.util.ArrayList;
import java.util.Collection;

public class RoomSearchParameters implements SearchParameters {
    private Collection<SearchParameter> parameters;

    public RoomSearchParameters() {
        parameters = new ArrayList<>();
    }

    @Override
    public void addParameter(SearchParameter parameter) {
        parameters.add(parameter);
    }

    public void addFloorRangeParameter(int start, int end) {
        parameters.add(RoomSearchParameter.fromFloorRange(start, end));
    }

//    public void addDateTimeRangeParameter() {
//        RoomSearchParameter.fromDateTimeRange()
//    }

    @Override
    public Collection<SearchParameter> getParameters() {
        return parameters;
    }
}
