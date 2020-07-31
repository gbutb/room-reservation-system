package ge.rrs.modules.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ge.rrs.modules.auth.RRSUser;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RRSSettingsController {
    @RequestMapping("/settings")
    public ModelAndView settingsView(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        if (req.getParameter("afterSave") != null) {
            RRSUser user = RRSUser.getCurrentUser();
            user.setPassword(req.getParameter("password"));
            user.setPhoneNumber(req.getParameter("number"));
            try{
                user.updateEntry();
            }catch(Exception e) {

            }
            mv.setViewName("/homepage/homepage-graphical-view");
        } else {
            mv.setViewName("/settings/settings");
        }
        return mv;
    }
}
