package model;

import java.util.List;

public class GameCards {

    private long idRoom;

    private int currentCard;

    private List<Card> cards;

    public GameCards() {
        /// TODO: 12/8/2019 currentCard va trebui sa fie inlocuita cu o stiva sau coada cu cartile care se pun jos
    }

    public GameCards(long idRoom, int currentCard, List<Card> cards) {
        this.idRoom = idRoom;
        this.currentCard = currentCard;
        this.cards = cards;
    }

    public long getRoom() {
        return idRoom;
    }

    public void setRoom(long idRoom) {
        this.idRoom = idRoom;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
