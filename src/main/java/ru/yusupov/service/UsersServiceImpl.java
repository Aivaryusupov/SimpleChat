package ru.yusupov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yusupov.dao.UsersDao;
import ru.yusupov.dto.UserDataForRegistrationDto;
import ru.yusupov.dto.UserDto;
import ru.yusupov.models.User;
import ru.yusupov.security.utils.TokenGenerator;

import java.util.ArrayList;
import java.util.List;

import static ru.yusupov.converter.Converter.convert;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private TokenGenerator generator;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDto registerUser(UserDataForRegistrationDto user) {
        User newUser = convert(user,
                                dto -> new User(dto.getLogin(),
                                encoder.encode(dto.getPassword()),
                                dto.getAge(),
                                dto.getName())
                            );
        User savedUser = usersDao.save(newUser);
        return convert(savedUser,
                model -> new UserDto(model.getId(),
                        model.getAge(),
                        model.getName()));
    }

    @Override
    @Transactional
    public String login(String password, String login) {
        User registeredUser = usersDao.findByLogin(login);
        if (encoder.matches(password, registeredUser.getHashPassword())) {
            String token = generator.generateToken();
            usersDao.updateToken(registeredUser.getId(), token);
            return token;
        } else throw new IllegalArgumentException("Incorrect username or password");
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersDao.findAll().forEach(users::add);
        return users;
    }
}
