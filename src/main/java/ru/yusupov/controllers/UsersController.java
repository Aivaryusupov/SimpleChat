package ru.yusupov.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yusupov.dto.UserDataForRegistrationDto;
import ru.yusupov.dto.UserDto;
import ru.yusupov.models.User;
import ru.yusupov.service.UsersService;

import java.util.List;

@RestController
public class UsersController {

    final static Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDataForRegistrationDto user) {
        return new ResponseEntity<>(usersService.registerUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader("password") String password,
                                         @RequestHeader("login") String login) {
        logger.debug("This is debug");
        String token = usersService.login(password, login);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-Token", token);
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.ACCEPTED);
    }
}
