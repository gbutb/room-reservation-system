// RoomSearchParamTest.java
package ge.rrs;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

// ge.rrs
import ge.rrs.database.room.RoomSearchParameter;

public class RoomSearchParamTest {
    @Test
    public void testValueParsing() throws Exception {
        RoomSearchParameter param = new RoomSearchParameter(
            "", "", "? ?", Arrays.asList(new String[] {"A", "BC"}));
        assertEquals("A BC", param.getValue());
    }    
}