package com.zero.rbacservice.controller;

import com.zero.rbacservice.model.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<User>();
    }

    @GetMapping("/byId/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return new User();
    }


    @GetMapping("/byUsername/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return new User();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return new User();
    }

    @PatchMapping("/{id}/disable")
    public void disableUser(@PathVariable UUID id) {

    }

    @PatchMapping("/{id}/enable")
    public void enableUser(@PathVariable UUID id) {

    }

    @PostMapping("/authenticate")
    public User authenticate(@RequestBody User user) {
        return user;
    }

    


}
