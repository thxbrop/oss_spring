package com.thxbrop.oss_spring.controller;

import com.thxbrop.oss_spring.Result;
import com.thxbrop.oss_spring.entity.User;
import com.thxbrop.oss_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
public class UserController {
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user/{id}")
    public Result<User> getById(@PathVariable int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) return new Result<>("User is not exist", 1002);
        return new Result<>(user);
    }

    @GetMapping("/user/register")
    public Result<User> register(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam @Nullable String username
    ) {
        boolean isEmailExist = stream().anyMatch(user -> user.email.equals(email));
        if (isEmailExist) return new Result<>("The email (" + email + " ) has been registered", 1003);
        User user = new User();
        user.email = email;
        user.password = password;
        user.username = Objects.requireNonNullElseGet(username, () -> "User_" + System.currentTimeMillis());
        repository.save(user);
        return new Result<>(user);
    }

    @GetMapping("/user/login")
    public Result<User> login(@RequestParam String email, @RequestParam String password) {
        boolean isUserApproved = stream().anyMatch(user -> user.email.equals(email) && user.password.equals(password));
        if (!isUserApproved) return new Result<>("Email is not registered or password is incorrect", 1004);
        User res = stream().filter(user -> user.email.equals(email)).findFirst().orElse(null);
        return new Result<>(res);
    }

    private Stream<User> stream() {
        return ((List<User>) repository.findAll()).stream();
    }
}
