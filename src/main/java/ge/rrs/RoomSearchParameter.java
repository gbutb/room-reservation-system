// RoomSearchParameter.java
package ge.rrs;

public class RoomSearchParameter implements SearchParameter {
    // Search parameters
    private String key;
    private String value;
    private String relation;

    RoomSearchParameter(String key, String relation, String value) {
        this.key = key;
        this.relation = relation;
        this.value = value;
    }

    /**
     * Creates a Room search parameter between
     * start floor and the end floor.
     * @param start: Initial floor.
     * @param end: Final floor.
     * @return: Room Search Parameter.
     */
    static RoomSearchParameter fromFloorRange(int start, int end) {
        // TODO: fix this.
        RoomSearchParameter param = new RoomSearchParameter(
            "floor", " BETWEEN ", String.format(
                "%d AND %d", start, end));
        return param;
    }

    @Override
    public String getKey() throws Exception {
        return key;
    }

    @Override
    public String getValue() throws Exception {
        return value;
    }

    @Override
    public String getRelation() throws Exception {
        return relation;
    }
}
