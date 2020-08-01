package ge.rrs.modules.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ge.rrs.modules.auth.RRSUser;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RRSSettingsController {
    @GetMapping("/settings")
    public ModelAndView settingsView() {
        // Initialize result
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("/settings/settings");
        return modelView;
    }

    @PostMapping("/settings")
    public ModelAndView settingsView(HttpServletRequest req) {
        RRSUser user = RRSUser.getCurrentUser();
        if(!req.getParameter("password").isEmpty()) user.setPassword(req.getParameter("password"));
        if(!req.getParameter("number").isEmpty()) user.setPhoneNumber(req.getParameter("number"));
        try {
            user.updateEntry();
        } catch(Exception e) {
            
        }
        // Initialize redirect
        ModelAndView mv = new ModelAndView("redirect:/homepage-gv?floor=1");
        return mv;
    }
}
