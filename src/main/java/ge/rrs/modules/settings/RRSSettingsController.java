package ge.rrs.modules.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RRSSettingsController {

    @RequestMapping("/settings")
    public ModelAndView settingsView() {
        ModelAndView mv = new ModelAndView();

        // TODO: User code here

        mv.setViewName("/settings/settings");
        return mv;
    }
}
