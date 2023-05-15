package com.example.laborator11.controllers;

import com.example.laborator11.User;
import com.example.laborator11.UsersDB;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> getListOfUsers(){
        return UsersDB.userList;
    }
}
