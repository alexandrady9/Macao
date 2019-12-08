

import connection.Utils;
import model.Room;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/rooms")
public class Rooms extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            String roomId = request.getParameter("roomId");
            Utils utils = new Utils();
            User user = (User)request.getSession().getAttribute("currentSessionUser");
            long userId = utils.getUserId(user.getUsername());

            if(roomId == null) {
                utils.createRoom(userId);

                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", request.getSession().getAttribute("currentSessionUser"));
                session.setAttribute("roomId", roomId);
                response.sendRedirect("game.jsp");
            } else {
                Room room = utils.getRoom(Long.parseLong(roomId));
                if(room.getJoinedUsers() < 6) {
                    System.out.print(userId + " " + room.getId());
                    utils.joinRoom(userId, room.getId());

                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", request.getSession().getAttribute("currentSessionUser"));
                    session.setAttribute("roomId", roomId);
                    response.sendRedirect("game.jsp");
                } else {
                    /// TODO: 12/8/2019 Mesaj de alerta(pop up) ca nu poate da join
                }
            }
        }

        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}
