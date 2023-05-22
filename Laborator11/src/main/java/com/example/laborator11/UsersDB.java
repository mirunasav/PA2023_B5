package com.example.laborator11;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class UsersDB {
    public static List<User> userList = new ArrayList<>();

    static {
        userList.add(new User("user1", 1));
        userList.add(new User("user2", 2));
        userList.add(new User("user3", 3));
    }

    public static List<User> getAll() {
        return userList;
    }

    public static User save(User user) {
        userList.add(user);
        return user;
    }

    public static List<User> delete(String username) {
        userList.removeIf(object -> Objects.equals(object.getUsername(), username));
        return userList;
    }

    public static void changeUsername(String oldUsername, String newUsername) {
        boolean userFound = userList.stream()
                .filter(user -> user.getUsername().equals(oldUsername))
                .peek(user -> user.setUsername(newUsername))
                .findFirst()
                .isPresent();
        if (!userFound) {
            throw new IllegalArgumentException("User with old username not found");
        }

    }
}
