// RoomTest.java
package ge.rrs;

import java.sql.SQLException;
import java.util.*;

// JUnit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameter;


public class RoomTest {
    // Database credentials
    private DBConnection connection;

    @BeforeEach
    public void initialize() throws SQLException {
        connection = new DBConnection(
            MockDatabaseCredentials.SERVER,
            MockDatabaseCredentials.USER,
            MockDatabaseCredentials.PASSWORD,
            MockDatabaseCredentials.DB_NAME);
    }

    @Test
    public void testAllQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(97, rooms.size());
    }

    @Test
    public void testSomeQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("floor", "=", "2"));
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(26, rooms.size());
    }

    @Test
    public void testRoomSearchParamQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(RoomSearchParameter.fromFloor(3));
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(28, rooms.size());
    }
}
