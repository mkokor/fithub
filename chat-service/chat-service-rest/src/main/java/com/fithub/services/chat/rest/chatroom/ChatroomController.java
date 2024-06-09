package com.fithub.services.chat.rest.chatroom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "chatroom", description = "Chatroom API")
@RestController
@RequestMapping(value = "chatroom")
@AllArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;

    @Operation(summary = "Get chatroom data")
    @GetMapping(value = "/data")
    public ResponseEntity<ChatroomDataResponse> getChatroomData() throws ApiException {
        return new ResponseEntity<>(chatroomService.getChatroomData(), HttpStatus.OK);
    }

}