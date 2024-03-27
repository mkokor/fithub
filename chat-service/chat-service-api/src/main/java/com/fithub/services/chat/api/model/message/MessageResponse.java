package com.fithub.services.chat.api.model.message;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a message response object")
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long chatroomId;

    private String username;
    
    private String content;
    
    private LocalDateTime created;

}