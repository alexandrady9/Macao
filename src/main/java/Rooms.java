import model.Room;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "rooms",
        urlPatterns = {"/rooms"},
        loadOnStartup = 1)
public class Rooms extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try
        {

            System.out.println(request);
            Room room = (Room) request.getSession().getAttribute("room");

            System.out.println(room.getHostedBy());

            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", request.getSession().getAttribute("currentSessionUser"));
            response.sendRedirect("game.jsp");
        }


        catch (Throwable theException)
        {
            System.out.println(theException);
        }
    }
}
