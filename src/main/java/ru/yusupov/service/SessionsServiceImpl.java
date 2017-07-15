package ru.yusupov.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class SessionsServiceImpl implements SessionsService {

    private Map<Integer, List<WebSocketSession>> sessions;

    public SessionsServiceImpl() {
            this.sessions = new HashMap<>();
    }

    @Override
    public void submitSession(int chatId, WebSocketSession session) {
        if (sessions.get(chatId) == null) {
            ArrayList<WebSocketSession> newChatSessions = new ArrayList<>();
            newChatSessions.add(session);
            sessions.put(chatId, newChatSessions);
        } else {
            List<WebSocketSession> chatSessions = sessions.get(chatId);
            chatSessions.add(session);
        }
    }

    @Override
    public List<WebSocketSession> getSessionsOfChat(int chatId) {
        return sessions.get(chatId);
    }
}
