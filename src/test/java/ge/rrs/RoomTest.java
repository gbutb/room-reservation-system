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
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter());
        Room nullRoom = new Room(connection);
        Collection<? extends TableEntry> rooms = nullRoom.filter(params);
        assertEquals(2, rooms.size());
    }

    @Test
    public void testSomeQuery() throws Exception {
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter("floor", "=", "2"));
        Room nullRoom = new Room(connection);
        Collection<? extends TableEntry> rooms = nullRoom.filter(params);
        assertEquals(1, rooms.size());
    }

    @Test
    public void testRoomSearchParamQuery() throws Exception {
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(RoomSearchParameter.fromFloorRange(3, 4));
        Room nullRoom = new Room(connection);
        Collection<? extends TableEntry> rooms = nullRoom.filter(params);
        assertEquals(1, rooms.size());
    }
}
