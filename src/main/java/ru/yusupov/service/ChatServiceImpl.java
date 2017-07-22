package ru.yusupov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.yusupov.dao.ChatsDao;
import ru.yusupov.dao.MessagesDao;
import ru.yusupov.dao.UsersDao;
import ru.yusupov.dto.ChatDto;
import ru.yusupov.dto.MessageDto;
import ru.yusupov.models.Chat;
import ru.yusupov.models.Message;
import ru.yusupov.models.User;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private MessagesDao messagesDao;

    @Autowired
    private ChatsDao chatsDao;

    @Autowired
    private SessionsService sessionsService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<MessageDto> getMessages(String token, int chatId) {
        Chat chat = chatsDao.findOne(chatId);
        List<Message> messages = messagesDao.findByChat(chat);
        List<MessageDto> result = messages.
                stream().map(message ->
                new MessageDto(message.getChat().getId(), usersDao.findById(message.getAuthor().getId()).getName(),
                        message.getText())).collect(Collectors.toList());
        return result;
    }

    @Override
    public void saveAndDeliverMessage(String token, int chatId, MessageDto message) {
        if (isUserInChat(token, chatId)) {
            User user = usersDao.findByToken(token);
            Chat chat = chatsDao.findOne(chatId);
            Message model = new Message();
            model.setAuthor(user);
            model.setChat(chat);
            model.setText(message.getMessage());
            messagesDao.save(model);
            message.setFrom(user.getName());
            messagingTemplate.convertAndSend("/topic/chats/" + chatId, message);
            sessionsService.sendToSessions(message, chatId);
        }
    }

    @Override
    public boolean isUserInChat(String token, int chatId) {
        User user = usersDao.findByToken(token);
        return chatsDao.isUserInChat(user.getId(), chatId);
    }

    @Override
    public void addChat(ChatDto chat, String token) {
        User user = usersDao.findByToken(token);
        Chat model = new Chat(user, null, chat.getName(), null);
        chatsDao.save(model);
    }

    @Override
    public List<ChatDto> getChats() {
        List<Chat> chats = chatsDao.findAll();
        return chats.
                stream().map(chat ->
                new ChatDto(chat.getId(), chat.getName(), chat.getCreator().getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void enterChat(String token, int chatId) {
        Chat chat = chatsDao.findOne(chatId);
        User user = usersDao.findByToken(token);
        Optional<User> foundUser = chat.getUsers()
                .stream()
                .filter(chatUser -> chatUser.getId() == user.getId())
                .findFirst();
        if (!foundUser.isPresent()) {
            user.getChats().add(chat);
            usersDao.save(user);
        }
    }
}
