// DatabaseTest.java
package ge.rrs;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    @Test
    public void testDatabase() {
        boolean threwError = false;
        try {
            DBConnection connection = new DBConnection();
        } catch (Exception _) {
            threwError = true;
        }

        assertFalse(threwError);
    }
}
