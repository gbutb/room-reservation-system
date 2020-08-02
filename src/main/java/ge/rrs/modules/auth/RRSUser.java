// UserData.java
package ge.rrs.modules.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// spring
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;

/**
 * A container for storing user data
 */
public class RRSUser extends TableEntry implements UserDetails {
    private static final long serialVersionUID = 1L;
    // The name of the table to which
    // this table entry corresponds to.
    private static final String TABLE_NAME = "accounts";

    // Names of the table columns
    private static final String ACCOUNT_ID_NAME = "account_id";
    private static final String USERNAME_NAME = "username";
    private static final String PASSWORD_NAME = "encryptedPassword";
    private static final String EMAIL_NAME = "email";
    private static final String PHONENUMBER_NAME = "phoneNumber";

    // Reference to DBConnection
    private DBConnection connection;

    // User Details
    private Integer primaryKey;
    private String username;
    private String encryptedPassword;
    private String email;
    private String phoneNumber;

    // User status
    private boolean nonExpired;
    private boolean nonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    // Authorities
    private Collection<GrantedAuthority> authorities;

    /**
     * Initializes From result set.
     * @param rs result set corresponding to the entry.
     * @param connection A reference to DBConnection.
     */
    public RRSUser(ResultSet rs, DBConnection connection) throws SQLException {
        this(
            rs.getString(USERNAME_NAME),
            rs.getString(PASSWORD_NAME),
            rs.getString(EMAIL_NAME),
            rs.getString(PHONENUMBER_NAME),
            connection);
        primaryKey = rs.getInt(ACCOUNT_ID_NAME);
    }

    /**
     * Initializes new RRS User.
     * @param username username of the account
     * @param encryptedPassword BCrypt encrypted password.
     * @param email Email of the account.
     * @param connection a reference to the DBConnection.
     * @param phoneNumber a 9-digit phone number.
     */
    public RRSUser(String username, String encryptedPassword, String email, String phoneNumber, DBConnection connection) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        
        this.nonExpired = true;
        this.nonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        
        this.authorities = new ArrayList<>();

        this.connection = connection;
    }

    /**
     * Null user constructor.
     */
    public RRSUser(DBConnection connection) {
        this(null, null, null, null, connection);
        this.primaryKey = null;
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

    @Override
    public Integer getPrimaryKey() {
        if (primaryKey == null) {
            try {
                RRSUser user = getUser(
                    getUsername(), connection);
                primaryKey = user.getPrimaryKey();
            } catch (Exception e) {}
        }
        return primaryKey;
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
                rs, connection));
        }
        return entries;
    }

    /**
     * Filters users with given entris.
     * @param connection reference to DBConnection
     * @return a collection of users which match the entry.
     * @throws Exception should any error occur.
     */
    public static RRSUser getUser(
            String username, DBConnection connection) throws Exception {
        // Initialize Search parameters
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter(USERNAME_NAME, "=", username));

        // Query
        Collection<RRSUser> users = getFilteredUsers(params, connection);

        // Return the first user
        for (RRSUser user : users) {
            return user;
        }

        return null;
    }

    @Override
    public void insertEntry() throws Exception {
        if (getPrimaryKey() != null)
            throw new Exception("Entry already exists!");
        // Insert the entry
        getConnection().executeUpdate(
            String.format(
                "INSERT INTO %s VALUES (0, ?, ?, ?, ?)",
                getTableName()),
            Arrays.asList(new String[] {
                getUsername(),
                getPassword(),
                getEmail(),
                getPhoneNumber() }));
    }

    @Override
    public void updateEntry() throws Exception {
        if (getPrimaryKey() == null)
            throw new Exception("No such entry exists");
        // Update the entry
        getConnection().executeUpdate(
            String.format(
                "UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                getTableName(),
                USERNAME_NAME, PASSWORD_NAME, EMAIL_NAME, PHONENUMBER_NAME,
                ACCOUNT_ID_NAME),
            Arrays.asList(new String[] {
                getUsername(),
                getPassword(),
                getEmail(),
                getPhoneNumber(),
                getPrimaryKey().toString() }));
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

    public String getPhoneNumber() {
        return phoneNumber;
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

    /////////////
    // Setters //
    /////////////

    /**
     * Sets an BCrypt encrypted password
     * @param encryptedPassword encrypted password.
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * Sets password.
     * @param password unencrypted password.
     */
    public void setPassword(String password) {
        setEncryptedPassword((new BCryptPasswordEncoder()).encode(password));
    }

    /**
     * Sets phone number.
     * @param phoneNumber 9-digit phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    /**
     * @return true if the user is authenticated.
     */
    public static boolean isAuthenticated() {
        return (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() 
                instanceof AnonymousAuthenticationToken));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RRSUser)) return false;
        RRSUser otherUser = (RRSUser)other;
        if ((otherUser.getUsername() != this.getUsername()) ||
            (otherUser.getEmail() != this.getEmail())) return false;
        return true;
    }
}
