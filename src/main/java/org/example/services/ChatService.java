package org.example.services;

import org.example.models.entities.MessageEntity;
import org.example.models.entities.UserEntity;

public class ChatService {
//    join(UserEntity user);
//     -           leave(Long userId);
//     -           send(Message msg);
//     -           readAll();

    public void join(UserEntity user) {
        user.withPasscode(user.getPasscode());
        System.out.println("User joined to chat");
        }

       


    public void send(MessageEntity msg) {
        msg.withId(msg.getId());
        msg.withSubject(msg.getSubject());
        msg.withBody(msg.getBody());
    }

    public void readAll(){
        MessageEntity msg = new MessageEntity();
        msg.getSenderId();
        msg.getSubject();
        msg.getBody();
    }

}
