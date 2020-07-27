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
 * For testing purposes only.
 */
@Controller
public class RenderTest {

    @RequestMapping("/homepage")
    public ModelAndView renderDashboard(HttpServletRequest req) throws IOException {
        String floorParam = req.getParameter("floor");

        ModelAndView mv = new ModelAndView();
        BufferedReader rd = new BufferedReader(new FileReader("src/main/resources/floor-" + floorParam + "-rooms.txt"));
        HashMap<String, ArrayList<String>> rooms = new HashMap<>();

        int roomId = 0;
        while (true) {
            String line = rd.readLine();
            if (line == null) break;

            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

            rooms.put(String.valueOf(roomId), new ArrayList<String>());
            while (stringTokenizer.hasMoreElements()) {
                String currElement = stringTokenizer.nextToken();
                rooms.get(String.valueOf(roomId)).add(currElement);
            }
            roomId++;
        }

        rd.close();

        mv.setViewName("homepage");
        mv.addObject("rooms", rooms);

        return mv;
    }
}
