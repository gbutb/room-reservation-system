package ge.rrs.modules.room;

import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.room.Room;
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

        Room currentRoom = RRSRoomService.getCurrentRoom(req);
        RRSUser currentUser = RRSUser.getCurrentUser();

        // Take Current Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        double time = Integer.parseInt(dtf.format(now).substring(0, 2));
        time += Integer.parseInt(dtf.format(now).substring(3)) / 60.0;
        if (time <= 9) time += 24;

        // Update Room Comment
        if (req.getParameter("comment") != null && !req.getParameter("commentString").isEmpty()) {
            currentRoom.setRoomComment(req.getParameter("commentString"));
        }

        // Add Room Attribute
        mv.addObject("room", currentRoom);

        // Set Current Time And User Attributes
        mv.addObject("currentTime", dtf.format(now));
        mv.addObject("currentTimeRelativePosition", (time - 9) / 24);
        mv.addObject("user", currentUser);


        // Get Reservations And Time Portions Respectively
        Collection<Reservation> reservations = currentRoom.getReservations();
        ArrayList<RRSRoomService.ReservationTimePortion> timePortions = new ArrayList<>();
        RRSRoomService.getTimePortions(timePortions, reservations, time, mv);

        // Set Comment Attributes
        RoomComment comment = currentRoom.getRoomComment();
        if (comment == null) {
            mv.addObject("roomComment", "");
            mv.addObject("roomCommentDate", "(No Comment For This Room)");
        } else {
            mv.addObject("roomComment", comment.getUserComment());
            mv.addObject("roomCommentDate", "Latest Comment - " + comment.getCommentDate().substring(0, 16));
        }

        // Make New Reservation If Valid Reservation Is Requested And Set Attributes Accordingly
        RRSRoomService.handleReservationAndSetAttributes(timePortions, reservations, now, time, currentRoom, req, mv);

        return mv;
    }
}
