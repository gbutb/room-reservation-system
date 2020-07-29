package ge.rrs;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class RoomTest {
    // Database credentials
    private DBConnection connection;

    @BeforeEach
    public void initialize() throws SQLException {
        connection = new DBConnection(
            "localhost",
            "root",
            "12345678",
            "reservations_db");
    }

    @Test
    public void testAllQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(2, rooms.size());
    }

    @Test
    public void testSomeQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("floor", "=", "2"));
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(1, rooms.size());
    }

    @Test
    public void testRoomSearchParamQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(RoomSearchParameter.fromFloorRange(3, 4));
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(1, rooms.size());
    }
}
