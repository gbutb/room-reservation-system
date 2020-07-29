// FreeSearchParameter
package ge.rrs;

public class FreeSearchParameter implements SearchParameter {
    // Search parameters
    private String key;
    private String value;
    private String relation;

    private boolean empty;

    /**
     * FreeSearchParameter is a search parameter
     * with no constraints on the types of key/value and relation.
     * @param key Key of the entry
     * @param relation relation between the key and value.
     * @param value value of the entry
     */
    public FreeSearchParameter(String key, String relation, String value) {
        this.key = key;
        this.relation = relation;
        this.value = value;

        this.empty = false;
    }

    /**
     * Empty constructor.
     */
    public FreeSearchParameter() {
        this.key = null;
        this.value = null;
        this.relation = null;

        this.empty = true;
    }

    public String getKey() throws Exception {
        if (isEmpty())
            throw new Exception("Unable to access key: the parameter is empty.");
        return key;
    }

    public String getValue() throws Exception {
        if (isEmpty())
            throw new Exception("Unable to access value: the parameter is empty.");
        return value;
    }

    public String getValueExpression() throws Exception {
        if (isEmpty())
            throw new Exception("Unable to access value expression: the parameter is empty.");
        return "?";
    }

    public String[] getValueArgs() throws Exception {
        if (isEmpty())
            throw new Exception("Unable to access value args: the parameter is empty.");
        return new String[]{ getValue() };
    }

    public String getRelation() throws Exception {
        if (isEmpty())
            throw new Exception("Unable to access relation: the parameter is empty.");
        return relation;
    }

    public boolean isEmpty() {
        return empty;
    }
}
