package com.example.laborator11;

public class User {
    private String username;

    private int threadID = 0;

    public User(String username, int threadID) {
        this.username = username;
        this.threadID = threadID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }
}
