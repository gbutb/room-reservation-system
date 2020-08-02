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

    @GetMapping("/login")
	public String login() {
        if (RRSUser.isAuthenticated())
            return "redirect:/";
		return "/auth/login";
	}

	@GetMapping("/register")
	public ModelAndView register() {
        // Initialize result
        ModelAndView modelView;
        if (RRSUser.isAuthenticated()) {
            modelView = new ModelAndView("redirect:/");
        } else {
            modelView = new ModelAndView();
            modelView.setViewName("/auth/registration");
            modelView.addObject("failed", false);
        }

		return modelView;
	}

	@PostMapping("/register")
	public ModelAndView register(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber) throws Exception {

        // Initialize result
        ModelAndView modelView;

        // Ignore if the user is authenticated
        if (RRSUser.isAuthenticated()) {
            modelView = new ModelAndView("redirect:/");
            return modelView;
        }

        // Try to register user
        try {
            userService.registerNewUser(
                new RRSUser(
                    username, userService.getEncoder().encode(password),
                    email, phoneNumber, DBConnection.getContextConnection()));
            modelView = new ModelAndView("redirect:/login");
        } catch (Exception e) {
            // System.out.println(e.toString());
            modelView = new ModelAndView();
            modelView.setViewName("/auth/registration");
            modelView.addObject("failed", true);
        }
        return modelView;
	}    
}
