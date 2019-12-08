import connection.Utils;
import model.Room;
import model.User;
import model.UserCards;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            User user = new User();
            Utils utils = new Utils();

            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));

            List<User> users = Utils.users;
            users.forEach(user1 -> UserCardsRepository.getInstance().add(new UserCards(user1.getIdRoom(), user1, new ArrayList<>() {{
                add(1); add(2); add(3); // am adaugat niste carti harcodate, sa vedem designul
            }})));

            List<Room> rooms = new ArrayList<>() {
                {
                    add(new Room(1, 2, UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(1).size()));
                    add(new Room(2, 1, UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(2).size()));
                    add(new Room(3, 5, UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(3).size()));
                    add(new Room(4, 5, UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(4).size()));
                    add(new Room(5, 0, UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(5).size()));
                }};

            /// TODO: 12/3/2019 de luat lista de camere din baza de date

            if (utils.checkLogin(user.getUsername(), user.getPassword()) != null) {

                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", utils.checkLogin(user.getUsername(), user.getPassword()));
                session.setAttribute("rooms", rooms);
                response.sendRedirect("rooms_view.jsp");
            }

            else
                response.sendRedirect("invalidLogin.jsp");
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}