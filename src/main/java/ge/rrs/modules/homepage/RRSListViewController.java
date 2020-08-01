// RRSListView.java
package ge.rrs.modules.homepage;

import ge.rrs.database.DBConnection;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;
import ge.rrs.modules.auth.RRSUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Implementation of homepage list view controller
 */
@Controller
public class RRSListViewController {

    @GetMapping("/homepage-lv")
    public ModelAndView renderListView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();

        // extracts RoomSearchParameters from HttpSession
        HttpSession session = req.getSession();
        RoomSearchParameters roomSearchParameters = (RoomSearchParameters) session.getAttribute("filterParams");

        Collection<Room> rooms = Room.getFilteredRooms(roomSearchParameters, new DBConnection());

        mv.setViewName("/homepage/homepage-list-view");
        mv.addObject("rooms", rooms);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    @PostMapping("/homepage-lv")
    public ModelAndView renderFilteredListView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();

        // generates RoomSearchParameters and stores them in HttpSession
        RoomSearchParameters roomSearchParameters = RRSHomepageService.buildParameters(req);

        Collection<Room> filteredRooms = Room.getFilteredRooms(roomSearchParameters, new DBConnection());

        mv.setViewName("/homepage/homepage-list-view");
        mv.addObject("rooms", filteredRooms);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }
}
