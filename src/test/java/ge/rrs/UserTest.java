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
    public void testAllQuery() throws Exception {
        DBConnection connection = new DBConnection();
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter());
        RRSUser nullUser = new RRSUser(connection);
        Collection<? extends TableEntry> users = nullUser.filter(params);
        assertEquals(2, users.size());

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

        assertEquals(0, usernames.size());
        assertEquals(0, emails.size());
        assertEquals(2, users.size());
    }

    @Test
    public void testUserQuery() throws Exception {
        DBConnection connection = new DBConnection();
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter("username", "=", "Human 1"));
        RRSUser nullUser = new RRSUser(connection);
        Collection<? extends TableEntry> users = nullUser.filter(params);
        assertEquals(1, users.size());

        Set<String> usernames = new HashSet<>();
        usernames.add("Human 1");

        Set<String> emails = new HashSet<>();
        emails.add("human1@humans.org");

        for (TableEntry user : users) {
            usernames.remove(
                ((RRSUser)user).getUsername());

            emails.remove(
                ((RRSUser)user).getEmail());
        }

        assertEquals(0, usernames.size());
        assertEquals(0, emails.size());
        assertEquals(1, users.size());
    }

    @Test
    public void testUserMultiQuery() throws SQLException {
        DBConnection connection = new DBConnection();
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter("username", "=", "Human 1"));
        params.add(new FreeSearchParameter("email", "=", "human1@humans.org"));
        RRSUser nullUser = new RRSUser(connection);
        Collection<? extends TableEntry> users = nullUser.filter(params);
        assertEquals(1, users.size());

        Set<String> usernames = new HashSet<>();
        usernames.add("Human 1");

        Set<String> emails = new HashSet<>();
        emails.add("human1@humans.org");

        for (TableEntry user : users) {
            usernames.remove(
                ((RRSUser)user).getUsername());

            emails.remove(
                ((RRSUser)user).getEmail());
        }

        assertEquals(0, usernames.size());
        assertEquals(0, emails.size());
        assertEquals(1, users.size());
    }
}