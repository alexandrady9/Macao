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

    private List<GameCards> gameCards = new ArrayList<GameCards>() {{
        add(new GameCards(1, 1, new ArrayList<Integer>(){{add(1); add(2);}} ));
        add(new GameCards(2, 0, new ArrayList<Integer>(){{add(1); add(2);}} ));
        add(new GameCards(3, 2, new ArrayList<Integer>(){{add(1); add(2);}} ));
        add(new GameCards(4, 1, new ArrayList<Integer>() ));
        add(new GameCards(5, 0, new ArrayList<Integer>(){{add(1); add(2); add(3);}} ));
        add(new GameCards(6, 2, new ArrayList<Integer>() ));
    }};

    @Override
    public void add(GameCards item) {
        gameCards.add(item);
    }

    @Override
    public void update(GameCards item) {
        /// TODO: 12/8/2019 functie pe care sa o apelam cand trebuie sa updatam listele, cand se pune o carte jos si cand se ia din pachet
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
        return gameCards.stream().filter(gameCards1 -> gameCards1.getRoom() == idRoom).findFirst().orElseThrow();
    }
}
