package ge.rrs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView renderDashboard() throws IOException {
        ModelAndView mv = new ModelAndView();
        BufferedReader rd = new BufferedReader(new FileReader("src/main/resources/floor-2-rooms.txt"));
        HashMap<String, ArrayList<String>> rooms = new HashMap<>();

        while (true) {
            String line = rd.readLine();

            if (line == null) break;

            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

            String roomID = stringTokenizer.nextToken();
            rooms.put(roomID, new ArrayList<String>());

            while (stringTokenizer.hasMoreElements()) {
                String currElement = stringTokenizer.nextToken();
                rooms.get(roomID).add(currElement);
            }
        }

        rd.close();

        System.out.println(rooms);

        mv.setViewName("homepage");
        mv.addObject("rooms", rooms);

        return mv;
    }
}
