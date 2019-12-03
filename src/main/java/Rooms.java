

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/rooms")
public class Rooms extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            String roomId = request.getParameter("roomId");
            if(roomId == null) {
                /// TODO: 12/3/2019 aici intra cand apasam pe new-game, noi trebui sa adaugam o noua camera in baza de date cu hostul fiind userul curent
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", request.getSession().getAttribute("currentSessionUser"));
            session.setAttribute("roomId", roomId);
            response.sendRedirect("game.jsp");
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}
