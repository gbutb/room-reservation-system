package ge.rrs;

public class ReservationSearchParameter implements SearchParameter {

    // Search parameters
    private String key;
    private String valueExpression;
    private String relation;
    private String[] args;

    private boolean empty;

    /**
     * Initializes Reservation Search parameter.
     *
     * @param key             The key of the query.
     * @param relation        Relation between the key and value.
     * @param valueExpression Expression, with arguments replaced with '?'
     * @param args            Arguments.
     */
    public ReservationSearchParameter(String key, String relation, String valueExpression, String[] args) {
        this.key = key;
        this.relation = relation;
        this.valueExpression = valueExpression;
        this.args = args;
    }

    static ReservationSearchParameter startsAfter(String dateTime) {
        return new ReservationSearchParameter(
                "start_date", " > ", "?", new String[] {dateTime});
    }

    static ReservationSearchParameter endsBefore(String dateTime) {
        return new ReservationSearchParameter(
                "end_date", " < ", "?", new String[] {dateTime});
    }

    static ReservationSearchParameter startsAfterTime(String time) {
        return new ReservationSearchParameter(
                "start_date", " > ", "TIME(?)", new String[] {time});
    }

    static ReservationSearchParameter endsBeforeTime(String time) {
        return new ReservationSearchParameter(
                "end_date", " < ", "TIME(?)", new String[] {time});
    }

    static ReservationSearchParameter isRepeated(boolean repeated) {
        String repeatedStr = "FALSE";
        if (repeated) repeatedStr = "TRUE";
        return new ReservationSearchParameter(
                "do_repeat", " = ", "?", new String[] {repeatedStr});
    }

    @Override
    public String getKey() throws Exception {
        return key;
    }

    @Override
    public String getValue() throws Exception {
        return String.format(
                valueExpression.replace("?", "%s"), (Object) args);
    }

    @Override
    public String getValueExpression() throws Exception {
        return valueExpression;
    }

    @Override
    public String[] getValueArgs() throws Exception {
        return args;
    }

    @Override
    public String getRelation() throws Exception {
        return relation;
    }
}
