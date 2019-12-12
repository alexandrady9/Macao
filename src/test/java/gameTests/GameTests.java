package gameTests;

import model.Card;
import model.GameCards;
import model.User;
import model.UserCards;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.UserCardsRepository;
import servlet.Game;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


public class GameTests extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethodTest() {
        //Given
        User currentUser = new User(1, "diana", "diana", 1, 3);
        UserCardsRepository.getInstance().add(new UserCards(1, currentUser, new ArrayList<>()));

        when(session.getAttribute("roomId")).thenReturn(1L);
        when(session.getAttribute("currentSessionUser")).thenReturn(currentUser);
        when(session.getAttribute("userCards")).thenReturn(UserCardsRepository.getInstance().getCardsForCurrentUser(currentUser,1));
        when(session.getAttribute("gameCards")).thenReturn(new GameCards(1, new Card(), new ArrayList<>(), 0, 0, 0));
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("finish");

        //When
        new Game().doPost(request, response);

        //Then
        verify(request, atLeast(1)).getParameter("action");
    }
}
