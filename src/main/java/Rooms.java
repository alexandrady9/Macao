
import model.GameCards;
import model.User;
import model.UserCards;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import connection.Utils;
import model.Room;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/rooms")
public class Rooms extends HttpServlet {

    private Utils utils = new Utils();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            User currentUser = (User) request.getSession().getAttribute("currentSessionUser");

            if(request.getParameter("roomId") == null) {

                utils.createRoom(currentUser.getId());
                Room createdRoom = utils.getLastRoomCreated();

                //we set id room for current user just because to not request another query to db
                currentUser.setIdRoom(createdRoom.getId());
                /// TODO: 12/8/2019 de facut un pachet standard pentru fiecare joc cand se incepe care se pune in arrayList
                GameCardsRepository
                        .getInstance()
                        .add(new GameCards(createdRoom.getId(), 0, new ArrayList<>()));
                UserCardsRepository
                        .getInstance()
                        .add(new UserCards(createdRoom.getId(), currentUser, new ArrayList<>()));

                setAttributeForWindow(request, currentUser, createdRoom.getId());
                response.sendRedirect("game.jsp");

            } else {
                long roomId = Long.parseLong(request.getParameter("roomId"));

                Room room = utils.getRoom(roomId);
                if(room.getJoinedUsers() < 6) {
                    utils.joinRoom(currentUser.getId(), room.getId());

                    currentUser.setIdRoom(roomId);
                    UserCardsRepository
                            .getInstance()
                            .add(new UserCards(roomId, currentUser, new ArrayList<>()));

                    setAttributeForWindow(request, currentUser, roomId);
                    response.sendRedirect("game.jsp");
                } else {
                    /// TODO: 12/8/2019 Mesaj de alerta(pop up) ca nu poate da join
                }
            }
        }

        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }

    private void setAttributeForWindow(HttpServletRequest request, User currentUser, long roomId) {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentSessionUser", currentUser);
        session.setAttribute("roomId", roomId);
        session.setAttribute("gameCards", GameCardsRepository.getInstance().getByRoomId(roomId));
        session.setAttribute("usersCards", UserCardsRepository.getInstance().getUsersCardsForCurrentRoom(roomId));

        session.setAttribute("userCards", UserCardsRepository.getInstance().getCardsForCurrentUser(currentUser, roomId));

    }
}
