package ge.rrs;

import com.sun.org.apache.xpath.internal.operations.And;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        startOverlap.addParameter(ReservationSearchParameter.startsBefore(dateFrom, true));
        startOverlap.addParameter("AND", ReservationSearchParameter.endsAfter(dateFrom, false));

        Clause endOverlap = new Clause();
        endOverlap.addParameter(ReservationSearchParameter.startsBefore(dateTo, false));
        endOverlap.addParameter("AND", ReservationSearchParameter.endsAfter(dateTo, true));

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
        startOverlap.addParameter(ReservationSearchParameter.startsBeforeTime(timeFrom, true));
        startOverlap.addParameter("AND", ReservationSearchParameter.endsAfterTime(timeFrom, false));

        Clause endOverlap = new Clause();
        endOverlap.addParameter(ReservationSearchParameter.startsBeforeTime(timeTo, false));
        endOverlap.addParameter("AND", ReservationSearchParameter.endsAfterTime(timeTo, true));

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
