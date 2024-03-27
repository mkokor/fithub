package com.fithub.services.chat.api.model.message;

import java.io.Serializable;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of a message send request object")
public class MessageSendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The user id of the user who sent the message")
    @NotBlank(message = "The user id must not be blank.")
    private String userId;

    @Schema(description = "The content of the message")
    @NotBlank(message = "The content of the message must not be blank.")
    private String content;

    @Schema(description = "The time of message creation")
    private LocalDateTime created;

}