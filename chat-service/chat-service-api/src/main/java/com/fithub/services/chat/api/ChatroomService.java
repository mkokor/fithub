package com.fithub.services.chat.api;

import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;

public interface ChatroomService {

    ChatroomDataResponse getChatroomData() throws ApiException;

}