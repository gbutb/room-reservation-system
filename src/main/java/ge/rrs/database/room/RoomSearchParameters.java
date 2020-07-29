// RoomSearchParameters.java
package ge.rrs.database.room;

import java.sql.SQLException;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.SearchParameters;

public class RoomSearchParameters extends SearchParameters {

    public RoomSearchParameters() {
        super();
    }

    /**
     * Adds parameters, which serve fetching rooms which are on the
     * user given range of floors
     *
     * @param start start of the floor range
     * @param end   end of the floor range (inclusive)
     */
    public void addFloorRangeParameter(int start, int end) throws Exception {
        addParameter(RoomSearchParameter.fromFloorRange(start, end));
    }

    /**
     * Adds parameters, which serve fetching rooms which are
     * available (not reserved) in the user given period of time (date-time)
     *
     * @param dateFrom   start of the given time period
     * @param dateTo     end of the given time period
     * @param connection connection to get reservations in the given
     *                   time range from the database to get
     *                   corresponding reserved rooms to ignore them
     * @throws SQLException SQL database error
     */
    public void addDateTimeRangeParameter(String dateFrom, String dateTo, DBConnection connection) throws Exception {
        addParameter(RoomSearchParameter.fromDateTimeRange(dateFrom, dateTo, connection));
    }

    public void addAirConditionerParameter() throws Exception {
        addParameter(RoomSearchParameter.withAirConditioner());
    }

    public void addProjectorParameter() throws Exception {
        addParameter(RoomSearchParameter.withProjector());
    }

    public void addRoomSizeParameter(int size) throws Exception {
        addParameter(RoomSearchParameter.withRoomSize(size));
    }
}
