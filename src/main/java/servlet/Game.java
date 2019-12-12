package servlet;

import connection.Utils;
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
            ConfigureLog.configureLogFile().info("Current user: " + currentUser.getUsername());

            if(currentSessionUser.getUsername().equals(currentUser.getUsername())) {
                if(request.getParameter("cardId") != null) {
                    int cardId = Integer.parseInt(request.getParameter("cardId"));
                    Card cardToPut = userCards.getCards().get(cardId);

                    gameCards.setCurrentCard(cardToPut);

                    List<Card> cards = usersCards.get(currentPosition).getCards();
                    cards.remove(cardToPut);
                    usersCards.get(currentPosition).setCards(cards);
                    userCards.setCards(cards);

                    ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " put down " +
                            cardToPut.getNumber().getNumberCode() + " " + cardToPut.getSuit().name());

                    request.getSession().setAttribute("userCards", userCards);
                    request.getSession().setAttribute("gameCards", gameCards);
                    request.getSession().setAttribute("usersCards", usersCards);
                }

                else if(request.getParameter("cardsToDraw") != null) {
                    gameCards.setCardsToDraw(Integer.parseInt(request.getParameter("cardsToDraw")));

                    List<Card> cardsToDraw = gameCards.getCards()
                            .stream()
                            .limit(gameCards.getCardsToDraw())
                            .collect(Collectors.toList());
                    List<Card> remainingCards = gameCards.getCards();
                    remainingCards.subList(0, gameCards.getCardsToDraw()).clear();
                    gameCards.setCards(remainingCards);

                    List<Card> newUserCards = usersCards.get(currentPosition).getCards();
                    newUserCards.addAll(cardsToDraw);
                    usersCards.get(currentPosition).setCards(newUserCards);
                    userCards.setCards(newUserCards);
                    gameCards.setCardsToDraw(0);

                    System.out.println(currentUser.getUsername() + " has " + usersCards.get(currentPosition).getCards().size());
                    System.out.print("Cards in game: " + gameCards.getCards().size());

                    ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " drawn " + cardsToDraw + " cards.");

                    request.getSession().setAttribute("userCards", userCards);
                    request.getSession().setAttribute("gameCards", gameCards);
                    request.getSession().setAttribute("usersCards", usersCards);
                }

                else if (request.getParameter("isStarted") != null) {
                    System.out.println("Start");
                    for (int i = 0; i < usersCards.size(); i++) {
                        List<Card> givenCards = gameCards.getCards()
                                .stream()
                                .limit(5)
                                .collect(Collectors.toList());
                        List<Card> remainingCards = gameCards.getCards();
                        remainingCards.subList(0, 5).clear();
                        gameCards.setCards(remainingCards);
                        usersCards.get(i).setCards(givenCards);
                        userCards.setCards(givenCards);
//                        System.out.println(usersCards.get(i).getUser().getUsername() + ": ");
//                        System.out.println("Cards: " + givenCards.size());
//                        System.out.println("servlet.Game cards: " + gameCards.getCards().size());
//                        System.out.println("User cards: " + usersCards.get(i).getCards().size());
                    }

                    ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " has started the game.");
                    gameCards.setIsStartGame(1);

                    request.getSession().setAttribute("userCards", userCards);
                    request.getSession().setAttribute("gameCards", gameCards);
                    request.getSession().setAttribute("usersCards", usersCards);
                }

                switch (request.getParameter("action")) {
                    case "next": {
                        if (currentPosition == usersCards.size() - 1) {
                            gameCards.setCurrentPositionForUser(0);
                        } else {
                            gameCards.setCurrentPositionForUser(currentPosition + 1);
                        }
                        ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " press next button.");
                        break;
                    }

                    case "take": {
                        Card card = gameCards.getCards().get(0);
                        gameCards.getCards().remove(0);
                        List<Card> cards = usersCards.get(currentPosition).getCards();
                        cards.add(card);
                        usersCards.get(currentPosition).setCards(cards);
                        userCards.setCards(cards);

                        System.out.println(usersCards.get(currentPosition).getUser().getUsername() + " take " +
                                card.getNumber().name() + " " + card.getSuit().name());

                        ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " took " +
                                card.getNumber().name() + " " + card.getSuit().name());

                        request.getSession().setAttribute("userCards", userCards);
                        request.getSession().setAttribute("gameCards", gameCards);
                        request.getSession().setAttribute("usersCards", usersCards);
                        break;
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

                    ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " has finished the game.");

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