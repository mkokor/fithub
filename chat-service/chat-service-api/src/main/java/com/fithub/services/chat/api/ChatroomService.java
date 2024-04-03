package com.fithub.services.chat.api;

import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.NewChatroomRequest;

public interface ChatroomService {
    
    ChatroomDataResponse getChatroomData(String chatroomId) throws Exception;

	ChatroomDataResponse createNewChatroom(NewChatroomRequest newChatroomRequest) throws Exception;

}