package repositoryTests;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GameCardsRepository;
import java.util.ArrayList;


public class GameCardsRepositoryTests extends Mockito {

    @Mock
    private GameCardsRepository gameCardsRepository;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getByRoomIdTest() {
        // Given
        GameCards actual = new GameCards(1, new Card(),new ArrayList<>(), 1, 0);
        when(gameCardsRepository.getByRoomId(1)).thenReturn(actual);

        // When
        GameCards expected = gameCardsRepository.getByRoomId(1);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addTest() {
        // Given
        doNothing()
                .when(gameCardsRepository)
                .add(new GameCards());

        // When
        gameCardsRepository.add(new GameCards());

        // Then
        Assert.assertEquals(0, gameCardsRepository.getAll().size());
    }

    @Test
    public void removeTest() {
        // Given
        GameCards gameCards = new GameCards();
        gameCardsRepository.add(gameCards);

        doNothing()
                .when(gameCardsRepository)
                .remove(gameCards);

        // When
        gameCardsRepository.remove(gameCards);

        // Then
        Assert.assertEquals(0, gameCardsRepository.getAll().size());
    }
}

