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
        mv.setViewName("/room/room");

        DBConnection connection = DBConnection.getContextConnection();
        Room currentRoom = RRSRoomService.getCurrentRoom(req);
        RRSUser currentUser = RRSUser.getCurrentUser();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        double time = Integer.parseInt(dtf.format(now).substring(0, 2));
        time += Integer.parseInt(dtf.format(now).substring(3)) / 60.0;
        if (time <= 9) time += 24;

        // Update Comment
        if (req.getParameter("comment") != null && !req.getParameter("commentString").isEmpty()) {
            currentRoom.setRoomComment(req.getParameter("commentString"));
        }

        mv.addObject("currentTime", dtf.format(now));
        mv.addObject("currentTimeRelativePosition", (time - 9) / 24);
        mv.addObject("user", currentUser);

        Collection<Reservation> reservations = currentRoom.getReservations();
        System.out.println(reservations);

        ArrayList<RRSRoomService.ReservationTimePortion> timePortions = new ArrayList<>();

        RRSRoomService.getTimePortions(timePortions, reservations, time, mv);

        mv.addObject("room", currentRoom);
        mv.addObject("timePortions", timePortions);

        RoomComment comment = currentRoom.getRoomComment();
        if (comment == null) {
            mv.addObject("roomComment", "-");
            mv.addObject("roomCommentDate", "(No Comment For This Room)");
        } else {
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            mv.addObject("roomComment", comment.getUserComment());
            mv.addObject("roomCommentDate", "Latest Comment - " + comment.getCommentDate().substring(0, 16));
        }

        return mv;
    }


}
