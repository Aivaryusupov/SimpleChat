package ru.yusupov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yusupov.models.Chat;

public interface ChatsDao extends JpaRepository<Chat, Integer> {

    @Query(value = "SELECT COUNT (*) > 0 FROM chat_user WHERE user_id = ?1 " +
            "AND chat_id = ?2", nativeQuery = true)
    boolean isUserInChat(int userId, int chatId);
}
