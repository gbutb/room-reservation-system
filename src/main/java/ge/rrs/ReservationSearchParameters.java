package ge.rrs;

import java.util.ArrayList;
import java.util.List;

public class ReservationSearchParameters implements SearchParameters {

    // Data structure to construct a statement from all user given parameters
    private final StringBuilder parameters;
    // Data Structure to save all parameters' value expression's arguments
    List<String> arguments;

    public ReservationSearchParameters() {
        parameters = new StringBuilder();
        arguments = new ArrayList<>();
    }

    @Override
    public void addParameter(SearchParameter parameter) throws Exception {
        if (parameters.length() != 0) parameters.append(" AND ");
        parameters.append(parameter.getKey());
        parameters.append(parameter.getRelation());
        parameters.append(parameter.getValueExpression());

        arguments.addAll(parameter.getValueArgs());
    }

    @Override
    public void addParameter(String operator, SearchParameter parameter) throws Exception {
        if (parameters.length() != 0) parameters.append(operator);
        parameters.append(parameter.getKey());
        parameters.append(parameter.getRelation());
        parameters.append(parameter.getValueExpression());

        arguments.addAll(parameter.getValueArgs());
    }

    @Override
    public void addParametersClause(SearchParameter... inputParameters) throws Exception {
        if (parameters.length() != 0) parameters.append(" AND ");
        parameters.append("( ");
        for (SearchParameter parameter : inputParameters) {
            parameters.append(parameter.getKey());
            parameters.append(parameter.getRelation());
            parameters.append(parameter.getValueExpression());

            arguments.addAll(parameter.getValueArgs());
        }
        parameters.append(" )");
    }

    @Override
    public void addParametersClause(String operator, SearchParameter... inputParameters) throws Exception {
        if (parameters.length() != 0) parameters.append(operator);
        parameters.append("( ");
        for (SearchParameter parameter : inputParameters) {
            parameters.append(parameter.getKey());
            parameters.append(parameter.getRelation());
            parameters.append(parameter.getValueExpression());

            arguments.addAll(parameter.getValueArgs());
        }
        parameters.append(" )");
    }

    @Override
    public String getParametersStatement() {
        return parameters.toString();
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Adds parameters, which serve fetching reservations
     * which overlap the user given exact date-time range
     *
     * @param dateFrom start of the date-time interval
     * @param dateTo   end of the date-time interval
     */
    public void addDateTimeRangeOverlapParameter(String dateFrom, String dateTo) throws Exception {
        addParametersClause(ReservationSearchParameter.startsBefore(dateFrom, true),
                ReservationSearchParameter.endsAfter(dateFrom, false));
        addParametersClause(" OR ", ReservationSearchParameter.startsBefore(dateTo, false),
                ReservationSearchParameter.endsAfter(dateTo, true));

    }

    /**
     * Adds parameters, which serve fetching repeated reservations
     * which overlap the user given time range (only hours and minutes are considered)
     *
     * @param timeFrom start of the time interval
     * @param timeTo   end of the time interval
     */
    public void addRepeatAndTimeRangeOverlapParameter(String timeFrom, String timeTo) throws Exception {
        addParameter(ReservationSearchParameter.isRepeated(true));
        addParametersClause(ReservationSearchParameter.startsBeforeTime(timeFrom, true),
                ReservationSearchParameter.endsAfterTime(timeFrom, false));
        addParametersClause(" OR ", ReservationSearchParameter.startsBeforeTime(timeTo, false),
                ReservationSearchParameter.endsAfterTime(timeTo, true));
    }

    /**
     * Adds parameters, which serve fetching reservations
     * which contain the user given date
     *
     * @param date user given date
     * @throws Exception error
     */
    public void addRoomSpecificDateOverlapParameter(String date) throws Exception {
        addParameter(ReservationSearchParameter.containsDate(date));
        addParametersClause(" OR ", ReservationSearchParameter.containsTime(date),
                ReservationSearchParameter.isRepeated(true));
    }
}
