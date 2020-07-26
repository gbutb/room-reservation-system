package ge.rrs;

import java.sql.SQLException;
import java.util.*;

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
        RRSUser user = new RRSUser("username", "password", "username", null);

        assertEquals(user.getUsername(), "username");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getEmail(), user.getUsername());
        assertEquals(user.getAuthorities().size(), 0);
    }

    @Test
    public void testDatabase() throws Exception {
        DBConnection connection = new DBConnection();
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter());
        RRSUser nullUser = new RRSUser(connection);
        Collection<? extends TableEntry> users = nullUser.filter(params);
        assertEquals(users.size(), 2);

        Set<String> usernames = new HashSet<>();
        usernames.add("Human");
        usernames.add("Human 1");

        Set<String> emails = new HashSet<>();
        emails.add("human@humans.org");
        emails.add("human1@humans.org");

        for (TableEntry user : users) {
            usernames.remove(
                ((RRSUser)user).getUsername());

            emails.remove(
                ((RRSUser)user).getEmail());
        }

        assertEquals(usernames.size(), 0);
        assertEquals(emails.size(), 0);
    }
}
