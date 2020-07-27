package ge.rrs;

import java.util.ArrayList;
import java.util.List;

public class ReservationSearchParameter implements SearchParameter {

    // Search parameters
    private final String key;
    private final String valueExpression;
    private final String relation;
    private final List<String> args;

    private boolean empty;

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

    static ReservationSearchParameter startsBefore(String dateTime, boolean inclusive) {
        String relation = " < ";
        if (inclusive) relation = " <= ";
        return new ReservationSearchParameter(
                "start_date", relation, "?",
                new ArrayList<String>() {
                    {
                        add(dateTime);
                    }
                });
    }

    static ReservationSearchParameter endsAfter(String dateTime, boolean inclusive) {
        String relation = " > ";
        if (inclusive) relation = " >= ";
        return new ReservationSearchParameter(
                "end_date", relation, "?",
                new ArrayList<String>() {
                    {
                        add(dateTime);
                    }
                });
    }

    static ReservationSearchParameter startsBeforeTime(String time, boolean inclusive) {
        String relation = " < ";
        if (inclusive) relation = " <= ";
        return new ReservationSearchParameter(
                "end_date", relation, "TIME(?)",
                new ArrayList<String>() {
                    {
                        add(time);
                    }
                });
    }

    static ReservationSearchParameter endsAfterTime(String time, boolean inclusive) {
        String relation = " > ";
        if (inclusive) relation = " >= ";
        return new ReservationSearchParameter(
                "end_date", relation, "TIME(?)",
                new ArrayList<String>() {
                    {
                        add(time);
                    }
                });
    }

    static ReservationSearchParameter isRepeated(boolean repeated) {
        String repeatedStr = "FALSE";
        if (repeated) repeatedStr = "TRUE";

        List<String> arguments = new ArrayList<>();
        arguments.add(repeatedStr);

        return new ReservationSearchParameter(
                "do_repeat", " = ", "?", arguments);
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
