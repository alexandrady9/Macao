package model;

import java.util.List;

public class GameCards {

    private long idRoom;

    /***
     * the last card that was put down
     */
    private Card currentCard;

    /***
     * the cards from deck that have remained to be taken
     */
    private List<Card> cards;

    private int currentPositionForUser;

    /***
     * the number of cards that the user must draw
     */
    private int cardsToDraw;

    private int isStartGame;

    public GameCards() { }

    public GameCards(long idRoom, Card currentCard, List<Card> cards, int currentPositionForUser, int cardsToDraw, int isStartGame) {
        this.idRoom = idRoom;
        this.currentCard = currentCard;
        this.cards = cards;
        this.currentPositionForUser = currentPositionForUser;
        this.cardsToDraw = cardsToDraw;
        this.isStartGame = isStartGame;
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

    public int getIsStartGame() {
        return isStartGame;
    }

    public void setIsStartGame(int isStartGame) {
        this.isStartGame = isStartGame;
    }
}
