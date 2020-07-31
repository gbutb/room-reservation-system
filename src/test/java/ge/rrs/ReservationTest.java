// ReservationTest.java
package ge.rrs;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

// JUnit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.reservation.Reservation;

public class ReservationTest {
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
    public void testReservationQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        Collection<Reservation> reservations =
            Reservation.getFilteredReservations(params, connection);
        assertTrue(reservations.size() > 0);
    }

    @Test
    public void testAddNewReservation() throws Exception {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        int previous_size = Reservation.getFilteredReservations(params, connection).size();

        // Create new reservation
        Reservation reservation = new Reservation(
            205,
            formatter.format(datetime),
            formatter.format(datetime),
            false, 1, connection);
        reservation.insertEntry();

        int new_size = Reservation.getFilteredReservations(params, connection).size();
        assertEquals(previous_size + 1, new_size);
    }

    @Test
    public void testUpdateReservation() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        Collection<Reservation> reservations =
            Reservation.getFilteredReservations(params, connection);
        assertTrue(reservations.size() > 0);

        // Extract first one
        Reservation reservation =
            reservations.toArray(new Reservation[reservations.size()])[0];
        String endDate = reservation.getEndDate();

        // Set end to today
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        reservation.setEndDate(formatter.format(now));
        reservation.updateEntry();

        // Revert changes
        reservation.setEndDate(endDate);
        reservation.updateEntry();

        // TODO: query the value again.
    }
}
