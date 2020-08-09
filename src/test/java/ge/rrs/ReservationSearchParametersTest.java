// ReservationSearchParametersTest.java
package ge.rrs;


// JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.reservation.ReservationSearchParameters;

@TestInstance(Lifecycle.PER_CLASS)
public class ReservationSearchParametersTest {
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
    public void testDateTimeOverlap() throws Exception {
        int overlapNumber[] = new int[] {9, 10, 10, 10, 9, 10};
        for (int i = 1; i <= 6; ++i) {
            ReservationSearchParameters params = new ReservationSearchParameters();
            params.addDateTimeRangeOverlapParameter(
                String.format("2020-08-01 %d:20:00", i),
                String.format("2020-08-01 %d:40:00", i));
            assertEquals(
                String.format("At %d", i),
                overlapNumber[i-1], Reservation.getFilteredReservations(params, connection).size());
        }
    }

    @Test
    public void testAllButOne() throws Exception {
        ReservationSearchParameters params = new ReservationSearchParameters();
        params.addDateTimeRangeOverlapParameter(
            "2020-07-31 23:00:00",
            "2020-08-01 06:00:00");
        assertEquals(
            11, Reservation.getFilteredReservations(params, connection).size());
    }

    @Test
    public void testSingleTime() throws Exception {
        ReservationSearchParameters params = new ReservationSearchParameters();
        params.addDateTimeRangeOverlapParameter(
            "2020-08-01 01:35:00",
            "2020-08-01 01:35:00");
        assertEquals(
            9, Reservation.getFilteredReservations(params, connection).size());
    }

    @Test
    public void testDoRepeat() throws Exception {
        int repeated[] = {1, 1, 1, 1, 1, 1, 2};
        for (int i = 0; i < repeated.length; ++i) {
            ReservationSearchParameters params = new ReservationSearchParameters();
            params.addTodaysRepeatedParameter(
               LocalDateTime.of(2020, 8, 3 + i, 00, 00, 01));
            assertEquals(
                String.format("At %d", i),
                repeated[i], Reservation.getFilteredReservations(params, connection).size());
        }
    }
}
