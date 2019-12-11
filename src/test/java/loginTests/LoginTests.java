package loginTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import servlet.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class LoginTests extends Mockito {

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
        when(request.getParameter("username")).thenReturn("diana");
        when(request.getParameter("password")).thenReturn("diana");
        when(request.getSession()).thenReturn(session);

        //When
        new Login().doPost(request, response);

        //Then
        //verify username was called
        verify(request, atLeast(1)).getParameter("username");

       // verify(response).setHeader("Location", "room_view.jsp");
    }
}
