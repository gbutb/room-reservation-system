// ReservationSearchParametersTest.java
package ge.rrs;

import java.sql.SQLException;
import java.util.*;

// JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.reservation.ReservationSearchParameters;


public class ReservationSearchParametersTest {
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
    public void testDateTimeOverlap() throws Exception {
        int overlapNumber[] = new int[] {1, 2, 1, 2, 2, 3};
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
            5, Reservation.getFilteredReservations(params, connection).size());
    }
}
