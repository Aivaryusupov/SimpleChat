package ru.yusupov.service;

import ru.yusupov.dto.UserDataForRegistrationDto;
import ru.yusupov.dto.UserDto;
import ru.yusupov.models.User;

import java.util.List;

public interface UsersService {
    UserDto registerUser(UserDataForRegistrationDto user);
    String login(String password, String login);
    List<User> getUsers();
}
