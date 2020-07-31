// RRSGraphicalView.java
package ge.rrs.modules.homepage;

import ge.rrs.database.DBConnection;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;
import ge.rrs.modules.auth.RRSUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

// ge.rrs

/**
 * Initial implementation of homepage graphical view controller
 */
@Controller
public class RRSGraphicalView {

    @RequestMapping("/homepage-gv")
    public ModelAndView renderGraphicalView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();
        String floorParam = req.getParameter("floor");
        String isFiltered = req.getParameter("isFiltered");

        RoomSearchParameters params = new RoomSearchParameters();
//        if (isFiltered != null) {
//             filterRooms(params, req);
//        }

        params.addFloorParameter(Integer.parseInt(floorParam));
        Collection<Room> rooms = Room.getFilteredRooms(params, new DBConnection());
        HashMap<Integer, ArrayList<String>> roomsRenderData = parseRenderData(rooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("rooms", rooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    private void filterRooms(RoomSearchParameters params, HttpServletRequest req) throws Exception {
        String fromTime = req.getParameter("fromTime");
        String toTime = req.getParameter("toTime");

//        if (fromTime.length() != 0 && toTime.length() != 0) {
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate from = LocalDate.now();
//            LocalDate to = from.plusDays(1);
//
//            params.addDateTimeRangeParameter(
//                    dtf.format(now) + " " + fromTime,
//                    dtf.format(now) + " " + toTime,
//                    new DBConnection()
//            );
//        }

        ArrayList<String> requestParameters = new ArrayList<String>() {
            {
                add(req.getParameter("hasProjector"));
                add(req.getParameter("hasAirConditioner"));
                add(req.getParameter("roomSize1"));
                add(req.getParameter("roomSize2"));
                add(req.getParameter("roomSize3"));
                add(req.getParameter("roomSize4"));
            }
        };

        System.out.println(requestParameters);

        for (int i = 0; i < requestParameters.size(); i++) {
            addParameter(params, requestParameters.get(i), i);
        }
    }

    private void addParameter(RoomSearchParameters params, String parameter, int method) throws Exception {
        if (parameter != null) {
            if (parameter.equals("on")) {
                addFilterParameter(params, method);
            }
        }
    }

    private void addFilterParameter(RoomSearchParameters params, int method) throws Exception {
        switch (method) {
            case 0:
                params.addAirConditionerParameter();
                break;
            case 1:
                params.addProjectorParameter();
                break;
            case 2:
                params.addRoomSizeParameter(1);
                break;
            case 3:
                params.addRoomSizeParameter(2);
                break;
            case 4:
                params.addRoomSizeParameter(3);
                break;
            case 5:
                params.addRoomSizeParameter(4);
        }
    }

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
