package ru.yusupov.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.yusupov.service.ChatService;
import ru.yusupov.service.SessionsService;

import java.io.IOException;

public class AuthHandler extends TextWebSocketHandler {

    @Autowired
    private SessionsService sessionsService;

    @Autowired
    private ChatService chatService;

    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        System.out.println(textMessage.getPayload());
        String message[] = textMessage.getPayload().split(" ");
        String token = message[0];
        int chatId = Integer.parseInt(message[1]);
        if (!chatService.isUserInChat(token, chatId)) {
            session.close();
        } else {
            sessionsService.submitSession(chatId, session);
        }
    }
}
