package com.shopme.be.service;

import com.shopme.be.persistant.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    public void sendPrivate(Message message){
        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getReceiver() + "/private",message);
    }
    public void sendPublic(Message message){
        simpMessagingTemplate.convertAndSend("/chatroom/public",message);
    }
}
