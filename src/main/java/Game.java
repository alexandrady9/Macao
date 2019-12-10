import connection.Utils;
import model.Card;
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
import java.util.stream.Collectors;


@WebServlet("/game")
public class Game extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            Utils utils = new Utils();

            Long roomId = (Long) request.getSession().getAttribute("roomId");
            User currentSessionUser = (User) request.getSession().getAttribute("currentSessionUser");
            UserCards userCards = (UserCards) request.getSession().getAttribute("userCards");
            GameCards gameCards = (GameCards) request.getSession().getAttribute("gameCards");
            //List<UserCards> usersCards = (List<UserCards>) request.getSession().getAttribute("usersCards");

            List<UserCards> usersCards = UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId);

            System.out.println("session: " + currentSessionUser.getUsername());
            int currentPosition = gameCards.getCurrentPositionForUser();
            User currentUser = usersCards.get(currentPosition).getUser();
            System.out.println("current user:" + currentUser.getUsername());

            if(currentSessionUser.getUsername().equals(currentUser.getUsername())) {
                switch (request.getParameter("action")) {
                    case "next": {
                        if (currentPosition == usersCards.size() - 1) {
                            gameCards.setCurrentPositionForUser(0);
                        } else {
                            gameCards.setCurrentPositionForUser(currentPosition + 1);
                        }
                        break;
                    }

                    case "draw": {
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
                        gameCards.setCardsToDraw(0);
                        System.out.println(currentUser.getUsername() + " has " + usersCards.get(currentPosition).getCards().size());
                        System.out.print("Cards in game: " + gameCards.getCards().size());
                        break;
                    }

                    case "take": {
                        Card card = gameCards.getCards().get(0);
                        gameCards.getCards().remove(0);
                        List<Card> cards = usersCards.get(currentPosition).getCards();
                        cards.add(card);
                        usersCards.get(currentPosition).setCards(cards);
                        System.out.println(usersCards.get(currentPosition).getUser().getUsername() + " take " +
                                card.getNumber().name() + " " + card.getSuit().name());
//                    System.out.println(usersCards.get(currentPosition).getUser().getUsername() + " has " +
//                            usersCards.get(currentPosition).getCards().size());
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

                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", currentSessionUser);
                    session.setAttribute("rooms", utils.getRooms());
                    response.sendRedirect("rooms_view.jsp");
                    break;
                }

                case "start": {
                    for (int i = 0; i < usersCards.size(); i++) {
                        List<Card> givenCards = gameCards.getCards()
                                .stream()
                                .limit(5)
                                .collect(Collectors.toList());
//                        System.out.println(usersCards.get(i).getUser().getUsername() + ": ");
//                        System.out.println("Cards: " + givenCards.size());
                        List<Card> remainingCards = gameCards.getCards();
                        remainingCards.subList(0, 5).clear();
                        gameCards.setCards(remainingCards);
//                        System.out.println("Game cards: " + gameCards.getCards().size());
                        usersCards.get(i).setCards(givenCards);
//                        System.out.println("User cards: " + usersCards.get(i).getCards().size());
                    }

                    request.getSession().removeAttribute("gameCards");

                    userCards = UserCardsRepository.getInstance().getCardsForCurrentUser(currentSessionUser, roomId);
                    gameCards = GameCardsRepository.getInstance().getByRoomId(roomId);
                    usersCards = UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId);

                    request.getSession().setAttribute("userCards", userCards);
                    request.getSession().setAttribute("gameCards", gameCards);
                    request.getSession().setAttribute("usersCards", usersCards);
                    break;
                }
            }
        } catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}