import model.GameCards;
import model.User;
import model.UserCards;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@WebServlet("/game")
public class Game extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            Long roomId = (Long) request.getSession().getAttribute("roomId");
            User currentSessionUser = (User) request.getSession().getAttribute("currentSessionUser");
            UserCards userCards = (UserCards) request.getSession().getAttribute("userCards");
            GameCards gameCards = (GameCards) request.getSession().getAttribute("gameCards");
            List<UserCards> usersCards = (List<UserCards>) request.getSession().getAttribute("usersCards");

            switch (request.getParameter("action")){
                case "next" : {
                    response.sendRedirect("index.jsp");
                    break;
                }

                case "finish" : {
                    /// TODO: 12/9/2019 actualizare lista de camere
                    /// TODO: 12/9/2019 stergere camera curenta si de la userii id-ul camerei, si din gameCards, userCards
                    response.sendRedirect("roomView.jsp");
                    break;
                }
            }
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            Long roomId = (Long) request.getSession().getAttribute("roomId");
            User currentSessionUser = (User) request.getSession().getAttribute("currentSessionUser");
            UserCards userCards = (UserCards) request.getSession().getAttribute("userCards");
            GameCards gameCards = (GameCards) request.getSession().getAttribute("gameCards");
            List<UserCards> usersCards = (List<UserCards>) request.getSession().getAttribute("usersCards");

            switch (request.getParameter("action")){
                case "umflatura" : {
                    /// TODO: 12/9/2019 se vede cate carti au fost in pachetul de jos si se iau dupa din GameCards
                    response.sendRedirect("invalidLogin.jsp");
                    break;
                }

                case "take" : {
                    /// TODO: 12/9/2019 se ia din gameCards carte
                    break;
                }

                case "start" : {
                    /// TODO: 12/9/2019 se impart cartile la jucatori
                    break;
                }
            }
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}