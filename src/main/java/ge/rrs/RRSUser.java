// UserData.java
package ge.rrs;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * A container for storing user data
 */
public class RRSUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    // Email of the user
    private String email;

    /**
     * Initializes new RRS user.
     */
    public RRSUser(
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
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
}
