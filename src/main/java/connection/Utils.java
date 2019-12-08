package connection;

import model.Room;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Utils {
    private static List<User> users = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();

    public Utils() {
        getUsers();
    }

    private void getUsers() {
        try {
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from user";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                int idRoom = rs.getInt("idRoom");
                User user = new User(id, name, password);
                users.add(user);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> getRooms() {
        try {
            rooms.clear();
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from room";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                int hostedBy = rs.getInt("idHost");
                int joinedUsers = rs.getInt("joinedUsers");
                Room room = new Room(id, hostedBy, joinedUsers);
                rooms.add(room);
            }
            rs.close();
            return rooms;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * validation for login
     *
     * @param username
     * @param password
     * @return if exists
     */
    public boolean checkLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public long getUserId(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getId();
            }
        }
        return 0;
    }

    public Room getRoom(long roomId) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public void createRoom(long idHost) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = "INSERT INTO room (`idHost`, `joinedUsers`) VALUES('" + idHost + "', '" + 1 + "')";
            statement.executeUpdate(query);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void joinRoom(long userId, long roomId) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = "UPDATE room set joinedUsers = joinedUsers + 1 where id = '" + roomId + "'";
            statement.executeUpdate(query);

            String query2 = "UPDATE user set idRoom ='" +  roomId + "'where id ='" + userId + "'";
            statement.executeUpdate(query2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
