// RoomSearchParameter.java
package ge.rrs;

public class RoomSearchParameter implements SearchParameter {
    // Search parameters
    private String key;
    private String relation;
    private String valueExpression;
    private String[] args;

    /**
     * Initializes Room Search parameter.
     * @param key The key of the query.
     * @param relation Relation between the key and value.
     * @param valueExpression Expression, with arguments replaced with '?'
     * @param args Arguments.
     */
    RoomSearchParameter(String key, String relation, String valueExpression, String[] args) {
        this.key = key;
        this.relation = relation;
        this.valueExpression = valueExpression;
        this.args = args;
    }

    /**
     * Creates a Room search parameter between
     * start floor and the end floor.
     * @param start: Initial floor.
     * @param end: Final floor.
     * @return: Room Search Parameter.
     */
    static RoomSearchParameter fromFloorRange(int start, int end) {
        RoomSearchParameter param = new RoomSearchParameter(
            "floor", " BETWEEN ", "? AND ?", new String[] {
                ""+start, ""+end});
        return param;
    }

    @Override
    public String getKey() throws Exception {
        return key;
    }

    @Override
    public String getValue() throws Exception {
        return String.format(
            valueExpression.replace("?", "%s"), (Object[])args);
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
