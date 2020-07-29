package ge.rrs;

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
            Collection<SearchParameter> params = new ArrayList<>();
            params.add(new FreeSearchParameter("username", "=", username));
            Collection<? extends TableEntry> users = (new RRSUser(new DBConnection())).filter(params);
            if (users.size() != 1)
                throw new UsernameNotFoundException(
                    String.format("There's no user with '%s' username", username));
            Object[] usersArray = users.toArray();
            return (RRSUser)usersArray[0];
        }
        catch (SQLException e) {
            throw new UsernameNotFoundException("Unable to access database");
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
