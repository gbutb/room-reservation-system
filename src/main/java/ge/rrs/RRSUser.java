// UserData.java
package ge.rrs;

import java.sql.ResultSet;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * A container for storing user data
 */
public class RRSUser extends org.springframework.security.core.userdetails.User implements TableEntry {
    private static final long serialVersionUID = 1L;

    // Email of the user
    private String email;

    /**
     * Initializes new RRS user.
     */
    public RRSUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = username;
    }

    /////////////
    // Getters //
    /////////////

    public String getEmail() {
        return this.email;
    }

    /////////////
    // Setters //
    /////////////

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public DBConnection getConnection() {
        // TODO: Implement this.
        return null;
    }

    @Override
    public Collection<TableEntry> fromResultSet(ResultSet rs) {
        // TODO: Implement this.
        return null;
    }

    @Override
    public String getTableName() {
        // TODO: Implement this.
        return null;
    }
}
