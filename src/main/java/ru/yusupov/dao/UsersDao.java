package ru.yusupov.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.yusupov.models.User;

public interface UsersDao extends CrudRepository<User, Integer> {
    User findByToken(String token);
    User findByLogin(String login);
    User findById(int id);

    @Modifying
    @Query("update User user set user.token = ?2 where user.id = ?1")
    void updateToken(int id, String token);

    @Query("select count(user) > 0 from User user where user.token = ?1")
    boolean isExistToken(String token);
}
