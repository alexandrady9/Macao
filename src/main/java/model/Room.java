package model;

public class Room {

    private long id;
    private int joinedUsers;
    private int hostedBy;

    public Room() {}

    public Room(long id, int joinedUsers, int hostedBy) {
        this.id = id;
        this.joinedUsers = joinedUsers;
        this.hostedBy = hostedBy;
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

    public int getHostedBy() {
        return hostedBy;
    }

    public void setHostedBy(int hostedBy) {
        this.hostedBy = hostedBy;
    }
}

