package org.example;

import org.example.models.entities.UserEntity;
import org.example.services.ChatService;

public class Main {
    public static void main(String[] args) {
        UserEntity user1 = new UserEntity();
        ChatService chatService = new ChatService();
        chatService.join(user1);

//        chatService.send("Hello", "World")
        chatService.readAll();
    }
}