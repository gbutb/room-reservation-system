package ge.rrs.modules.room;

import ge.rrs.database.DBConnection;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;
import ge.rrs.database.room.comment.RoomComment;
import ge.rrs.modules.auth.RRSUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class RRSRoomController {

    @RequestMapping("/room")
    public ModelAndView loadRoomView(HttpServletRequest req) throws Exception {

        ModelAndView mv = new ModelAndView();
        int roomId = Integer.parseInt(req.getParameter("id"));

        // Only for test purposes
        DBConnection connection = new DBConnection();
        //

        RoomSearchParameters params = new RoomSearchParameters();
        params.addRoomIdParameter(roomId);
        Room currentRoom = Room.getFilteredRooms(params, connection).iterator().next();

        Collection<Reservation> reservations = currentRoom.getReservations();

        double lastReservation = 9;
        ArrayList<TimePortion> timePortions = new ArrayList<>();
        for (Reservation reservation : reservations) {
            double startDate = Integer.parseInt(reservation.getStartDate().substring(11, 13));
            startDate += Integer.parseInt(reservation.getStartDate().substring(14, 16)) / 60.0;
            if (startDate < 9) startDate += 24;
            double endDate = Integer.parseInt(reservation.getEndDate().substring(11, 13));
            endDate += Integer.parseInt(reservation.getEndDate().substring(14, 16)) / 60.0;
            if (startDate < 9) endDate += 24;

            if (startDate != lastReservation) {
                double freeTimeSpan = startDate - lastReservation;
                timePortions.add(new TimePortion(lastReservation, freeTimeSpan, false));
            }
            double reservationSpan = endDate - startDate;
            timePortions.add(new TimePortion(startDate, reservationSpan, true));

            lastReservation = endDate;
        }
        double lastTimeSpan = 33 - lastReservation;
        if (lastTimeSpan != 0) timePortions.add(new TimePortion(lastReservation, lastTimeSpan, false));

        mv.setViewName("/room/room");
        mv.addObject("room", currentRoom);
        RoomComment comment = currentRoom.getRoomComment();
        if (comment != null)
            mv.addObject("roomComment", comment);
        mv.addObject("reservations", reservations);
        mv.addObject("timePortions", timePortions);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        double time = Integer.parseInt(dtf.format(now).substring(0, 2));
        time += Integer.parseInt(dtf.format(now).substring(3)) / 60.0;
        if (time <= 9) time += 24;
        time -= 9;

        System.out.println(dtf.format(now));
        mv.addObject("currentTime", dtf.format(now));
        mv.addObject("currentTimeRelativePosition", time / 24);
        mv.addObject("username", RRSUser.getCurrentUser().getUsername());

        return mv;
    }

    public class TimePortion {
        private final double timePortion;
        private final double relativePosition;
        private final boolean isReservation;

        TimePortion(double time, double timeSpan, boolean isReservation) {
            relativePosition = (time - 9) / 24;
            timePortion = timeSpan / 24;
            this.isReservation = isReservation;
        }

        public double getRelativePosition() { return relativePosition; }

        public double getTimePortion() {
            return timePortion;
        }

        public boolean isReservation() {
            return isReservation;
        }
    }
}
