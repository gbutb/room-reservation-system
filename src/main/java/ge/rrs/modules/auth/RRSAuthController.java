// RRSAuthConfig.java
package ge.rrs.modules.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

// ge.rrs
import ge.rrs.database.DBConnection;


@Controller
public class RRSAuthController {
    private final RRSUserService userService;

    /**
     * Initializes Auth controller.
     */
    RRSAuthController() {
        userService = new RRSUserService();
    }

    // TODO: remove this
    @GetMapping("/")
    public String home() {
        return "/index";
    }

    @GetMapping("/login")
	public String login() {
		return "/auth/login";
	}

	@GetMapping("/register")
	public ModelAndView register() {
        // Initialize result
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/auth/registration");
        modelView.addObject("failed", false);
		return modelView;
	}

	@PostMapping("/register")
	public ModelAndView register(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "email", required = true) String email) throws Exception {
        // Initialize result
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/auth/registration");
        System.out.println("Trying register");
        modelView.addObject("failed", false);
        try {
            userService.registerNewUser(
                new RRSUser(
                    username, userService.getEncoder().encode(password),
                    email, new DBConnection()));
        } catch (Exception e) {
            System.out.println(e.toString());
            modelView.addObject("failed", true);
        }
        return modelView;
	}    
}
