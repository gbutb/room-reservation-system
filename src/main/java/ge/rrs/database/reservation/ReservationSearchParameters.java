// ReservationSearchParameters.java
package ge.rrs.database.reservation;

// ge.rrs

import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReservationSearchParameters extends SearchParameters {

    private static final ReservationSearchParameter.Comparator MORE = ReservationSearchParameter.Comparator.MORE;
    private static final ReservationSearchParameter.Comparator LESS = ReservationSearchParameter.Comparator.LESS;

    private static final int SQL_WEEKDAY_OFFSET = -1;

    public ReservationSearchParameters() {
        super();
    }

    /**
     * Adds parameters, which serve fetching reservations
     * which overlap the user given exact date-time range
     *
     * @param dateFrom start of the date-time interval
     * @param dateTo   end of the date-time interval
     */
    public void addDateTimeRangeOverlapParameter(String dateFrom, String dateTo) throws Exception {
        Clause tempClause = new Clause();

        tempClause.addParameter(ReservationSearchParameter.isRepeated(false));

        tempClause.addParameter(
                "AND",
                ReservationSearchParameter.compareDateTime(
                        "end_date", MORE, dateFrom, false
                )
        );

        tempClause.addParameter(
                "AND",
                ReservationSearchParameter.compareDateTime(
                        "start_date", LESS, dateTo, false
                )
        );

        if (clause.isEmpty()) clause.addClause(tempClause);
        else clause.addClause("AND", tempClause);
    }

    /**
     * Adds parameters, which serve fetching repeated reservations
     * of today
     */
    public void addTodaysRepeatedParameter() throws Exception {
        // TODO repeated parameter setting can be done with another method
        Clause tempClause = new Clause();
        tempClause.addParameter(ReservationSearchParameter.isRepeated(true));

        Clause timeClause = new Clause();

        DateTimeFormatter dtfWeekday = DateTimeFormatter.ofPattern("e");
        DateTimeFormatter dtfHour = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();

        String comparator1;
        String comparator2;
        String weekday;

        if (Integer.parseInt(dtfHour.format(now)) < 9) {
            comparator1 = " >= ";
            comparator2 = " < ";
            weekday = "" + (Integer.parseInt(dtfWeekday.format(now.minusDays(1))) + SQL_WEEKDAY_OFFSET);
        } else {
            comparator1 = " < ";
            comparator2 = " >= ";
            weekday = "" + (Integer.parseInt(dtfWeekday.format(now.plusDays(1))) + SQL_WEEKDAY_OFFSET);
        }

        Clause nightClause = new Clause();
        Clause dayClause = new Clause();

        dayClause.addParameter(
                new FreeSearchParameter(
                        "WEEKDAY(start_date)", " = ", weekday
                )
        );

        dayClause.addParameter(
                "AND",
                new ReservationSearchParameter(
                        "TIME(start_date)", comparator1, "TIME(?)",
                        new ArrayList<String>() {
                            {
                                add("09:00");
                            }
                        }
                )
        );

        nightClause.addParameter(
                new FreeSearchParameter(
                        "WEEKDAY(start_date)", " = ",
                        "" + (Integer.parseInt(dtfWeekday.format(now)) + SQL_WEEKDAY_OFFSET)
                )
        );

        nightClause.addParameter(
                "AND",
                new ReservationSearchParameter(
                        "TIME(start_date)", comparator2, "TIME(?)",
                        new ArrayList<String>() {
                            {
                                add("09:00");
                            }
                        }
                )
        );

        timeClause.addClause(dayClause);
        timeClause.addClause("OR", nightClause);
        tempClause.addClause("AND", timeClause);

        if (clause.isEmpty()) clause.addClause(tempClause);
        else clause.addClause("AND", tempClause);
    }
}
