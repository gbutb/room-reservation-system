// RRSGraphicalView.java
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// ge.rrs

/**
 * Implementation of homepage graphical view controller
 */
@Controller
public class RRSGraphicalViewController {

    @GetMapping("/homepage-gv")
    public ModelAndView renderGraphicalView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();

        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));

        Collection<Room> allRooms = Room.getFilteredRooms(roomSearchParameters, new DBConnection());

        HashMap<Integer, ArrayList<String>> roomsRenderData = RRSHomepageService.parseRenderData(allRooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("allRooms", allRooms);
        mv.addObject("filteredRooms", allRooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    @PostMapping("/homepage-gv")
    public ModelAndView renderFilteredGraphicalView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();

        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));

        Collection<Room> allRooms = Room.getFilteredRooms(roomSearchParameters, new DBConnection());
        Collection<Room> filteredRooms = RRSHomepageService.filterRooms(req, new DBConnection());

        HashMap<Integer, ArrayList<String>> roomsRenderData = RRSHomepageService.parseRenderData(allRooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("allRooms", allRooms);
        mv.addObject("filteredRooms", filteredRooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }
}
