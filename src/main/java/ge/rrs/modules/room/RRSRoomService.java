package ge.rrs.modules.room;

import ge.rrs.database.DBConnection;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;
import ge.rrs.modules.auth.RRSUser;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class RRSRoomService {

    public static Room getCurrentRoom(HttpServletRequest req) throws Exception {
        DBConnection connection = DBConnection.getContextConnection();
        RoomSearchParameters params = new RoomSearchParameters();
        params.addRoomIdParameter(Integer.parseInt(req.getParameter("id")));
        Collection<Room> rooms = Room.getFilteredRooms(params, connection);
        if (rooms.isEmpty()) return null;
        return rooms.iterator().next();
    }

    public static void getTimePortions(ArrayList<ReservationTimePortion> timePortions, Collection<Reservation> reservations,
                                       double currentTime, ModelAndView mv) {
        Reservation currentReservation = null;

        double lastReservation = 9;
        for (Reservation reservation : reservations) {
            double startDate = Integer.parseInt(reservation.getStartDate().substring(11, 13));
            startDate += Integer.parseInt(reservation.getStartDate().substring(14, 16)) / 60.0;
            if (startDate < 9) startDate += 24;
            double endDate = Integer.parseInt(reservation.getEndDate().substring(11, 13));
            endDate += Integer.parseInt(reservation.getEndDate().substring(14, 16)) / 60.0;
            if (endDate <= 9) endDate += 24;

            if (currentTime >= startDate && currentTime < endDate) currentReservation = reservation;

            double reservationSpan = endDate - startDate;
            timePortions.add(new ReservationTimePortion(startDate, reservationSpan, reservation));
        }

        if (currentReservation != null) mv.addObject("currentReservation", currentReservation);
    }

    public static void handleReservationAndSetAttributes(ArrayList<ReservationTimePortion> timePortions,
                                                         Collection<Reservation> reservations, LocalDateTime now, double time, Room currentRoom,
                                                         HttpServletRequest req, ModelAndView mv) throws Exception {
        boolean invalidReservation = false;
        if (req.getParameter("reserve") != null) {
            double startDate = Integer.parseInt(req.getParameter("fromTime").substring(0, 2));
            startDate += Integer.parseInt(req.getParameter("fromTime").substring(3, 5)) / 60.0;
            if (startDate < 9) startDate += 24;
            startDate -= 9;
            double endDate = Integer.parseInt(req.getParameter("toTime").substring(0, 2));
            endDate += Integer.parseInt(req.getParameter("toTime").substring(3, 5)) / 60.0;
            if (endDate <= 9) endDate += 24;
            endDate -= 9;

            for (RRSRoomService.ReservationTimePortion portion : timePortions) {
                double rPos = portion.getRelativePosition();
                double rPor = portion.getTimePortion();
                if ((rPos >= startDate / 24.0 && rPos < endDate / 24.0)
                        || (rPos + rPor > startDate / 24.0 && rPos + rPor <= endDate / 24.0)
                        || rPos < startDate / 24.0 && rPos + rPor > endDate / 24.0) {
                    invalidReservation = true;
                    break;
                }
            }

            if (!invalidReservation) {
                DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String resStartDate = dtf1.format(now);
                String resEndDate = dtf1.format(now);

                if (startDate < 15 && endDate >= 15) {
                    resEndDate = dtf1.format(now.plusDays(1));
                } else if (startDate >= 15 && endDate > 15 && time <= 24) {
                    resStartDate = dtf1.format(now.plusDays(1));
                    resEndDate = dtf1.format(now.plusDays(1));
                }
                Reservation newReservation = new Reservation(currentRoom.getRoomId(),
                        resStartDate + " " + req.getParameter("fromTime"),
                        resEndDate + " " + req.getParameter("toTime"),
                        false,
                        RRSUser.getCurrentUser().getPrimaryKey(),
                        DBConnection.getContextConnection());
                newReservation.insertEntry();

                reservations = currentRoom.getReservations();
                timePortions = new ArrayList<>();
                RRSRoomService.getTimePortions(timePortions, reservations, time, mv);
            }
        }

        mv.addObject("invalidReservation", invalidReservation);
        mv.addObject("reservations", reservations);
        mv.addObject("timePortions", timePortions);
    }

    public static class ReservationTimePortion {
        private final double timePortion;
        private final double relativePosition;
        private final Reservation reservation;

        ReservationTimePortion(double time, double timeSpan, Reservation reservation) {
            relativePosition = (time - 9) / 24;
            timePortion = timeSpan / 24;
            this.reservation = reservation;
        }

        public double getRelativePosition() {
            return relativePosition;
        }

        public double getTimePortion() {
            return timePortion;
        }

        public Reservation getReservation() {
            return reservation;
        }
    }

}
