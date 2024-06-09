package com.fithub.services.chat.api.model.message;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of a message send request object.")
public class MessageSendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The content of the message")
    @NotBlank(message = "The content of the message must not be blank.")
    private String content;

}