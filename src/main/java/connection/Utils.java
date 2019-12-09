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
    private List<User> users = new ArrayList<>();
    public List<Room> rooms = new ArrayList<>();

    public Utils() {
        getUsers();
        getRooms();
    }

    public List<User> getUsers() {
        try {
            users.clear();
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from user";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                int idRoom = rs.getInt("idRoom");
                User user = new User(id, name, password, idRoom);
                users.add(user);
            }
            rs.close();

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Room> getRooms() {
        try {
            rooms.clear();
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from room";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idHost = rs.getInt("idHost");
                int joinedUsers = rs.getInt("joinedUsers");
                Room room = new Room(id, idHost, joinedUsers);
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
     * @return user
     */
    public User checkLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public Room getRoom(long roomId) {
        return rooms
                .stream()
                .filter(room -> room.getId() == roomId)
                .findFirst()
                .orElse(null);
    }

    public void createRoom(long idHost) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = "INSERT INTO room (`idHost`, `joinedUsers`) VALUES('" + idHost + "', '" + 1 + "')";
            statement.executeUpdate(query);
            /// TODO: 12/9/2019 sa se adauge si in tabela de user, id-ul camerei
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room getLastRoomCreated() {
        getRooms();
        return rooms.get(rooms.size() - 1);
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

    public void deletedRoom(long roomId, List<User> joinedUsers) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();

            joinedUsers.forEach(joinedUser -> {
                String query2 = "UPDATE user set idRoom = null where id ='" + joinedUser.getId() + "'";
                try {
                    statement.executeUpdate(query2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });

            String query = "DELETE from room where id = '" + roomId + "'";
            statement.executeUpdate(query);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
