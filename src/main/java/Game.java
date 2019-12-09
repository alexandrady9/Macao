import connection.Utils;
import model.GameCards;
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


@WebServlet("/game")
public class Game extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            Utils utils = new Utils();

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
                    List<User> joinedUsers = new ArrayList<>();
                    usersCards.forEach(userCards1 -> joinedUsers.add(userCards1.getUser()));
                    utils.deletedRoom(roomId, joinedUsers);

                    GameCardsRepository.getInstance().remove(gameCards);
                    usersCards.forEach(userCards1 -> UserCardsRepository.getInstance().remove(userCards1));

                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", currentSessionUser);
                    session.setAttribute("rooms", utils.getRooms());
                    response.sendRedirect("rooms_view.jsp");
                    break;
                }

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
                    //for (int i = 0; i < gameCards.getCards().size(); i++) {
                        /// TODO: 12/10/2019 update the userCardsrepo, gameCardsRepo
                        request.getSession().removeAttribute("gameCards");

                        userCards = UserCardsRepository.getInstance().getCardsForCurrentUser(currentSessionUser, roomId);
                        gameCards = GameCardsRepository.getInstance().getByRoomId(roomId);
                        usersCards = UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId);

                        request.getSession().setAttribute("userCards", userCards);
                        request.getSession().setAttribute("gameCards", gameCards);
                        request.getSession().setAttribute("usersCards", usersCards);
                    //}
                    break;
                }
            }
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}