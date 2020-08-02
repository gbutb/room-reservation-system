// Room.java
package ge.rrs.database.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.reservation.ReservationSearchParameters;
import ge.rrs.database.room.comment.RoomComment;

public class Room extends TableEntry {

    // The name of the table to which
    // this table entry corresponds to.
    private static final String TABLE_NAME = "rooms";

    // Names of the table columns to which
    // the instance objects of this class correspond to.
    private static final String ROOM_ID_NAME = "room_id";
    private static final String ROOM_SIZE_NAME = "room_size";
    private static final String FLOOR_NAME = "floor";
    private static final String CONDITIONER_NAME = "air_conditioner";
    private static final String PROJECTOR_NAME = "projector";
    private static final String RENDER_DATA_NAME = "render_data";

    // Reference to DBConnection
    private final DBConnection connection;

    // Room Details
    private Integer roomId;
    private int roomSize;
    private int floor;
    private boolean conditioner;
    private boolean projector;
    private String renderData;

    /**
     * Initializes Room using given ResultSet's current row
     *
     * @param rSet       user given ResultSet of a database query of filtered rooms
     * @param connection DBConnection
     * @throws SQLException SQL database error
     */
    public Room(ResultSet rSet, DBConnection connection) throws SQLException {
        roomId = rSet.getInt(ROOM_ID_NAME);
        roomSize = rSet.getInt(ROOM_SIZE_NAME);
        floor = rSet.getInt(FLOOR_NAME);
        conditioner = rSet.getBoolean(CONDITIONER_NAME);
        projector = rSet.getBoolean(PROJECTOR_NAME);
        renderData = rSet.getString(RENDER_DATA_NAME);
        this.connection = connection;
    }

    public Room(int roomId, int roomSize, int floor, boolean conditioner, boolean projector,
                String renderData, DBConnection connection) {
        this.roomId = roomId;
        this.roomSize = roomSize;
        this.floor = floor;
        this.conditioner = conditioner;
        this.projector = projector;
        this.renderData = renderData;

        this.connection = connection;
    }

    /**
     * Null Constructor.
     *
     * @param connection A reference to DB connection.
     */
    public Room(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public DBConnection getConnection() {
        return connection;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Integer getPrimaryKey() {
        return roomId;
    }

    public static Collection<Room> getFilteredRooms(SearchParameters parameters,
                                                     DBConnection connection) throws SQLException {
        ResultSet rs = TableEntry.filter(parameters, connection, Room.TABLE_NAME);
        Collection<Room> entries = new ArrayList<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new Room(rs, connection));
        }
        return entries;
    }

    /**
     * Returns a collection of reservations of the room
     *
     * @return returns a collection of reservations of the room
     * @throws Exception SQL database error
     */
    public Collection<Reservation> getReservations() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();
        String fromDate;
        String toDate;

        if (Integer.parseInt(dtf1.format(now)) < 9) {
            fromDate = "'" + dtf.format(now.minusDays(1)) + " 09:00:00'";
            toDate = dtf.format(now) + " 09:00:00";
        } else {
            fromDate = "'" + dtf.format(now) + " 09:00:00'";
            toDate = dtf.format(now.plusDays(1)) + " 09:00:00";
        }

        ReservationSearchParameters rParams = new ReservationSearchParameters();
        rParams.addParameter(new FreeSearchParameter("room_id", " = ",
                Integer.toString(roomId)));
        rParams.addParameter(new FreeSearchParameter("start_date BETWEEN" + fromDate, " AND ", toDate));
        return Reservation.getFilteredReservations(rParams, getConnection());
    }

    public boolean isOccupied() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        ReservationSearchParameters parameters = new ReservationSearchParameters();
        parameters.addRoomSpecificDateOverlapParameter(getRoomId(), dtf.format(now));
        Collection<Reservation> reservations = Reservation.getFilteredReservations(parameters, getConnection());

        return reservations.size() != 0;
    }

    public RoomComment getRoomComment() throws Exception {
        return RoomComment.getRoomComment(getRoomId(), getConnection());
    }

    public void setRoomComment(String comment) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        RoomComment roomComment = RoomComment.getRoomComment(getRoomId(), getConnection());
        roomComment.setCommentDate(dtf.format(now));
        roomComment.setUserComment(comment);
        roomComment.updateEntry();
    }

    // Getter Methods

    public int getRoomId() {
        return roomId;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isConditioner() {
        return conditioner;
    }

    public boolean isProjector() {
        return projector;
    }

    public String getRenderData() {
        return renderData;
    }

    @Override
    public void insertEntry() throws Exception {
        // Insert the entry
        getConnection().executeUpdate(
            String.format(
                "INSERT %s VALUES (?, ?, ?, ?, ?, ?)",
                getTableName()),
            Arrays.asList(new String[] {
                Integer.toString(getRoomId()),
                Integer.toString(getRoomSize()),
                Integer.toString(getFloor()),
                isConditioner() ? "1" : "0",
                isProjector() ? "1" : "0",
                getRenderData() }));
    }

    @Override
    public void updateEntry() throws Exception {
        if (getPrimaryKey() == null)
            throw new Exception("No such entry exists");
        // Update the entry
        getConnection().executeUpdate(
            String.format(
                "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                getTableName(),
                ROOM_SIZE_NAME,
                FLOOR_NAME,
                CONDITIONER_NAME,
                PROJECTOR_NAME,
                RENDER_DATA_NAME,
                ROOM_ID_NAME),
            Arrays.asList(new String[] {
                Integer.toString(getRoomSize()),
                Integer.toString(getFloor()),
                isConditioner() ? "1" : "0",
                isProjector() ? "1" : "0",
                getRenderData(),
                Integer.toString(getRoomId()) }));
    }
}
