package com.example.laborator11.controllers;

import com.example.laborator11.User;
import com.example.laborator11.UsersDB;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> getListOfUsers(){
        return UsersDB.userList;
    }
    
    @PostMapping(path = "/users")
     public User createUser(@RequestBody User user)  {
       return UsersDB.save(user);
    }

    @DeleteMapping(path = "/users/{username}")
    @ResponseBody
    public List<User> deleteUser (@PathVariable String username)
    {
        return UsersDB.delete(username);
    }

    @PutMapping(path = "/users/change/{oldUsername}")
    public ResponseEntity<String> changeUsername(
            @PathVariable String oldUsername,
            @RequestParam String newUsername) {
       try{
           UsersDB.changeUsername(oldUsername, newUsername);
       }
       catch(Exception ex){
           return (ResponseEntity<String>) ResponseEntity.badRequest();
       }
        return ResponseEntity.ok("username update successfully");
    }
}
