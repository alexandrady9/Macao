package roomsTests;

import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import servlet.Rooms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RoomsTests extends Mockito {

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
        doNothing()
                .when(session)
                .setAttribute("currentSessionUser", new User(1, "diana", "diana", 1));
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("roomId")).thenReturn(null);

        //When
        new Rooms().doPost(request, response);

        //Then
        //verify roomId was called
        verify(request, atLeast(1)).getParameter("roomId");

//        try {
//            verify(response).sendRedirect(
//                    "/game.jsp" );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
