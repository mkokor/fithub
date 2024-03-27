package com.fithub.services.chat.api;

import java.util.List;

import com.fithub.services.chat.api.model.message.MessageResponse;

public interface ChatroomService {

    List<MessageResponse> getMessages(Long chatroomId) throws Exception;

}