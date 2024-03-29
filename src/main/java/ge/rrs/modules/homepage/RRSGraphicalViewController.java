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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// ge.rrs

/**
 * Implementation of homepage graphical view controller
 */
@Controller
public class RRSGraphicalViewController {
    /**
     * Redirects to homepage.
     */
    @GetMapping("/")
    public String indexHomepage() {
        return "redirect:/homepage-gv";
    }

    @GetMapping("/homepage-gv")
    public ModelAndView renderGraphicalView(HttpServletRequest req, HttpServletResponse resp) throws Exception {        
        // Check if input request is valid
        if (!RRSHomepageService.floorIsValid(req))
            return new ModelAndView("redirect:/homepage-gv?floor=1");

        ModelAndView mv = new ModelAndView();

        // filters room according to 'floor' parameter only
        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));
        Collection<Room> allRooms = Room.getFilteredRooms(roomSearchParameters, DBConnection.getContextConnection());

        // extracts RoomSearchParameters from HttpSession
        HttpSession session = req.getSession();
        if (session.getAttribute("filterParams") != null) {
            roomSearchParameters = (RoomSearchParameters) session.getAttribute("filterParams");
        }

        // makes copy of RoomSearchParameters to add 'floor' parameter
        // without changing main object in HttpSession
        roomSearchParameters = new RoomSearchParameters(roomSearchParameters.getParametersStatement(),
                roomSearchParameters.getArguments());
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));

        Collection<Room> filteredRooms = Room.getFilteredRooms(roomSearchParameters, DBConnection.getContextConnection());
        HashMap<Integer, ArrayList<String>> roomsRenderData = RRSHomepageService.parseRenderData(allRooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("allRooms", allRooms);
        mv.addObject("filteredRooms", filteredRooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    @PostMapping("/homepage-gv")
    public ModelAndView renderFilteredGraphicalView(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();

        if (!RRSHomepageService.floorIsValid(req)) resp.sendRedirect("/homepage-gv?floor=1");

        // filters room according to 'floor' parameter only
        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));
        Collection<Room> allRooms = Room.getFilteredRooms(roomSearchParameters, DBConnection.getContextConnection());

        // generates RoomSearchParameters and stores them in HttpSession
        roomSearchParameters = RRSHomepageService.buildParameters(req);

        // makes copy of RoomSearchParameters to add 'floor' parameter
        // without changing main object in HttpSession
        roomSearchParameters = new RoomSearchParameters(roomSearchParameters.getParametersStatement(),
                roomSearchParameters.getArguments());
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));

        Collection<Room> filteredRooms = Room.getFilteredRooms(roomSearchParameters, DBConnection.getContextConnection());
        HashMap<Integer, ArrayList<String>> roomsRenderData = RRSHomepageService.parseRenderData(allRooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("allRooms", allRooms);
        mv.addObject("filteredRooms", filteredRooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }
}
