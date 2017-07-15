package ru.yusupov.service;

import ru.yusupov.dto.ChatDto;
import ru.yusupov.dto.MessageDto;

import java.util.List;


public interface ChatService {
    List<MessageDto> getMessages(String token, int chatId);

    void saveAndDeliverMessage(String token, int chatId, MessageDto message);

    boolean isUserInChat(String token, int chatId);

    void addChat(ChatDto chat, String token);

    List<ChatDto> getChats();
}
