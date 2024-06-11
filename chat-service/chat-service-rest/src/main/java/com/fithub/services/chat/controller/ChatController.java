package com.fithub.services.chat.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;
import com.fithub.services.chat.api.websocket.Message;
import com.fithub.services.chat.api.websocket.MessageType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatroomService chatroomService;
    private final MessageService messageService;

    private String constructDestinationUri(final String username) throws ApiException {
        final ChatroomResponse chatroomResponse = chatroomService.getChatroomDataByParticipiantUsername(username);
        return "/chatroom/" + chatroomResponse.getId();
    }

    private void storeMessage(final Message message) throws Exception {
        if (Objects.isNull(message.getType())
                || !(message.getType().equals(MessageType.MESSAGE) && Objects.nonNull(message.getContent()))) {
            return;
        }

        MessageSendRequest messageSendRequest = new MessageSendRequest();
        messageSendRequest.setContent(message.getContent());
        messageSendRequest.setUsername(message.getSenderUsername());

        messageService.sendMessageUnauthenticated(messageSendRequest);
    }

    @MessageMapping("/message")
    public Message sendMessage(@Payload final Message message) throws Exception {
        storeMessage(message);
        message.setCreated(LocalDateTime.now());

        final String destination = constructDestinationUri(message.getSenderUsername());
        simpMessagingTemplate.convertAndSend(destination, message);

        return message;
    }

}