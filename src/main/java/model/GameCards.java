package model;

import java.util.List;

public class GameCards {

    private long idRoom;

    private Card currentCard;

    private List<Card> cards;

    private int currentPositionForUser;

    private int cardsToDraw;

    public GameCards() { }

    public GameCards(long idRoom, Card currentCard, List<Card> cards, int currentPositionForUser, int cardsToDraw) {
        this.idRoom = idRoom;
        this.currentCard = currentCard;
        this.cards = cards;
        this.currentPositionForUser = currentPositionForUser;
        this.cardsToDraw = cardsToDraw;
    }

    public long getRoom() {
        return idRoom;
    }

    public void setRoom(long idRoom) {
        this.idRoom = idRoom;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


    public int getCurrentPositionForUser() {
        return currentPositionForUser;
    }

    public void setCurrentPositionForUser(int currentPositionForUser) {
        this.currentPositionForUser = currentPositionForUser;
    }


    public int getCardsToDraw() {
        return cardsToDraw;
    }

    public void setCardsToDraw(int cardsToDraw) {
        this.cardsToDraw = cardsToDraw;
    }
}
