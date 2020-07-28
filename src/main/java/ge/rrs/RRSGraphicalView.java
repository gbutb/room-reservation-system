package ge.rrs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Initial implementation of homepage graphical view controller
 */
@Controller
public class RRSGraphicalView {

    @RequestMapping("/homepage-gv")
    public ModelAndView renderDashboard(HttpServletRequest req) throws IOException {
        ModelAndView mv = new ModelAndView();
        String floorParam = req.getParameter("floor");
        String isFiltered = req.getParameter("filter");

        if (isFiltered != null) {
            filterRooms(req);
        }

        mv.setViewName("/homepage-graphical-view");
        mv.addObject("rooms", fetchData(floorParam));

        return mv;
    }

    private void filterRooms(HttpServletRequest req) {
        String fromTime = req.getParameter("fromTime");
        String toTime = req.getParameter("toTime");

        String hasProjector = req.getParameter("hasProjector");
        String hasAirConditioner = req.getParameter("hasAirConditioner");

        String roomSize1 = req.getParameter("roomSize1");
        String roomSize2 = req.getParameter("roomSize2");
        String roomSize3 = req.getParameter("roomSize3");
        String roomSize4 = req.getParameter("roomSize4");

        if (fromTime.length() != 0 && toTime.length() != 0) {
            // TODO: Set time range attribute to filter parameter
        }

        System.out.println(fromTime);
        System.out.println(toTime);

        System.out.println(hasAirConditioner);
        System.out.println(hasProjector);

        System.out.println(roomSize1);
        System.out.println(roomSize2);
        System.out.println(roomSize3);
        System.out.println(roomSize4);

//        addParameter(hasAirConditioner, 1);
    }

//    private ArrayList<Room> addParameter(String parameter, int method) {
//        RoomSearchParameters rsp = new RoomSearchParameters();
//
//        if (parameter != null) {
//            switch (parameter) {
//                case "on":
//                    addFilterParameter(rsp, method);
//            }
//        }
//
//        return Room.getFilteredRooms(rsp, connection);
//    }
//
//    private void addFilterParameter(RoomSearchParameters rsp, int method) {
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

    public HashMap<String, ArrayList<String>> fetchData(String floorParam) throws IOException {
        BufferedReader rd = new BufferedReader(new FileReader("src/main/resources/floor-" + floorParam + "-rooms.txt"));
        HashMap<String, ArrayList<String>> rooms = new HashMap<>();

        int roomId = 0;
        while (true) {
            String line = rd.readLine();
            if (line == null) break;

            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

            rooms.put(String.valueOf(roomId), new ArrayList<>());
            while (stringTokenizer.hasMoreElements()) {
                String currElement = stringTokenizer.nextToken();
                rooms.get(String.valueOf(roomId)).add(currElement);
            }
            roomId++;
        }

        rd.close();

        return rooms;
    }
}
