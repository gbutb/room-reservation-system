// RRSAuthConfig.java
package ge.rrs;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class RRSAuthController {
    private final RRSUserService userService;

    /**
     * Initializes Auth controller.
     */
    RRSAuthController() {
        userService = new RRSUserService();
    }

    @GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/register")
	String register() {
		return "registration";
	}

	@PostMapping("/register")
	String register(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "email", required = true) String email) throws Exception {
        userService.registerNewUser(
            new RRSUser(
                username, userService.getEncoder().encode(password),
                email, new DBConnection()));
		return "redirect:/login";
	}    
}
