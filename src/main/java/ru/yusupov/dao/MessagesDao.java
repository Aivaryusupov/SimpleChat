package ru.yusupov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yusupov.models.Chat;
import ru.yusupov.models.Message;
import ru.yusupov.models.User;

import java.util.List;

public interface MessagesDao extends JpaRepository<Message, Integer> {
    List<Message> findByAuthorAndChat(User user, Chat chat);
}
