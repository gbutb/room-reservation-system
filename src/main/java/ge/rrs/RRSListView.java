package ge.rrs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Initial implementation of homepage list view controller
 */
@Controller
public class RRSListView {

    @RequestMapping("/homepage-lv")
    public ModelAndView renderDashboard(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        String isFiltered = req.getParameter("filter");

        if (isFiltered != null) {
            // TODO: implement filterRooms()...
        }

        mv.setViewName("/homepage-list-view");

        return mv;
    }
}
