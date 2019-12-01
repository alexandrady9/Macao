import connection.Utils;
import model.Room;
import model.User;
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

        try
        {
            User user = new User();

            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));

            List<Room> rooms = new ArrayList<Room>() {
                {
                    add(new Room(1, 2, 3));
                    add(new Room(2, 1, 3));
                    add(new Room(3, 5, 2));
                    add(new Room(4, 5, 2));
                    add(new Room(5, 0, 2));
                }};

            //Utils utils = new Utils();

            //if (utils.checkLogin(user.getUsername(), user.getPassword()))
            if(user.getUsername().equals("diana"))
            {

                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser",user);
                session.setAttribute("rooms", rooms);
                response.sendRedirect("rooms_view.jsp");
            }

            else
                response.sendRedirect("invalidLogin.jsp");
        }


        catch (Throwable theException)
        {
            System.out.println(theException);
        }
    }
}