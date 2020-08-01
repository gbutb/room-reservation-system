package ge.rrs.modules.homepage;

import ge.rrs.database.DBConnection;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Initial implementation of homepage service class used by
 * RRSGraphicalViewController and RRSListViewController
 */
public class RRSHomepageService {

    /**
     * Creates RoomSearchParameters object according to parameters taken from
     * given HttpServletRequest and stores it inside HttpSession. Parameters doesn't
     * include floor data, it's added separately in controllers.
     *
     * @param req servlet request
     * @return generated RoomSearchParameters
     */
    public static RoomSearchParameters buildParameters(HttpServletRequest req) throws Exception {
        String fromTime = req.getParameter("fromTime");
        String toTime = req.getParameter("toTime");

        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();

        // filter rooms by time
        if (fromTime.length() != 0 && toTime.length() != 0) {
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            Time from = new Time(timeFormatter.parse(fromTime).getTime());
            Time to = new Time(timeFormatter.parse(toTime).getTime());
            LocalDate localDate = LocalDate.now();

            if (from.before(to) || from.equals(to)) {
                roomSearchParameters.addDateTimeRangeParameter(
                        localDate + " " + fromTime,
                        localDate + " " + toTime,
                        new DBConnection()
                );
            } else if (from.after(to)) {
                LocalDate nextDate = localDate.plusDays(1);
                roomSearchParameters.addDateTimeRangeParameter(
                        localDate + " " + fromTime,
                        nextDate + " " + toTime,
                        new DBConnection()
                );
            }
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

        // roomSearchParameters is stored without 'floor' value
        req.getSession().setAttribute("filterParams", roomSearchParameters);

        return roomSearchParameters;
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
    public static HashMap<Integer, ArrayList<String>> parseRenderData(Collection<Room> rooms) {
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
