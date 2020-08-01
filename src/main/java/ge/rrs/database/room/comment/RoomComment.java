package ge.rrs.database.room.comment;

import ge.rrs.database.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RoomComment extends TableEntry {

    // The name of the table to which
    // this table entry corresponds to.
    private static final String TABLE_NAME = "room_comments";

    // Names of the table columns to which
    // the instance objects of this class correspond to.
    private static final String COMMENT_ID_NAME = "comment_id";
    private static final String COMMENT_DATE_NAME = "comment_date";
    private static final String USER_COMMENT_NAME = "user_comment";

    // Reference to DBConnection
    private final DBConnection connection;

    private int commentId;
    private String commentDate;
    private String userComment;

    public RoomComment(int commentId, String commentDate, String userComment, DBConnection connection) {
        this.commentId = commentId;
        this.commentDate = commentDate;
        this.userComment = userComment;

        this.connection = connection;
    }

    public RoomComment(ResultSet rSet, DBConnection connection) throws SQLException {
        commentId = rSet.getInt(COMMENT_ID_NAME);
        commentDate = rSet.getString(COMMENT_DATE_NAME);
        userComment = rSet.getString(USER_COMMENT_NAME);

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
        return commentId;
    }

    @Override
    public void insertEntry() throws Exception {
        // Insert the entry
        getConnection().executeUpdate(
                String.format(
                        "INSERT %s VALUES (?, ?, ?)",
                        getTableName()),
                Arrays.asList(new String[] {
                        Integer.toString(getCommentId()),
                        getCommentDate(),
                        getUserComment()}));
    }

    @Override
    public void updateEntry() throws Exception {
        if (getPrimaryKey() == null)
            throw new Exception("No such entry exists");
        // Update the entry
        getConnection().executeUpdate(
                String.format(
                        "UPDATE %s SET %s=?, %s=? WHERE %s=?",
                        getTableName(),
                        COMMENT_DATE_NAME,
                        USER_COMMENT_NAME,
                        getCommentId()),
                Arrays.asList(new String[] {
                        getCommentDate(),
                        getUserComment() }));
    }

    public static RoomComment getRoomComment(int commentId, DBConnection connection) throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("comment_id", " = ", "" + commentId));
        ResultSet rSet = TableEntry.filter(params, connection, RoomComment.TABLE_NAME);
        rSet.next();

        return new RoomComment(rSet, connection);
    }

    // Getter Methods

    public String getCommentDate() {
        return commentDate;
    }

    public String getUserComment() {
        return userComment;
    }

    public int getCommentId() {
        return commentId;
    }

    // Setter Methods

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
