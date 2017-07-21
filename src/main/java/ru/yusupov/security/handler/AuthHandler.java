package ru.yusupov.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.yusupov.security.auth.TokenAuthentication;
import ru.yusupov.service.ChatService;
import ru.yusupov.service.SessionsService;

import java.io.IOException;

import static ru.yusupov.security.utils.TokenUtils.getTokenFromWebSocketsMessage;

@Component
public class AuthHandler extends TextWebSocketHandler {

    @Autowired
    private SessionsService sessionsService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private AuthenticationManager manager;

    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        String token = getTokenFromWebSocketsMessage(textMessage);
        manager.authenticate(new TokenAuthentication(token));
        int chatId = Integer.parseInt(textMessage.getPayload().split(" ")[1]);
        if (!chatService.isUserInChat(token, chatId)) {
            session.close();
        } else {
            sessionsService.submitSession(chatId, session);
        }
    }
}