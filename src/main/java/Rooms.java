
import model.*;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import connection.Utils;
import model.User;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


@WebServlet(urlPatterns = {"/rooms"},  asyncSupported=true)
public class Rooms extends HttpServlet {

    private Utils utils = new Utils();
    private List<AsyncContext> contexts = new LinkedList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10 * 60 * 1000);
        contexts.add(asyncContext);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();

        try {
            User currentUser = (User) request.getSession().getAttribute("currentSessionUser");

            if (request.getParameter("roomId") == null) {

                utils.createRoom(currentUser.getId());
                Room createdRoom = utils.getLastRoomCreated();

                //we set id room for current user just because to not request another query to db
                currentUser.setIdRoom(createdRoom.getId());

                List<Card> cards = GenerateDeck.generate();
                Random rand = new Random();
                Card currentCard = cards.get(rand.nextInt(cards.size()));
                cards.remove(currentCard);

                GameCardsRepository
                        .getInstance()
                        .add(new GameCards(createdRoom.getId(), currentCard, cards, 0, 0));
                UserCardsRepository
                        .getInstance()
                        .add(new UserCards(createdRoom.getId(), currentUser, new ArrayList<>()));

                setAttributeForWindow(request, currentUser, createdRoom.getId());
                response.sendRedirect("game.jsp");

            } else {
                long roomId = Long.parseLong(request.getParameter("roomId"));

                Room room = utils.getRoom(roomId);
                utils.joinRoom(currentUser.getId(), room.getId());

                currentUser.setIdRoom(roomId);
                UserCardsRepository
                        .getInstance()
                        .add(new UserCards(roomId, currentUser, new ArrayList<>()));

                setAttributeForWindow(request, currentUser, roomId);
                response.sendRedirect("game.jsp");
            }

            for (AsyncContext asyncContext : asyncContexts) {
                try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
                    writer.flush();
                    asyncContext.complete();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Throwable theException) {
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
