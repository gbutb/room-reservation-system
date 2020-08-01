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

    public void addFloorParameter(int floor) throws Exception {
        addParameter(RoomSearchParameter.fromFloor(floor));
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

    public void addRoomSizeParameter(int mini, int small, int medium, int large) throws Exception {
        addParameter(RoomSearchParameter.withRoomSize(mini, small, medium, large));
    }
}
