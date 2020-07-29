package ge.rrs;

import org.junit.jupiter.api.BeforeEach;
// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class SearchParametersTest {
    private SearchParameters searchParameters;

    @BeforeEach
    public void initialize() {
        searchParameters = new SearchParameters();
    }

    @Test
    public void testEmpty() throws Exception {
        assertEquals("", searchParameters.getParametersStatement());

        searchParameters.addParameter(new FreeSearchParameter());
        assertEquals("", searchParameters.getParametersStatement());

        searchParameters.addParameter("AND", new FreeSearchParameter());
        assertEquals("", searchParameters.getParametersStatement());
    }

    @Test
    public void testSingle() throws Exception {
        searchParameters.addParameter(new FreeSearchParameter("key", "=", "value"));
        assertEquals("key=?", searchParameters.getParametersStatement());
        assertArrayEquals(
            new String[] {"value"},
            searchParameters.getArguments().toArray());
    }

    @Test
    public void testMultiple() throws Exception {
        int numParams = 10;
        StringBuilder expected = new StringBuilder();
        if (numParams > 1)
            expected.append("(");
        for (int i = 0; i < numParams; ++i) {
            // Build from search params
            searchParameters.addParameter(
                new FreeSearchParameter(
                    String.format("param%d", i),
                    "=",
                    String.format("%d", i)));
            
            // Build the expected
            expected.append(
                String.format("param%d=?", i));
            if (i != numParams - 1)
                expected.append(" AND ");
        }
        if (numParams > 1)
            expected.append(")");

        assertEquals(
            expected.toString(),
            searchParameters.getParametersStatement());
    }
}