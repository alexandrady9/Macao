package repository;

import model.GameCards;
import java.util.ArrayList;
import java.util.List;


public class GameCardsRepository implements Repository<GameCards> {

    private static GameCardsRepository instance = null;

    private GameCardsRepository() {
    }

    public static GameCardsRepository getInstance() {
        if (instance == null)
            instance = new GameCardsRepository();

        return instance;
    }

    private List<GameCards> gameCards = new ArrayList<GameCards>();

    @Override
    public void add(GameCards item) {
        gameCards.add(item);
    }

    @Override
    public void update(GameCards oldItem, GameCards newItem) {
    }

    @Override
    public void remove(GameCards item) {
        gameCards.remove(item);
    }

    @Override
    public List<GameCards> getAll() {
        return gameCards;
    }

    public GameCards getByRoomId(long idRoom) {
        return gameCards
                .stream()
                .filter(gameCards1 -> gameCards1.getRoom() == idRoom)
                .findFirst()
                .orElse(null);
    }
}
