package repositoryTests;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.UserCardsRepository;
import java.util.ArrayList;


public class UserCardsRepositoryTests extends Mockito {

    @Mock
    private UserCardsRepository userCardsRepository;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserCardsForCurrentRoomTest() {
        // Given
        User user = new User(1, "diana", "diana", 1, 3);
        UserCards actual = new UserCards(1, user, new ArrayList<>());
        when(userCardsRepository.getUsersCardsForCurrentRoom(1))
                .thenReturn(new ArrayList<UserCards>() {{ add(actual);}});

        // When
        UserCards expected = userCardsRepository.getUsersCardsForCurrentRoom(1).get(0);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCardsForCurrentUserTest() {
        // Given
        User currentUser = new User(1, "diana", "diana", 1, 3);
        UserCards actual = new UserCards(1, currentUser, new ArrayList<>());
        when(userCardsRepository.getCardsForCurrentUser(currentUser, 1))
                .thenReturn((actual));

        // When
        UserCards expected = userCardsRepository.getCardsForCurrentUser(currentUser, 1);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addTest() {
        // Given
        doNothing()
                .when(userCardsRepository)
                .add(new UserCards());

        // When
        userCardsRepository.add(new UserCards());

        // Then
        Assert.assertEquals(0, userCardsRepository.getAll().size());
    }

    @Test
    public void removeTest() {
        // Given
        UserCards actual = new UserCards();
        userCardsRepository.add(actual);

        doNothing()
                .when(userCardsRepository)
                .remove(actual);

        // When
        userCardsRepository.remove(actual);

        // Then
        Assert.assertEquals(0, userCardsRepository.getAll().size());
    }
}

