package repository;

import model.User;
import model.UserCards;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserCardsRepository implements Repository<UserCards> {

    private static UserCardsRepository instance = null;

    private UserCardsRepository() {
    }

    public static UserCardsRepository getInstance() {
        if (instance == null)
            instance = new UserCardsRepository();

        return instance;
    }

    private List<UserCards> userCards = new ArrayList<>();

    @Override
    public void add(UserCards item) {
        userCards.add(item);
    }

    @Override
    public void update(UserCards oldItem, UserCards newItem) {
        userCards.set(userCards.indexOf(oldItem), newItem);
    }

    @Override
    public void remove(UserCards item) {
        userCards.remove(item);
    }

    @Override
    public List<UserCards> getAll() {
        return userCards;
    }

    public List<UserCards> getUsersCardsForCurrentRoom(long idRoom) {
        return userCards
                .stream()
                .filter(userCards1 -> userCards1.getRoom() == idRoom && userCards1.getUser().getIdRoom() == idRoom)
                .collect(Collectors.toList());
    }

    public UserCards getCardsForCurrentUser(User user, long idRoom) {
        return userCards
                    .stream()
                    .filter(userCards1 -> userCards1.getUser().getId() == user.getId() && userCards1.getRoom() == idRoom)
                    .findFirst()
                    .orElse(null);
    }

    public void removeAll() {
        userCards.clear();
    }
}
