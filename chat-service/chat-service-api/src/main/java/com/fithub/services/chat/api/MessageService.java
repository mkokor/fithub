package com.fithub.services.chat.api;

import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;

public interface MessageService {

    MessageResponse sendMessage(MessageSendRequest messageSendRequest, Long chatroomId) throws Exception;

}