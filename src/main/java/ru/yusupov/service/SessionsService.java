package ru.yusupov.service;

import org.springframework.web.socket.WebSocketSession;
import ru.yusupov.dto.MessageDto;

public interface SessionsService {
    void submitSession(int chatId, WebSocketSession session);

    void sendToSessions(MessageDto message, int chatId);
}
