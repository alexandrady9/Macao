package connection;

import model.Room;
import model.User;

class Queries {

    static String getRooms = "SELECT * from room";

    static String getUsers = "SELECT * from user";

    static String insertRoom(long idHost) {
        return "INSERT INTO room (`idHost`, `joinedUsers`) VALUES('" + idHost + "', '" + 1 + "')";
    }

    static String updateUserWhenCreateRoom(long idHost, Room room) {
        return "UPDATE user set idRoom ='" +  room.getId() + "'where id ='" + idHost + "'";
    }

    static String updateJoinedUsersFromRoom(long roomId) {
        return "UPDATE room set joinedUsers = joinedUsers + 1 where id = '" + roomId + "'";
    }

    static String updateUserWhenJoinToRoom(long roomId, long userId) {
        return "UPDATE user set idRoom ='" +  roomId + "'where id ='" + userId + "'";
    }

    static String updateUserWhenFinishGame(User joinedUser) {
        return "UPDATE user set idRoom = null where id ='" + joinedUser.getId() + "'";
    }

    static String deleteRoom(long roomId) {
        return  "DELETE from room where id = '" + roomId + "'";
    }
}
