package model;

import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private int joinedUsers;
    private long idHost;

    public Room() {}

    public Room(long id, long idHost, int joinedUsers) {
        this.id = id;
        this.idHost = idHost;
        this.joinedUsers = joinedUsers;
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

