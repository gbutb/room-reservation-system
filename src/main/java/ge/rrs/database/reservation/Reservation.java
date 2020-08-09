// Reservation.java
package ge.rrs.database.reservation;

import ge.rrs.database.DBConnection;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Integer reservationId;
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

    public Reservation(
            int roomId,
            String startDate,
            String endDate,
            boolean doRepeat,
            int accountId,
            DBConnection connection) {
        this(0,
                roomId,
                startDate,
                endDate,
                doRepeat,
                accountId,
                connection);
        this.reservationId = null;
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
        return reservationId;
    }

    public static Collection<Reservation> getFilteredReservations(SearchParameters parameters,
                                                                  DBConnection connection) throws SQLException {
        ResultSet rs = TableEntry.filter(parameters, connection, Reservation.TABLE_NAME);
        Collection<Reservation> entries = new ArrayList<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new Reservation(rs, connection));
        }
        return entries;
    }

    public static Collection<Reservation> compareAndFilterReservations(Collection<Reservation> reservations,
                                                                       String timeFrom, String timeTo) {
        double from = hoursToDouble(timeFrom, false);
        double to = hoursToDouble(timeTo, true);

        Collection<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            double reservationStart = hoursToDouble(reservation.startDate, false);
            double reservationEnd = hoursToDouble(reservation.endDate, true);
            System.out.println(to + " > " + reservationStart);
            System.out.println("AND " + from + " < " + reservationEnd);

            if (to > reservationStart && from < reservationEnd) result.add(reservation);
        }

        return result;
    }

    private static double hoursToDouble(String time, boolean inclusive) {
        // TODO inconsistent time formats
        DateTimeFormatter dtfDate = time.length() == 16 ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TemporalAccessor temp = dtfDate.parse(time);

        DateTimeFormatter dtfHour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter dtfMinute = DateTimeFormatter.ofPattern("mm");

        double result = Integer.parseInt(dtfHour.format(temp));
        result += Integer.parseInt(dtfMinute.format(temp)) / 60.0;
        if (inclusive && result <= 9) result += 24;
        if (!inclusive && result < 9) result += 24;

        return result;
    }

    @Override
    public void insertEntry() throws Exception {
        if (getPrimaryKey() != null)
            throw new Exception("Entry already exists!");

        getConnection().executeUpdate(
                String.format(
                        "INSERT INTO %s VALUES (0, ?, ?, ?, ?, ?)",
                        getTableName()),
                Arrays.asList(Integer.toString(getRoomId()),
                        getStartDate(),
                        getEndDate(),
                        isDoRepeat() ? "1" : "0",
                        Integer.toString(getAccountId())));
    }

    @Override
    public void updateEntry() throws Exception {
        if (getPrimaryKey() == null)
            throw new Exception("No such entry exists!");
        // Update the entry
        getConnection().executeUpdate(
                String.format(
                        "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                        getTableName(),
                        ROOM_ID_NAME,
                        START_DATE_NAME,
                        END_DATE_NAME,
                        DO_REPEAT_NAME,
                        ACCOUNT_ID_NAME,
                        RESERVATION_ID_NAME),
                Arrays.asList(Integer.toString(getRoomId()),
                        getStartDate(),
                        getEndDate(),
                        isDoRepeat() ? "1" : "0",
                        Integer.toString(getAccountId()),
                        getPrimaryKey().toString()));
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
