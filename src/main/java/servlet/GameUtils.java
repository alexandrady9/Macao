package servlet;

import logger.Logging;
import model.Card;
import model.GameCards;
import model.User;
import model.UserCards;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

class GameUtils {

    static void putCard(HttpServletRequest request,
                        List<UserCards> usersCards,
                        UserCards userCards,
                        GameCards gameCards,
                        int currentPosition,
                        User currentUser) {
        int cardId = Integer.parseInt(request.getParameter("cardId"));
        Card cardToPut = userCards.getCards().get(cardId);

        gameCards.setCurrentCard(cardToPut);

        List<Card> cards = usersCards.get(currentPosition).getCards();
        cards.remove(cardToPut);
        usersCards.get(currentPosition).setCards(cards);
        userCards.setCards(cards);

        if(cardToPut.getNumber().getNumberCode().equals("2"))
            gameCards.setCardsToDraw(gameCards.getCardsToDraw() + 2);
        else if(cardToPut.getNumber().getNumberCode().equals("3"))
            gameCards.setCardsToDraw(gameCards.getCardsToDraw() + 3);
        else if(cardToPut.getNumber().getNumberCode().equals("Joker") && cardToPut.getSuit().name().equals("Red"))
            gameCards.setCardsToDraw(gameCards.getCardsToDraw() + 10);
        else if(cardToPut.getNumber().getNumberCode().equals("Joker") && cardToPut.getSuit().name().equals("Black"))
            gameCards.setCardsToDraw(gameCards.getCardsToDraw() + 5);
        else if(cardToPut.getNumber().getNumberCode().equals("4"))
            gameCards.setCardsToDraw(0);

        Logging.log(Level.INFO, "User " + currentUser.getUsername() + " put down " +
                cardToPut.getNumber().getNumberCode() + " " + cardToPut.getSuit().name());

        request.getSession().setAttribute("userCards", userCards);
        request.getSession().setAttribute("gameCards", gameCards);
        request.getSession().setAttribute("usersCards", usersCards);
    }

    static void isStartedGame(HttpServletRequest request,
                              List<UserCards> usersCards,
                              UserCards userCards,
                              GameCards gameCards,
                              User currentUser) {
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
        }
        userCards.setCards(usersCards.get(0).getCards());

        Logging.log(Level.INFO, "User " + currentUser.getUsername() + " has started the game.");
        gameCards.setIsStartGame(1);

        request.getSession().setAttribute("userCards", userCards);
        request.getSession().setAttribute("gameCards", gameCards);
        request.getSession().setAttribute("usersCards", usersCards);
    }

    static void actionNext(List<UserCards> usersCards,
                           GameCards gameCards,
                           int currentPosition,
                           User currentUser) {
        if (currentPosition == usersCards.size() - 1) {
            gameCards.setCurrentPositionForUser(0);
        } else {
            gameCards.setCurrentPositionForUser(currentPosition + 1);
        }

        Logging.log(Level.INFO, "User " + currentUser.getUsername() + " press next button.");
    }

    static void actionTake(HttpServletRequest request,
                           List<UserCards> usersCards,
                           UserCards userCards,
                           GameCards gameCards,
                           int currentPosition,
                           User currentUser) {
        Card card = gameCards.getCards().get(0);
        gameCards.getCards().remove(0);
        List<Card> cards = usersCards.get(currentPosition).getCards();
        cards.add(card);
        usersCards.get(currentPosition).setCards(cards);
        userCards.setCards(cards);

        System.out.println(usersCards.get(currentPosition).getUser().getUsername() + " take " +
                card.getNumber().name() + " " + card.getSuit().name());

        Logging.log(Level.INFO, "User " + currentUser.getUsername() + " took " +
                card.getNumber().name() + " " + card.getSuit().name());

        request.getSession().setAttribute("userCards", userCards);
        request.getSession().setAttribute("gameCards", gameCards);
        request.getSession().setAttribute("usersCards", usersCards);
    }

    static void actionDraw(HttpServletRequest request,
                           List<UserCards> usersCards,
                           UserCards userCards,
                           GameCards gameCards,
                           int currentPosition,
                           User currentUser) {
        System.out.println("Cards to draw: " + gameCards.getCardsToDraw());

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

        Logging.log(Level.INFO, "User " + currentUser.getUsername() + " drawn " + cardsToDraw + " cards.");

        request.getSession().setAttribute("userCards", userCards);
        request.getSession().setAttribute("gameCards", gameCards);
        request.getSession().setAttribute("usersCards", usersCards);
    }
}
