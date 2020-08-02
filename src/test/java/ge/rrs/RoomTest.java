// RoomTest.java
package ge.rrs;

import java.util.*;

import org.junit.jupiter.api.AfterAll;
// JUnit
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.Assert.*;


// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameter;

@TestInstance(Lifecycle.PER_CLASS)
public class RoomTest {
    // Database credentials
    private DBConnection connection;

    @BeforeAll
    public void initialize() throws Exception {
        connection = new DBConnection(
            MockDatabaseCredentials.SERVER,
            MockDatabaseCredentials.USER,
            MockDatabaseCredentials.PASSWORD,
            MockDatabaseCredentials.DB_NAME);
        connection.executeSQLFrom(MockDatabaseCredentials.SOURCE);
    }

    @AfterAll
    public void destroy() throws Exception {
        connection.executeSQLFrom(MockDatabaseCredentials.CLEAN);
        connection.closeConnection();
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

    @Test
    public void testInsert() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        int previousSize = Room.getFilteredRooms(params, connection).size();

        Room newRoom = new Room(500, 20, 5, false, true, "", connection);
        newRoom.insertEntry();

        int newSize = Room.getFilteredRooms(params, connection).size();
        assertEquals(previousSize + 1, newSize);
    }
}
