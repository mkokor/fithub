package com.fithub.services.chat.api;

import java.util.List;

import com.fithub.services.chat.api.model.user.UserResponse;

public interface UserService {

    List<UserResponse> getChatroomParticipants(Long chatroomId) throws Exception;

}