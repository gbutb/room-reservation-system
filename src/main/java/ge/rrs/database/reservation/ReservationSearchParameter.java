// ReservationSearchParameter.java
package ge.rrs.database.reservation;

import ge.rrs.database.SearchParameter;

import java.util.ArrayList;
import java.util.List;

// ge.rrs

public class ReservationSearchParameter implements SearchParameter {

    // Search parameters
    private final String key;
    private final String valueExpression;
    private final String relation;
    private final List<String> args;

    private boolean empty;

    /**
     * Used by compareDateTime and compareTime
     */
    enum Comparator {
        MORE,
        LESS
    }

    /**
     * Initializes Reservation Search parameter.
     *
     * @param key             The key of the query.
     * @param relation        Relation between the key and value.
     * @param valueExpression Expression, with arguments replaced with '?'
     * @param args            Arguments.
     */
    public ReservationSearchParameter(String key, String relation, String valueExpression, List<String> args) {
        this.key = key;
        this.relation = relation;
        this.valueExpression = valueExpression;
        this.args = args;
    }

    public static ReservationSearchParameter compareDateTime(String dateAnchor, Comparator comparator,
                                                             String dateTime, boolean inclusive) {
        String relation = (comparator == Comparator.MORE ? " >" : " <");
        if (inclusive) relation += "=";
        relation += " ";

        return new ReservationSearchParameter(
                dateAnchor, relation, "STR_TO_DATE(?, ?)",
                new ArrayList<String>() {
                    {
                        add(dateTime);
                        add("%Y-%m-%d %H:%i:%s");
                    }
                });
    }

    public static ReservationSearchParameter compareTime(String dateAnchor, Comparator comparator,
                                                         String dateTime, boolean inclusive) {
        String relation = (comparator == Comparator.MORE ? " >" : " <");
        if (inclusive) relation += "=";
        relation += " ";

        return new ReservationSearchParameter(
                "TIME(" + dateAnchor + ")", relation, "TIME(?)",
                new ArrayList<String>() {
                    {
                        add(dateTime);
                    }
                });
    }

    public static ReservationSearchParameter isRepeated(boolean repeated) {
        return new ReservationSearchParameter(
                "do_repeat", " = ", "?",
                new ArrayList<String>() {
                    {
                        add(repeated ? "1" : "0");
                    }
                });
    }

    public static ReservationSearchParameter containsDate(String date) {
        return new ReservationSearchParameter(
                "STR_TO_DATE(?, ?)", " BETWEEN ", "start_date AND end_date",
                new ArrayList<String>() {
                    {
                        add(date);
                        add("%Y-%m-%d %H:%i:%s");
                    }
                });
    }

    public static ReservationSearchParameter containsTime(String time) {
        return new ReservationSearchParameter(
                "TIME(?)", " BETWEEN ", "TIME(start_date) AND TIME(end_date)",
                new ArrayList<String>() {
                    {
                        add(time);
                    }
                });
    }

    public static ReservationSearchParameter ofRoom(int roomId) {
        return new ReservationSearchParameter(
                "room_id", " = ", "?",
                new ArrayList<String>() {
                    {
                        add("" + roomId);
                    }
                });
    }

    // Getter Methods

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
