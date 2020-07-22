package ge.rrs;

import java.util.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

// Spring
import org.springframework.security.core.GrantedAuthority;

public class UserTest {

    @Test
    public void testInitialization() {
        // Initialize User
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        RRSUser user = new RRSUser(
            "username", "password",
            true, true, true, true, authorities);

        assertEquals(user.getUsername(), "username");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getEmail(), user.getUsername());
        assertEquals(user.getAuthorities().size(), 0);
    }

    @Test
    public void testUser() {
        // Initialize User
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        RRSUser user = new RRSUser(
            "username", "password",
            true, true, true, true, authorities);

        user.setEmail("email@email.com");
        assertEquals(user.getEmail(), "email@email.com");
    }
}
