package model;

public class Room {

    private long id;
    private int joinedUsers;
    private long idHost;

    public Room() {}

    public Room(long id, long idHost) {
        this.id = id;
        this.idHost = idHost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(int joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public long getIdHost() {
        return idHost;
    }

    public void setIdHost(long idHost) {
        this.idHost = idHost;
    }
}

