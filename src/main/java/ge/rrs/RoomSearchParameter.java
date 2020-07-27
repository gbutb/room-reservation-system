// RoomSearchParameter.java
package ge.rrs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomSearchParameter implements SearchParameter {

    // Search parameters
    private final String key;
    private final String relation;
    private final String valueExpression;
    private final List<String> args;

    /**
     * Initializes Room Search parameter.
     *
     * @param key             The key of the query.
     * @param relation        Relation between the key and value.
     * @param valueExpression Expression, with arguments replaced with '?'
     * @param args            Arguments.
     */
    RoomSearchParameter(String key, String relation, String valueExpression, List<String> args) {
        this.key = key;
        this.relation = relation;
        this.valueExpression = valueExpression;
        this.args = args;
    }

    /**
     * Creates a Room search parameter between
     * start floor and the end floor.
     *
     * @param start Initial floor.
     * @param end   Final floor.
     * @return Room Search Parameter.
     */
    static RoomSearchParameter fromFloorRange(int start, int end) {
        return new RoomSearchParameter(
                "floor", " BETWEEN ", "? AND ?",
                new ArrayList<String>() {
                    {
                        add("" + start);
                        add("" + end);
                    }
                });
    }

    static RoomSearchParameter withAirConditioner() {
        return new RoomSearchParameter(
                "conditioner", " = ", "true", new ArrayList<>()
        );
    }

    static RoomSearchParameter withProjector() {
        return new RoomSearchParameter(
                "projector", " = ", "true", new ArrayList<>()
        );
    }

    static RoomSearchParameter withRoomSize(int size) {
        return new RoomSearchParameter(
                "room_size", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + size);
                    }
                }
        );
    }

    /**
     * Fetches reservations that overlap the given time period and
     * then returns a parameter which ignores rooms corresponding
     * to the overlapping reservations
     *
     * @param dateFrom start of the given time period
     * @param dateTo end of the given time period
     * @param connection DBConnection
     * @return returns corresponding parameter
     * @throws Exception error
     */
    static RoomSearchParameter fromDateTimeRange(String dateFrom, String dateTo, DBConnection connection) throws Exception {
        ReservationSearchParameters rParams = new ReservationSearchParameters();
        rParams.addDateTimeRangeOverlapParameter(dateFrom, dateTo);
        Collection<Reservation> filteredReservations =
                Reservation.getFilteredReservations(rParams, connection);

        rParams = new ReservationSearchParameters();
        rParams.addRepeatAndTimeRangeOverlapParameter(dateFrom, dateTo);
        filteredReservations.addAll(Reservation.getFilteredReservations(rParams, connection));

        StringBuilder valueExpression = new StringBuilder();
        valueExpression.append("(");
        List<String> args = new ArrayList<>();
        for (Reservation reservation : filteredReservations) {
            if (valueExpression.length() != 0) valueExpression.append(", ");
            valueExpression.append("?");
            args.add(Integer.toString(reservation.getRoomId()));
        }
        valueExpression.append(")");

        return new RoomSearchParameter("room_id", " NOT IN ", valueExpression.toString(), args);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return String.format(
                valueExpression.replace("?", "%s"), args);
    }

    @Override
    public String getValueExpression() {
        return valueExpression;
    }

    @Override
    public List<String> getValueArgs() {
        return args;
    }

    @Override
    public String getRelation() {
        return relation;
    }
}
