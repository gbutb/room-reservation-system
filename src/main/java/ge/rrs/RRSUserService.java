package ge.rrs;

// Java
import java.util.*;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Dummy user
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        RRSUser user = new RRSUser(
            username,  encoder.encode("password"),
            true, true, true, true, authorities);
        return user;
    }

    /**
     * Registers new user
     * @param user: New user object.
     */
    public void registerNewUser(RRSUser user) throws Exception {
        throw new Exception("Not implemented yet");
    }
}
