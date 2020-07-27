package ge.rrs;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class RoomSearchParamTest {
    @Test
    public void testValueParsing() throws Exception {
        RoomSearchParameter param = new RoomSearchParameter(
            "", "", "? ?", new String[] {"A", "BC"});
        assertEquals("A BC", param.getValue());
    }    
}