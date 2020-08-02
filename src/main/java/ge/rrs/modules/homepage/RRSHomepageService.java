package ge.rrs.modules.homepage;

import ge.rrs.database.DBConnection;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
            String[] fromTo = convertToDates(fromTime, toTime);

            System.out.println(fromTo[0]);
            System.out.println(fromTo[1]);

            roomSearchParameters.addDateTimeRangeParameter(fromTo[0], fromTo[1],
                    DBConnection.getContextConnection());
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
     * Reads two strings containing time in 24 hour format and generates
     * date using current system date.
     * Example 1: Consider that when you passed time parameters to this method
     * it was 2020-08-02
     * <p>
     * 18:00 - 19:00 -> 2020-08-02 18:00 - 2020-08-02 19:00
     * 20:00 - 02:00 -> 2020-08-02 20:00 - 2020-08-03 02:00
     * 01:00 - 05:00 -> 2020-08-03 01:00 - 2020-08-03 05:00
     * <p>
     * Example 2: Consider that when you passed time parameters to this method
     * it was 2020-08-03 but the "same" day
     * <p>
     * 01:00 - 05:00 -> 2020-08-03 01:00 - 2020-08-03 05:00
     *
     * @param fromTime first input
     * @param toTime   second input
     * @return array containing to dates
     */
    public static String[] convertToDates(String fromTime, String toTime) throws ParseException {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        Calendar now = new GregorianCalendar();
        Calendar from = new GregorianCalendar();
        Calendar to = new GregorianCalendar();
        now.setTime(new Date());
        from.setTime(timeFormatter.parse(fromTime));
        to.setTime(timeFormatter.parse(toTime));

        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int fromHour = from.get(Calendar.HOUR_OF_DAY);
        int toHour = to.get(Calendar.HOUR_OF_DAY);

        LocalDate localDate = LocalDate.now();
        LocalDate nextDate = localDate.plusDays(1);

        String[] result = new String[2];
        if (fromHour <= toHour) {
            if (fromHour < 9 && nowHour >= 9) {
                result[0] = nextDate + " " + timeFormatter.format(from.getTime());
                result[1] = nextDate + " " + timeFormatter.format(to.getTime());
            } else {
                result[0] = localDate + " " + timeFormatter.format(from.getTime());
                result[1] = localDate + " " + timeFormatter.format(to.getTime());
            }
        } else {
            result[0] = localDate + " " + timeFormatter.format(from.getTime());
            result[1] = nextDate + " " + timeFormatter.format(to.getTime());
        }

        return result;
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

    /**
     * Checks if given floor parameter is valid and returns
     * true or false accordingly.
     *
     * @param req HttpServletRequest
     * @return true or false
     */
    public static boolean floorIsValid(HttpServletRequest req) {
        if (req.getParameter("floor") == null)
            return false;

        int floor = Integer.parseInt(req.getParameter("floor"));

        return floor >= 1 && floor <= 4;
    }
}
