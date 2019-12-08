
import model.GameCards;
import model.User;
import model.UserCards;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@WebServlet("/rooms")
public class Rooms extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            Long roomId = Long.parseLong(request.getParameter("roomId"));
            if(roomId == null) {
                /// TODO: 12/3/2019 aici intra cand apasam pe new-game, noi trebui sa adaugam o noua camera in baza de date cu hostul fiind userul curent
                /// TODO: 12/8/2019 de facut un pachet standard pentru fiecare joc cand se incepe care se pune in arrayList
                GameCardsRepository.getInstance()
                        .add(new GameCards(GameCardsRepository.getInstance().getAll().size() + 1, 0, new ArrayList<>()));
            }

            User currentUser = (User) request.getSession().getAttribute("currentSessionUser");

            /// TODO: 12/8/2019 update in baza de date, sa puna roomId la user
            currentUser.setIdRoom(roomId);
            UserCardsRepository.getInstance().add(new UserCards(roomId, currentUser, new ArrayList<>()));

            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser", currentUser);
            session.setAttribute("roomId", roomId);
            session.setAttribute("gameCards", GameCardsRepository.getInstance().getByRoomId(roomId));
            session.setAttribute("usersCards", UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId));

            session.setAttribute("userCards", UserCardsRepository.getInstance().getCardsForCurrentUser(currentUser, roomId));
            response.sendRedirect("game.jsp");
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}
