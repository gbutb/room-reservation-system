// UserData.java
package ge.rrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * A container for storing user data
 */
public class RRSUser extends TableEntry implements UserDetails {
    private static final long serialVersionUID = 1L;
    // The name of the table to which
    // this table entry corresponds to.
    private static final String TABLE_NAME = "accounts";

    // Reference to DBConnection
    private DBConnection connection;

    // User Details
    private String username;
    private String encryptedPassword;
    private String email;

    // User status
    private boolean nonExpired;
    private boolean nonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    // Meta
    private boolean isNull;

    // Authorities
    private Collection<GrantedAuthority> authorities;

    /**
     * Initializes new RRS User.
     * @param username username of the account
     * @param encryptedPassword BCrypt encrypted password.
     * @param email Email of the account.
     * @param connection a reference to the DBConnection.
     */
    public RRSUser(String username, String encryptedPassword, String email, DBConnection connection) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        
        this.nonExpired = true;
        this.nonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        
        this.authorities = new ArrayList<>();

        this.connection = connection;
        this.isNull = false;
    }

    /**
     * Null user constructor.
     */
    public RRSUser(DBConnection connection) {
        this(null, null, null, connection);
        this.isNull = true;
    }

    /**
     * @param connection A reference to DBConnection.
     */
    public void setDBConnection(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public DBConnection getConnection() {
        return this.connection;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /////////////////
    // Table Entry //
    /////////////////

    /**
     * Filters out the table of users
     * based on the provided parameters.
     * @param parameters A set of search parameters.
     * @param connection A reference to DBConnection
     * @return A collection of users.
     * @throws SQLException should any SQL error occur.
     */
    public static Collection<RRSUser> getFilteredUsers(
            SearchParameters parameters,
            DBConnection connection) throws SQLException {
        ResultSet rs = TableEntry.filter(parameters, connection, RRSUser.TABLE_NAME);
        Collection<RRSUser> entries = new ArrayList<>();
        while (rs.next()) {
            // Add new entry
            entries.add(new RRSUser(
                rs.getString("username"),
                rs.getString("encryptedPassword"),
                rs.getString("email"),
                connection));
        }
        return entries;
    }

    @Override
    public void save() throws Exception {
        // TODO: move this to DBConnection
        getConnection().executeUpdate(
            "INSERT INTO accounts VALUES (?, ?, ?, ?)",
            Arrays.asList(
                new String[] {
                    getFilteredUsers(new SearchParameters(), getConnection()).size() + "",
                    getUsername(),
                    getPassword(),
                    getEmail() }));
    }

    /////////////////
    // UserDetails //
    /////////////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    ///////////
    // Other //
    ///////////

    /**
     * @return: RRSUser which is currently sending
     *  the request.
     */
    public static RRSUser getCurrentUser() {
        Object user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return (RRSUser)user;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RRSUser)) return false;
        RRSUser otherUser = (RRSUser)other;
        if ((otherUser.getUsername() != this.getUsername()) ||
            (otherUser.getPassword() != this.getPassword())) return false;
        return true;
    }
}
