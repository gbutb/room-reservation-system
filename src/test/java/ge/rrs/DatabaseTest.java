// DatabaseTest.java
package ge.rrs;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    @Test
    public void testDatabase() {
        // TODO: Allow DB connection to throw an error.
        DBConnection connection = new DBConnection();
    }
}
