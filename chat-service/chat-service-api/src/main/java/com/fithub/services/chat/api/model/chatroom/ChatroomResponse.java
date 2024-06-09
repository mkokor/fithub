package com.fithub.services.chat.api.model.chatroom;

import java.io.Serializable;

import com.fithub.services.chat.api.model.user.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a chatroom response object.")
public class ChatroomResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String roomName;
    private UserResponse admin;

}