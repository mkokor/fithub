package com.fithub.services.chat.api.model.chatroom;

import java.io.Serializable;
import java.util.List;

import com.fithub.services.chat.api.model.user.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a chatroom data response object.")
public class ChatroomDataResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    ChatroomResponse chatroomDetails;
    List<UserResponse> participants;

}