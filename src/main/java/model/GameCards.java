package model;

import java.util.List;

public class GameCards {

    private long idRoom;

    private int currentCard;

    private List<Integer> cards;

    public GameCards() {
        /// TODO: 12/8/2019 currentCard va trebui sa fie inlocuita cu o stiva sau coada cu cartile care se pun jos
        /// TODO: 12/8/2019 sa facem un pachet standard de carti
    }

    public GameCards(long idRoom, int currentCard, List<Integer> cards) {
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

    public List<Integer> getCards() {
        return cards;
    }

    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }
}
