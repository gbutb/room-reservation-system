// RRSSessionListener.java
package ge.rrs;

import ge.rrs.database.room.RoomSearchParameters;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * HttpSessionListener implementation.
 */
public class RRSSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        RoomSearchParameters roomSearchParameters = new RoomSearchParameters();
        session.setAttribute("filterParams", roomSearchParameters);

        System.out.println("session initialized");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        session.removeAttribute("filterParams");

        System.out.println("session destroyed");
    }
}
