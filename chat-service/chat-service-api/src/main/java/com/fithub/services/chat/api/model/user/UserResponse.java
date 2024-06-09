package com.fithub.services.chat.api.model.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a chatroom participant object.")
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String username;

}