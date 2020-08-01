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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

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

        HashMap<Integer, ArrayList<String>> roomsRenderData = parseRenderData(allRooms);

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
        Collection<Room> filteredRooms = filterRooms(req, new DBConnection());

        HashMap<Integer, ArrayList<String>> roomsRenderData = parseRenderData(allRooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("allRooms", allRooms);
        mv.addObject("filteredRooms", filteredRooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    /**
     * Filters rooms according to given parameters from request and returns Collection
     * of rooms that satisfy given constraints.
     *
     * @param req        servlet request
     * @param connection database connection
     * @return collection of filtered rooms
     */
    private Collection<Room> filterRooms(HttpServletRequest req, DBConnection connection) throws Exception {
        String fromTime = req.getParameter("fromTime");
        String toTime = req.getParameter("toTime");

        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        roomSearchParameters.addFloorParameter(Integer.parseInt(req.getParameter("floor")));

        // filter rooms by time
        if (fromTime.length() != 0 && toTime.length() != 0) {
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            Time from = new Time(timeFormatter.parse(fromTime).getTime());
            Time to = new Time(timeFormatter.parse(toTime).getTime());
            LocalDate localDate = LocalDate.now();

            if (from.before(to)) {
                roomSearchParameters.addDateTimeRangeParameter(
                        localDate + " " + fromTime,
                        localDate + " " + toTime,
                        new DBConnection()
                );
            } else {
                LocalDate nextDate = localDate.plusDays(1);
                roomSearchParameters.addDateTimeRangeParameter(
                        localDate + " " + fromTime,
                        nextDate + " " + toTime,
                        new DBConnection()
                );
            }
        } else if (fromTime.equals(toTime)) {
            // TODO: Implement something...
        }

        // filter rooms by advanced options
        if (req.getParameter("hasAirConditioner") != null) {
            roomSearchParameters.addAirConditionerParameter();
        }

        if (req.getParameter("hasProjector") != null) {
            roomSearchParameters.addProjectorParameter();
        }

        String roomSize1 = req.getParameter("roomSize1");
        String roomSize2 = req.getParameter("roomSize2");
        String roomSize3 = req.getParameter("roomSize3");
        String roomSize4 = req.getParameter("roomSize4");
        if (roomSize1 != null || roomSize2 != null || roomSize3 != null || roomSize4 != null) {
            roomSearchParameters.addRoomSizeParameter(
                    roomSize1 != null ? 1 : 0,
                    roomSize2 != null ? 2 : 0,
                    roomSize3 != null ? 3 : 0,
                    roomSize4 != null ? 4 : 0
            );
        }

        return Room.getFilteredRooms(roomSearchParameters, connection);
    }

    /**
     * Parses render data string to individual components and stores in HashMap
     * where key is the room ID and the value ArrayList of individual components.
     * Example:
     * "10 25 12 13" -> "10" "25" "12" "13"
     *
     * @param rooms collection of Room objects
     * @return roomRenderData HashMap
     */
    private HashMap<Integer, ArrayList<String>> parseRenderData(Collection<Room> rooms) {
        HashMap<Integer, ArrayList<String>> roomsRenderData = new HashMap<>();

        for (Room room : rooms) {
            int roomId = room.getRoomId();
            String renderData = room.getRenderData();

            StringTokenizer stringTokenizer = new StringTokenizer(renderData, " ");
            ArrayList<String> parsedData = new ArrayList<>();
            while (stringTokenizer.hasMoreElements()) {
                String currElement = stringTokenizer.nextToken();
                parsedData.add(currElement);
            }

            roomsRenderData.put(roomId, parsedData);
        }

        return roomsRenderData;
    }
}
