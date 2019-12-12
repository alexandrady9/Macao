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
    private List<Room> rooms = new ArrayList<>();

    public Utils() {
        getUsers();
        getRooms();
    }

    /**
     * get all users from the database
     * @return List of users
     */
    public List<User> getUsers() {
        try {
            users.clear();
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = Queries.getUsers;

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                int idRoom = rs.getInt("idRoom");
                int wonGames = rs.getInt("wonGames");
                User user = new User(id, name, password, idRoom, wonGames);
                users.add(user);
            }
            rs.close();

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all rooms from the database
     * @return list of rooms
     */
    public List<Room> getRooms() {
        try {
            rooms.clear();
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = Queries.getRooms;

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

    /**
     * get room with id = roomId
     * @param roomId
     * @return room
     */
    public Room getRoom(long roomId) {
        return rooms
                .stream()
                .filter(room -> room.getId() == roomId)
                .findFirst()
                .orElse(null);
    }

    /**
     * create a new room in the database for a specified user
     * @param idHost
     */
    public void createRoom(long idHost) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = Queries.insertRoom(idHost);
            statement.executeUpdate(query);

            Room room = getLastRoomCreated();
            String query2 = Queries.updateUserWhenCreateRoom(idHost, room);
            statement.executeUpdate(query2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the last room that was created
     * @return room
     */
    public Room getLastRoomCreated() {
        getRooms();
        return rooms.get(rooms.size() - 1);
    }

    /**
     * modify user and room tables when a given user join a room
     * @param userId
     * @param roomId
     */
    public void joinRoom(long userId, long roomId) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = Queries.updateJoinedUsersFromRoom(roomId);
            statement.executeUpdate(query);

            String query2 = Queries.updateUserWhenJoinToRoom(roomId, userId);
            statement.executeUpdate(query2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete the specified room
     * @param roomId
     */
    public void deleteRoom(long roomId) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = Queries.deleteRoom(roomId);
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete the connections between it and the user who want to leave
     * @param roomId
     * @param userId
     */
    public void removeUserFromRoom(long roomId, long userId) {
        try {
            Statement statement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String query = Queries.updateRoomWhenUserLeave(roomId);
            statement.executeUpdate(query);

            String query2 = Queries.deleteRoomFromUser(userId);
            statement.executeUpdate(query2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
