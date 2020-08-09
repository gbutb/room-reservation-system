// RoomSearchParameter.java
package ge.rrs.database.room;

import ge.rrs.database.DBConnection;
import ge.rrs.database.SearchParameter;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.reservation.ReservationSearchParameters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// ge.rrs

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
    public RoomSearchParameter(String key, String relation, String valueExpression, List<String> args) {
        this.key = key;
        this.relation = relation;
        this.valueExpression = valueExpression;
        this.args = args;
    }

    public static RoomSearchParameter fromFloor(int floor) {
        return new RoomSearchParameter(
                "floor", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + floor);
                    }
                });
    }

    static RoomSearchParameter withAirConditioner() {
        return new RoomSearchParameter(
                "air_conditioner", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + 1);
                    }
                }
        );
    }

    static RoomSearchParameter withProjector() {
        return new RoomSearchParameter(
                "projector", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + 1);
                    }
                }
        );
    }

    static RoomSearchParameter withRoomSize(int mini, int small, int medium, int large) {
        return new RoomSearchParameter(
                "room_size", " IN ", "(?, ?, ?, ?)",
                new ArrayList<String>() {
                    {
                        add("" + mini);
                        add("" + small);
                        add("" + medium);
                        add("" + large);
                    }
                }
        );
    }

    static RoomSearchParameter withRoomId(int id) {
        return new RoomSearchParameter(
                "room_id", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + id);
                    }
                }
        );
    }

    /**
     * Fetches reservations that overlap the given time period and
     * then returns a parameter which ignores rooms corresponding
     * to the overlapping reservations
     *
     * @param dateFrom   start of the given time period
     * @param dateTo     end of the given time period
     * @param connection DBConnection
     * @return returns corresponding parameter
     * @throws Exception error
     */
    static RoomSearchParameter fromDateTimeRange(String dateFrom, String dateTo, DBConnection connection) throws Exception {
        ReservationSearchParameters parameters = new ReservationSearchParameters();
        parameters.addDateTimeRangeOverlapParameter(dateFrom, dateTo);
        Collection<Reservation> filteredReservations =
                Reservation.getFilteredReservations(parameters, connection);

        parameters = new ReservationSearchParameters();
        parameters.addTodaysRepeatedParameter(LocalDateTime.now());
        filteredReservations.addAll(Reservation.compareAndFilterReservations(
                Reservation.getFilteredReservations(parameters, connection), dateFrom, dateTo
        ));

        StringBuilder valueExpression = new StringBuilder();
        List<String> args = new ArrayList<>();
        for (Reservation reservation : filteredReservations) {
            if (valueExpression.length() != 0) {
                valueExpression.append(", ");
            }
            valueExpression.append("?");

            args.add(Integer.toString(reservation.getRoomId()));
        }

        if (valueExpression.length() == 0) {
            return new RoomSearchParameter("1", "=", "1", new ArrayList<>());
        }

        valueExpression.insert(0, "(");
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
                valueExpression.replace("?", "%s"),
                args.toArray());
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
