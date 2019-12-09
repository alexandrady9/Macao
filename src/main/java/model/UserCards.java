package model;

import java.util.List;

public class UserCards {

    private long idRoom;

    private User user;

    private List<Card> cards;

    public UserCards() {
    }

    public UserCards(long idRoom, User user, List<Card> cards) {
        this.idRoom = idRoom;
        this.user = user;
        this.cards = cards;
    }

    public long getRoom() {
        return idRoom;
    }

    public void setRoom(long idRoom) {
        this.idRoom = idRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
