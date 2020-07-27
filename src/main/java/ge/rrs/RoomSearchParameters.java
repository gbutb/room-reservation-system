package ge.rrs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomSearchParameters implements SearchParameters {

    // Data structure to save all user given parameters
    private final StringBuilder parameters;
    // Data Structure to save all parameters' value expression's arguments
    List<String> arguments;

    public RoomSearchParameters() {
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
     * Adds parameters, which serve fetching rooms which are on the
     * user given range of floors
     *
     * @param start start of the floor range
     * @param end   end of the floor range (inclusive)
     */
    public void addFloorRangeParameter(int start, int end) throws Exception {
        addParameter(RoomSearchParameter.fromFloorRange(start, end));
    }

    /**
     * Adds parameters, which serve fetching rooms which are
     * available (not reserved) in the user given period of time (date-time)
     *
     * @param dateFrom   start of the given time period
     * @param dateTo     end of the given time period
     * @param connection connection to get reservations in the given
     *                   time range from the database to get
     *                   corresponding reserved rooms to ignore them
     * @throws SQLException SQL database error
     */
    public void addDateTimeRangeParameter(String dateFrom, String dateTo, DBConnection connection) throws Exception {
        addParameter(RoomSearchParameter.fromDateTimeRange(dateFrom, dateTo, connection));
    }

    public void addAirConditionerParameter() throws Exception {
        addParameter(RoomSearchParameter.withAirConditioner());
    }

    public void addProjectorParameter() throws Exception {
        addParameter(RoomSearchParameter.withProjector());
    }

    public void addRoomSizeParameter(int size) throws Exception {
        addParameter(RoomSearchParameter.withRoomSize(size));
    }
}
