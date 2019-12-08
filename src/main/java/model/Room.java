package model;

public class Room {

    private long id;
    private int hostedBy;
    private int joinedUsers;

    public Room() {}

    public Room(long id, int hostedBy, int joinedUsers) {
        this.id = id;
        this.hostedBy = hostedBy;
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

    public int getHostedBy() {
        return hostedBy;
    }

    public void setHostedBy(int hostedBy) {
        this.hostedBy = hostedBy;
    }
}

