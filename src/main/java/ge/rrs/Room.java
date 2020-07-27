package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Collection;

public class Room extends TableEntry {

    private static final String TABLE_NAME = "rooms";

    // Reference to DBConnection
    private DBConnection connection;

    // Room Details
    private int roomId;
    private int roomSize;
    private int floor;
    private int commentId;
    private boolean conditioner;
    private boolean projector;

    Room(int roomId) {
        // TODO: implement this;
    }

    Room(int roomId, int roomSize, int floor, int commentId,
         boolean conditioner, boolean projector, DBConnection connection) {
        this.roomId =  roomId;
        this.roomSize = roomSize;
        this.floor = floor;
        this.commentId = commentId;
        this.conditioner = conditioner;
        this.projector = projector;

        this.connection = connection;
    }

    /**
     * Null Constructor.
     * @param connection A reference to DB connection.
     */
    Room(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public DBConnection getConnection() { return connection; }

    @Override
    public Collection<? extends TableEntry> fromResultSet(ResultSet rs) throws SQLException {
        Collection<Room> entries = new ArrayDeque<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new Room(
                rs.getInt("room_id"),
                rs.getInt("room_size"),
                rs.getInt("floor"),
                rs.getInt("comment_id"),
                rs.getBoolean("conditioner"),
                rs.getBoolean("projector"),
                getConnection()
            ));
        }
        return entries;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void save() throws Exception {

    }

//    public Collection getReservations() {
//
//    }

    public boolean isOccupied() {
        return true;
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

    public int getCommentId() {
        return commentId;
    }

    public boolean isConditioner() {
        return conditioner;
    }

    public boolean isProjector() {
        return projector;
    }
}
