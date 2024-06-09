package com.fithub.services.chat.api;

import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;

public interface ChatroomService {

    ChatroomDataResponse getChatroomData() throws ApiException;

    ChatroomResponse getChatroomDataByParticipiantUsername(final String username) throws ApiException;

}