package servlet;

import connection.Utils;
import logger.Logging;
import model.*;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;


@WebServlet("/game")
public class Game extends HttpServlet {

    /***
     * A POST request results from an HTML form that specifically lists POST as the METHOD and it should be handled by doPost() method.
     * @param request
     * @param response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            /// TODO: 12/11/2019 update cards for first user
            /// TODO: 12/11/2019 cand se da finish sa se dea finish la toti userii
            Utils utils = new Utils();

            Long roomId = (Long) request.getSession().getAttribute("roomId");
            User currentSessionUser = (User) request.getSession().getAttribute("currentSessionUser");
            UserCards userCards = (UserCards) request.getSession().getAttribute("userCards");
            GameCards gameCards = (GameCards) request.getSession().getAttribute("gameCards");

            List<UserCards> usersCards = UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId);

            int currentPosition = gameCards.getCurrentPositionForUser();
            User currentUser = usersCards.get(currentPosition).getUser();
            Logging.log(Level.INFO, "Current user: " + currentUser.getUsername());

            if(currentSessionUser.getUsername().equals(currentUser.getUsername())) {
                if(request.getParameter("cardId") != null) {
                    GameUtils.putCard(request, usersCards, userCards, gameCards, currentPosition, currentUser);
                }

                else if (request.getParameter("isStarted") != null) {
                    GameUtils.isStartedGame(request, usersCards, userCards, gameCards, currentUser);
                }

                switch (request.getParameter("action")) {
                    case "next": {
                        GameUtils.actionNext(usersCards, gameCards, currentPosition, currentUser);
                        break;
                    }

                    case "take": {
                        GameUtils.actionTake(request, usersCards, userCards, gameCards, currentPosition, currentUser);
                        break;
                    }

                    case "draw": {
                      GameUtils.actionDraw(request, usersCards, userCards, gameCards, currentPosition, currentUser);
                    }
                }
            } else {
                System.out.println("Nu este randul tau!");
            }

            switch (request.getParameter("action")) {
                case "finish": {
                    List<User> joinedUsers = new ArrayList<>();
                    usersCards.forEach(userCards1 -> joinedUsers.add(userCards1.getUser()));
                    utils.deletedRoom(roomId, joinedUsers);

                    GameCardsRepository.getInstance().remove(gameCards);
                    usersCards.forEach(userCards1 -> UserCardsRepository.getInstance().remove(userCards1));

                    Logging.log(Level.INFO, "User " + currentUser.getUsername() + " has finished the game.");

                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", currentSessionUser);
                    session.setAttribute("rooms", utils.getRooms());
                    response.sendRedirect("rooms_view.jsp");
                    break;
                }
            }
        } catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}