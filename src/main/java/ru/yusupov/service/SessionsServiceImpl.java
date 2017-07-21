package ru.yusupov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.yusupov.dto.MessageDto;

import java.io.IOException;
import java.util.*;

@Service
public class SessionsServiceImpl implements SessionsService {

    private Map<Integer, List<WebSocketSession>> sessions;
    private ObjectMapper mapper = new ObjectMapper();

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
    public void sendToSessions(MessageDto message, int chatId) {
        List<WebSocketSession> sessions = getSessionsOfChat(chatId);
        if (sessions != null && sessions.size() != 0) {
            for (WebSocketSession session : sessions) {
                try {
                    if (session.isOpen()) {
                        String json = mapper.writeValueAsString(message);
                        session.sendMessage(new TextMessage(json.getBytes()));
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public List<WebSocketSession> getSessionsOfChat(int chatId) {
        return sessions.get(chatId);
    }
}
