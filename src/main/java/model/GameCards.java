package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameCards {

    private long idRoom;

    private int currentCard;

    private List<Card> cards;

    public GameCards() {
        cards = new ArrayList<Card>() {{
            for (Number number : Number.values()) {
                for (Suit suit : Suit.values()) {
                    if(!number.equals(Number.Joker)) {
                        if(!(suit.equals(Suit.Red) || suit.equals(Suit.Black))) {
                            add(new Card(suit, number));
                        }
                    }
                }
            }
            add(new Card(Suit.Red, Number.Joker));
            add(new Card(Suit.Black, Number.Joker));
        }};

        Collections.shuffle(cards);

        /// TODO: 12/8/2019 currentCard va trebui sa fie inlocuita cu o stiva sau coada cu cartile care se pun jos
        /// TODO: 12/8/2019 sa facem un pachet standard de carti
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
