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
import java.io.IOException;
import java.sql.SQLException;
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
    public ModelAndView renderFilteredGraphicalView(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();
        String floorParam = req.getParameter("floor");
        String isFiltered = req.getParameter("isFiltered");

//        if (isFiltered != null) {
//            filterRooms(req);
//        }

        RoomSearchParameters emptyParameters = new RoomSearchParameters();
        emptyParameters.addFloorParameter(Integer.parseInt(floorParam));
        Collection<Room> rooms = Room.getFilteredRooms(emptyParameters, new DBConnection());
        HashMap<Integer, ArrayList<String>> roomsRenderData = parseRenderData(rooms);

        mv.setViewName("/homepage/homepage-graphical-view");
        mv.addObject("rooms", rooms);
        mv.addObject("renderData", roomsRenderData);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

//    private void filterRooms(HttpServletRequest req) {
//        String fromTime = req.getParameter("fromTime");
//        String toTime = req.getParameter("toTime");
//
//        String hasProjector = req.getParameter("hasProjector");
//        String hasAirConditioner = req.getParameter("hasAirConditioner");
//
//        String roomSize1 = req.getParameter("roomSize1");
//        String roomSize2 = req.getParameter("roomSize2");
//        String roomSize3 = req.getParameter("roomSize3");
//        String roomSize4 = req.getParameter("roomSize4");
//
//        if (fromTime.length() != 0 && toTime.length() != 0) {
//            // TODO: Set time range attribute to filter parameter
//        }
//
//        addParameter(hasAirConditioner, 1);
//    }
//
//    private Collection<Room> addParameter(String parameter, int method, DBConnection dbConnection) throws Exception {
//        RoomSearchParameters rsp = new RoomSearchParameters();
//
//        if (parameter != null) {
//            if (parameter.equals("on")) {
//                addFilterParameter(rsp, method);
//            }
//        }
//
//        return Room.getFilteredRooms(rsp, dbConnection);
//    }
//
//    private void addFilterParameter(RoomSearchParameters rsp, int method) throws Exception {
//        switch (method) {
//            case 1:
//                rsp.addAirConditionerParameter();
//                break;
//            case 2:
//                rsp.addProjectorParameter();
//                break;
//            case 3:
//                rsp.addRoomSizeParameter(1);
//                break;
//            case 4:
//                rsp.addRoomSizeParameter(2);
//                break;
//            case 5:
//                rsp.addRoomSizeParameter(3);
//                break;
//            case 6:
//                rsp.addRoomSizeParameter(4);
//        }
//    }

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
