package com.fithub.services.chat.api.model.chatroom;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "The properties of a chatroom response object")
public class NewChatroomRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The name of the chatroom")
    @Size(min = 2, message = "Chatroom name is too short.")
    @Size(max = 20, message = "Chatroom name is too long.")
    @NotBlank(message = "The chatroom name must not be blank.")
    private String roomName;
    
    @Schema(description = "The user id of the coach who is the admin")
    @NotNull(message = "The admin id must not be blank.")
    private Long adminId;

}
