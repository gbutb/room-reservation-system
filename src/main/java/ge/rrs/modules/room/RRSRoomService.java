package ge.rrs.modules.room;

import ge.rrs.database.DBConnection;
import ge.rrs.database.reservation.Reservation;
import ge.rrs.database.room.Room;
import ge.rrs.database.room.RoomSearchParameters;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    public static class ReservationTimePortion {
        private final double timePortion;
        private final double relativePosition;
        private final Reservation reservation;

        ReservationTimePortion(double time, double timeSpan, Reservation reservation) {
            relativePosition = (time - 9) / 24;
            timePortion = timeSpan / 24;
            this.reservation = reservation;
        }

        public double getRelativePosition() { return relativePosition; }

        public double getTimePortion() {
            return timePortion;
        }

        public Reservation getReservation() {
            return reservation;
        }
    }

}
