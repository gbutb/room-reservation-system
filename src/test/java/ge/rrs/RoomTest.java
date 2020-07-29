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
        params.addParameter(RoomSearchParameter.fromFloorRange(3, 4));
        Collection<? extends TableEntry> rooms = Room.getFilteredRooms(params, connection);
        assertEquals(51, rooms.size());
    }
}
