package ge.rrs;

import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
// JUnit
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

// Spring
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTest {
    // Database credentials
    private DBConnection connection;

    @BeforeEach
    public void initialize() throws SQLException {
        connection = new DBConnection(
            MockDatabaseCredentials.SERVER,
            MockDatabaseCredentials.USER,
            MockDatabaseCredentials.PASSWORD,
            MockDatabaseCredentials.DB_NAME);
    }

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
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter());
        Collection<RRSUser> users = RRSUser.getFilteredUsers(params, connection);
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
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("username", "=", "Human 1"));
        Collection<RRSUser> users = RRSUser.getFilteredUsers(params, connection);
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
    public void testUserMultiQuery() throws Exception {
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("username", "=", "Human 1"));
        params.addParameter(new FreeSearchParameter("email", "=", "human1@humans.org"));
        Collection<? extends TableEntry> users = RRSUser.getFilteredUsers(params, connection);
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


    // @Test
    public void testRegister() throws Exception {
        RRSUser user = new RRSUser(
            "testRegisterUsername", 
            (new BCryptPasswordEncoder()).encode("testRegisterPassword"),
            "registeredUsers@humans.org",
            connection);
        user.save();


        // Check if the user registered
        Collection<SearchParameter> params = new ArrayList<>();
        params.add(new FreeSearchParameter("username", "=", "testRegisterUsername"));
        Collection<? extends TableEntry> users = (new RRSUser(connection)).filter(params);
        assertEquals(1, users.size());

        for (TableEntry found_user : users) {
            assertEquals(user.getUsername(), ((RRSUser)found_user).getUsername());
            assertEquals(user.getPassword(), ((RRSUser)found_user).getPassword());
            assertEquals(user.getEmail(), ((RRSUser)found_user).getEmail());
        }
    }
}
