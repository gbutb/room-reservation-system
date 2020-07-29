package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Reservation extends TableEntry {

    // The name of the table to which
    // this table entry corresponds to.
    private static final String TABLE_NAME = "reservations";

    // Names of the table columns to which
    // the instance objects of this class correspond to.
    private static final String RESERVATION_ID_NAME = "reservation_id";
    private static final String ROOM_ID_NAME = "room_id";
    private static final String START_DATE_NAME = "start_date";
    private static final String END_DATE_NAME = "end_date";
    private static final String DO_REPEAT_NAME = "do_repeat";
    private static final String ACCOUNT_ID_NAME = "account_id";

    // Reference to DBConnection
    private final DBConnection connection;

    private int reservationId;
    private int roomId;
    private String startDate;
    private String endDate;
    private boolean doRepeat;
    private int accountId;

    /**
     * Initializes Reservation using given ResultSet's current row
     *
     * @param rSet       user given ResultSet of a database query of filtered reservations
     * @param connection DBConnection
     * @throws SQLException SQL database error
     */
    public Reservation(ResultSet rSet, DBConnection connection) throws SQLException {
        reservationId = rSet.getInt(RESERVATION_ID_NAME);
        roomId = rSet.getInt(ROOM_ID_NAME);
        startDate = rSet.getString(START_DATE_NAME);
        endDate = rSet.getString(END_DATE_NAME);
        doRepeat = rSet.getBoolean(DO_REPEAT_NAME);
        accountId = rSet.getInt(ACCOUNT_ID_NAME);
        this.connection = connection;
    }

    public Reservation(int reservationId, int roomId, String startDate, String endDate,
                       boolean doRepeat, int accountId, DBConnection connection) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.doRepeat = doRepeat;
        this.accountId = accountId;
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

    public static Collection<Reservation> getFilteredReservations(ReservationSearchParameters parameters,
                                                                  DBConnection connection) throws SQLException {
        ResultSet rs = TableEntry.filter(parameters, connection, Reservation.TABLE_NAME);
        Collection<Reservation> entries = new ArrayList<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new Reservation(rs, connection));
        }
        return entries;
    }

    @Override
    public void save() throws Exception {
        getConnection().executeUpdate(
                "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)",
                new ArrayList<String>() {
                    {
                        add("" + getReservationId());
                        add("" + getRoomId());
                        add(getStartDate());
                        add(getEndDate());
                        add(isDoRepeat() ? "true" : "false");
                        add("" + getAccountId());
                    }
                }
        );
    }

    // Getter Methods

    public int getReservationId() {
        return reservationId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isDoRepeat() {
        return doRepeat;
    }

    public int getAccountId() {
        return accountId;
    }

    // Setter Methods

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDoRepeat(boolean doRepeat) {
        this.doRepeat = doRepeat;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
