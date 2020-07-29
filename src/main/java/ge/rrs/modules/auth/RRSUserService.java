// RRSUserService.java
package ge.rrs.modules.auth;

import java.sql.SQLException;
// Java
import java.util.*;

import com.mysql.cj.xdevapi.Table;

// Spring
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ge.rrs.database.DBConnection;
import ge.rrs.database.FreeSearchParameter;
import ge.rrs.database.SearchParameters;
import ge.rrs.database.TableEntry;

import org.springframework.security.core.GrantedAuthority;

@Service
public class RRSUserService implements UserDetailsService {
    private final BCryptPasswordEncoder encoder;

    /**
     * Initializes RRS user service.
     */
    public RRSUserService() {
        encoder = new BCryptPasswordEncoder();
    }

    /**
     * @return Password encoder.
     */
    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: use singleton DBConnection
        try {
            SearchParameters params = new SearchParameters();
            params.addParameter(new FreeSearchParameter("username", "=", username));
            Collection<? extends TableEntry> users = RRSUser.getFilteredUsers(params, new DBConnection());
            if (users.size() != 1)
                throw new UsernameNotFoundException(
                    String.format("There's no user with '%s' username", username));
            Object[] usersArray = users.toArray();
            return (RRSUser)usersArray[0];
        }
        catch (SQLException e) {
            throw new UsernameNotFoundException("Unable to access database");
        } catch (Exception e) {
            throw new UsernameNotFoundException("Unable to find username");
        }
    }

    /**
     * Registers new user
     * @param user: New user object.
     */
    public void registerNewUser(RRSUser user) throws Exception {
        user.save();
    }
}
