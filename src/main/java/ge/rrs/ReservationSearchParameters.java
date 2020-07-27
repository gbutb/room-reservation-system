package ge.rrs;

import java.util.ArrayList;
import java.util.Collection;

public class ReservationSearchParameters implements SearchParameters {

    private Collection<SearchParameter> parameters;

    public ReservationSearchParameters() {
        parameters = new ArrayList<>();
    }

    @Override
    public void addParameter(SearchParameter parameter) {
        parameters.add(parameter);
    }

    public void addDateTimeRangeParameter(String dateFrom, String dateTo) {
        parameters.add(ReservationSearchParameter.startsAfter(dateFrom));
        parameters.add(ReservationSearchParameter.endsBefore(dateTo));
    }

    public void addRepeatedInTimeRangeParameter(String timeFrom, String timeTo) {
        parameters.add(ReservationSearchParameter.startsAfterTime(timeFrom));
        parameters.add(ReservationSearchParameter.endsBeforeTime(timeTo));
        parameters.add(ReservationSearchParameter.isRepeated(true));
    }

    @Override
    public Collection<SearchParameter> getParameters() {
        return parameters;
    }
}
