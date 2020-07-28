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
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        String hasProjector = req.getParameter("hasProjector");
        String hasAirConditioner = req.getParameter("hasAirConditioner");

        String roomSize1 = req.getParameter("roomSize1");
        String roomSize2 = req.getParameter("roomSize2");
        String roomSize3 = req.getParameter("roomSize3");
        String roomSize4 = req.getParameter("roomSize4");

        System.out.println(startTime);
        System.out.println(endTime);

        System.out.println(hasProjector);
        System.out.println(hasAirConditioner);

        System.out.println(roomSize1);
        System.out.println(roomSize2);
        System.out.println(roomSize3);
        System.out.println(roomSize4);
    }

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
