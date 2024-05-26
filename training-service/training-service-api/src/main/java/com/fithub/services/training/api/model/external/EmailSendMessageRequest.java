package com.fithub.services.training.api.model.external;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailSendMessageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "The receiver UUID address should not be null.")
    private String recipientUuid;

    @NotNull(message = "The subject should not be null.")
    private String subject;

    @NotNull(message = "The content should not be null.")
    private String content;

}