package com.fithub.services.chat.api.websocket;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;
    private MessageType type;

    @NotBlank(message = "The sender username must not be null.")
    private String senderUsername;

}