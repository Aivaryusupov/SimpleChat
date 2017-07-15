package ru.yusupov.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface SessionsService {
    void submitSession(int chatId, WebSocketSession session);

    List<WebSocketSession> getSessionsOfChat(int chatId);
}
