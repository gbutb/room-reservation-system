// ReservationSearchParameters.java
package ge.rrs.database.reservation;

// ge.rrs

import ge.rrs.database.SearchParameters;

public class ReservationSearchParameters extends SearchParameters {

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

        Clause startOverlap = new Clause();
        ReservationSearchParameter.Comparator more = ReservationSearchParameter.Comparator.MORE;
        ReservationSearchParameter.Comparator less = ReservationSearchParameter.Comparator.LESS;
        startOverlap.addParameter(ReservationSearchParameter.compareDateTime("start_date", dateFrom, true, more));
        startOverlap.addParameter("AND", ReservationSearchParameter.compareDateTime("start_date", dateTo, false, less));

        Clause endOverlap = new Clause();
        endOverlap.addParameter(ReservationSearchParameter.compareDateTime("end_date", dateFrom, false, less));
        endOverlap.addParameter("AND", ReservationSearchParameter.compareDateTime("end_date", dateTo, true, less));

        tempClause.addClause(startOverlap);
        tempClause.addClause("OR", endOverlap);

        if (clause.isEmpty()) clause.addClause(tempClause);
        else clause.addClause("AND", tempClause);
    }

    /**
     * Adds parameters, which serve fetching repeated reservations
     * which overlap the user given time range (only hours and minutes are considered)
     *
     * @param timeFrom start of the time interval
     * @param timeTo   end of the time interval
     */
    public void addRepeatAndTimeRangeOverlapParameter(String timeFrom, String timeTo) throws Exception {
        Clause tempClause = new Clause();
        tempClause.addParameter(ReservationSearchParameter.isRepeated(true));

        Clause timeRangeClause = new Clause();

        Clause startOverlap = new Clause();
        ReservationSearchParameter.Comparator more = ReservationSearchParameter.Comparator.MORE;
        ReservationSearchParameter.Comparator less = ReservationSearchParameter.Comparator.LESS;
        startOverlap.addParameter(ReservationSearchParameter.compareTime("start_date", timeFrom, true, more));
        startOverlap.addParameter("AND", ReservationSearchParameter.compareTime("start_date", timeTo, false, less));

        Clause endOverlap = new Clause();
        endOverlap.addParameter(ReservationSearchParameter.compareTime("end_date", timeFrom, false, more));
        endOverlap.addParameter("AND", ReservationSearchParameter.compareTime("end_date", timeTo, true, less));

        timeRangeClause.addClause(startOverlap);
        timeRangeClause.addClause("OR", endOverlap);

        tempClause.addClause("AND", timeRangeClause);

        if (clause.isEmpty()) clause.addClause(tempClause);
        else clause.addClause("AND", tempClause);
    }

    /**
     * Adds parameters, which serve fetching reservations
     * which contain the user given date
     *
     * @param date user given date
     * @throws Exception error
     */
    public void addRoomSpecificDateOverlapParameter(int roomId, String date) throws Exception {
        Clause tempClause = new Clause();

        tempClause.addParameter(ReservationSearchParameter.ofRoom(roomId));

        Clause timeContainClause = new Clause();
        timeContainClause.addParameter(ReservationSearchParameter.containsDate(date));

        Clause rptTimeContainClause = new Clause();
        rptTimeContainClause.addParameter(ReservationSearchParameter.isRepeated(true));
        rptTimeContainClause.addParameter("AND", ReservationSearchParameter.containsTime(date));

        timeContainClause.addClause("OR", rptTimeContainClause);

        tempClause.addClause("AND", timeContainClause);

        if (clause.isEmpty()) clause.addClause(tempClause);
        else clause.addClause("AND", tempClause);
    }
}
