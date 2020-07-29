// RRSSecurityConfig.java
package ge.rrs.modules.auth;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class RRSSecurityConfig extends WebSecurityConfigurerAdapter {
	// User service, for handling authentication
	private RRSUserService userService;

	/**
	 * Initializes Security configuration.
	 */
	public RRSSecurityConfig() {
		userService = new RRSUserService();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
		// Set up password encoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/register").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Initialize service
		auth.userDetailsService(
			userService).passwordEncoder(
				passwordEncoder());
	}
}
