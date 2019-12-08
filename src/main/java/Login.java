import connection.Utils;
import model.Room;
import model.User;
import model.UserCards;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "login",
        urlPatterns = {"/login"},
        loadOnStartup = 1)
public class Login extends HttpServlet {

    private Utils utils = new Utils();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            User currentUser = new User();

            currentUser.setUsername(request.getParameter("username"));
            currentUser.setPassword(request.getParameter("password"));

            List<User> users = utils.getUsers();
            users.forEach(user1 -> UserCardsRepository
                    .getInstance()
                    .add(new UserCards(user1.getIdRoom(), user1, new ArrayList<>() {{
                add(1); add(2); add(3); // am adaugat niste carti harcodate, sa vedem designul
            }})));

            List<Room> rooms = utils.getRooms();
            rooms.forEach(room -> room
                    .setJoinedUsers(UserCardsRepository
                            .getInstance()
                            .getUsersCardsForCurrentRoom(room.getId()).size()
                    )
            );

            rooms.get(2).setJoinedUsers(6);

            currentUser = utils.checkLogin(currentUser.getUsername(), currentUser.getPassword());

            if (currentUser != null) {
                setAttributeForWindow(request, currentUser, rooms);
                response.sendRedirect("rooms_view.jsp");
            }

            else {
                response.sendRedirect("invalidLogin.jsp");
            }
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }

    private void setAttributeForWindow(HttpServletRequest request, User currentUser, List<Room> rooms) {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentSessionUser", currentUser);
        session.setAttribute("rooms", rooms);
    }
}