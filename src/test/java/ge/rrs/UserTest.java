// UserTest.java
package ge.rrs;

import java.util.*;

// JUnit
import org.junit.jupiter.api.BeforeAll;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.Assert.*;

// Spring
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// ge.rrs
import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;
import ge.rrs.modules.auth.RRSUser;

@TestInstance(Lifecycle.PER_CLASS)
public class UserTest {
    // Database credentials
    private DBConnection connection;

    @BeforeAll
    public void initialize() throws Exception {
        connection = new DBConnection(
            MockDatabaseCredentials.SERVER,
            MockDatabaseCredentials.USER,
            MockDatabaseCredentials.PASSWORD,
            MockDatabaseCredentials.DB_NAME);
        connection.executeSQLFrom(MockDatabaseCredentials.SOURCE);
    }

    @AfterAll
    public void destroy() throws Exception {
        connection.executeSQLFrom(MockDatabaseCredentials.CLEAN);
        connection.closeConnection();
    }

    @Test
    public void testInitialization() {
        // Initialize User
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        RRSUser user = new RRSUser("username", "password", "username", "123456789", null);

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


    @Test
    public void testRegister() throws Exception {
        RRSUser user = new RRSUser(
            "testRegisterUsername", 
            (new BCryptPasswordEncoder()).encode("testRegisterPassword"),
            "registeredUsers@humans.org",
            "123456789",
            connection);
        user.insertEntry();

        // Check if the user registered
        SearchParameters params = new SearchParameters();
        params.addParameter(new FreeSearchParameter("username", "=", "testRegisterUsername"));
        Collection<? extends TableEntry> users = RRSUser.getFilteredUsers(params, connection);
        assertEquals(1, users.size());

        for (TableEntry found_user : users) {
            assertEquals(user.getUsername(), ((RRSUser)found_user).getUsername());
            assertEquals(user.getPassword(), ((RRSUser)found_user).getPassword());
            assertEquals(user.getEmail(), ((RRSUser)found_user).getEmail());
        }

        // Try to register the user again
        RRSUser re_user = new RRSUser(
            user.getUsername(),
            (new BCryptPasswordEncoder()).encode("testRegisterPassword"),
            "registeredUsers@humangs.org",
            "123456789",
            connection);
        boolean threwError = false;
        try {
            re_user.insertEntry();
        } catch (Exception e) {
            threwError = true;
        }
        assertTrue(threwError);
    }

    @Test
    public void testUpdateUser() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        RRSUser user = new RRSUser(
            "testUpdateUsername", 
            encoder.encode("testUpdatePassword"),
            "updatedUsers@humans.org",
            "123456789",
            connection);
        user.insertEntry();
        user.setEncryptedPassword(
            encoder.encode("newPassword"));
        user.updateEntry();

        RRSUser user_from_db = RRSUser.getUser("testUpdateUsername", connection);
        assertTrue(encoder.matches("newPassword", user_from_db.getPassword()));
    }
}
