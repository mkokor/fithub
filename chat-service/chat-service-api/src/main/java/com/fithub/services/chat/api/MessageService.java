package com.fithub.services.chat.api;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;

public interface MessageService {

    MessageResponse sendMessage(MessageSendRequest messageSendRequest) throws Exception;

    MessageResponse sendMessageUnauthenticated(MessageSendRequest messageSendRequest) throws Exception;

    List<MessageResponse> getMessages(Pageable pageable);

}