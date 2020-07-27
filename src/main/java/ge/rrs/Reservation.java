package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Reservation extends TableEntry {
    private static final String TABLE_NAME = "";

    private int reservationId;
    private int roomId;
    private String startDate;
    private String endDate;
    private boolean doRepeat;
    private int accountId;
    private DBConnection connection;

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
    public Collection<? extends TableEntry> fromResultSet(ResultSet rs) throws SQLException {
        Collection<Reservation> entries = new ArrayList<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new Reservation(
                    rs.getInt("reservation_id"),
                    rs.getInt("room_id"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getBoolean("do_repeat"),
                    rs.getInt("account_id"),
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
}
