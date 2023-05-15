package com.example.laborator11;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersDB {
    public static List<User> userList = new ArrayList<>();

    static{
        userList.add(new User("user1", 1));
        userList.add(new User("user2", 2));
        userList.add(new User("user3", 3));
    }

    public static List<User> getAll (){
        return userList;
    }
}
