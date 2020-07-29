package ge.rrs;

import java.util.ArrayList;
import java.util.List;

public class SearchParameters {
    /*
    NOTE
    For enhanced security, parameters are saved with value expression's arguments
    replaced with question marks and value expression's arguments are stored
    separately
     */

    protected final Clause clause;

    public SearchParameters() {
        clause = new Clause();
    }

    /**
     * Processes and adds given parameter to all saved parameters
     * Given parameter (if not the first parameter) is logically
     * connected with previous parameters with "AND" operator
     *
     * @param parameter user given parameter
     */
    public void addParameter(SearchParameter parameter) throws Exception {
        if (parameter.isEmpty()) return;
        if (clause.isEmpty()) {
            clause.addParameter(parameter);
            return;
        }
        clause.addParameter("AND", parameter);
    }

    /**
     * Processes and adds given parameter to all saved parameters
     * Given parameter (if not the first parameter) is logically
     * connected with previous parameters with the user given operator
     *
     * @param parameter user given parameter
     * @param operator  user given operator
     */
    public void addParameter(String operator, SearchParameter parameter) throws Exception {
        if (parameter.isEmpty()) return;
        if (clause.isEmpty()) {
            clause.addParameter(parameter);
            return;
        }
        clause.addParameter(operator, parameter);
    }

    /**
     * Returns a complete collection of user added parameters
     *
     * @return returns a complete collection of user added parameters
     */
    public String getParametersStatement() {
        return (clause.isEmpty()) ? "" : clause.getClause();
    }

    /**
     * Returns a string list of value expressions' arguments
     *
     * @return returns a string list of value expressions' arguments
     */
    public List<String> getArguments() {
        return clause.getArguments();
    }

    protected class Clause {

        // Data structure to construct a statement from all given parameters
        private final StringBuilder clause;
        // Data Structure to save all parameters' arguments
        private List<String> arguments;

        private boolean closed;

        public Clause() {
            clause = new StringBuilder();
            arguments = new ArrayList<>();
            closed = false;
            clause.append("(");
        }

        public void addClause(String operator, Clause clause) {
            if (closed) return;
            this.clause.append(" " + operator + " ");
            addClause(clause);
        }

        public void addClause(Clause clause) {
            if (closed) return;
            this.clause.append(clause.getClause());
            arguments.addAll(clause.getArguments());
        }

        public void addParameter(SearchParameter parameter) throws Exception {
            if (closed) return;
            clause.append(parameter.getKey());
            clause.append(parameter.getRelation());
            clause.append(parameter.getValueExpression());

            arguments.addAll(parameter.getValueArgs());
        }

        public void addParameter(String operator, SearchParameter parameter) throws Exception {
            if (closed) return;
            this.clause.append(" " + operator + " ");
            addParameter(parameter);
        }

        public boolean isEmpty() {
            return clause.length() == 1;
        }

        public List<String> getArguments() {
            return arguments;
        }

        public String getClause() {
            if (!closed) {
                clause.append(")");
                closed = true;
            }
            return clause.toString();
        }

    }
}
