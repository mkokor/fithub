package com.fithub.services.chat.rest.chatroom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.NewChatroomRequest;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "chatroom", description = "Chatroom API")
@RestController
@RequestMapping(value = "chatroom")
@AllArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    private final MessageService messageService;
    
    @Operation(summary = "Get chatroom data")
    @GetMapping(value = "/{id}/data")
    public ResponseEntity<ChatroomDataResponse> getChatroomData(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(chatroomService.getChatroomData(id), HttpStatus.OK);
    }
    
    @Operation(summary = "Send a message to chatroom")
    @PostMapping(value = "/{id}/send-message")
    public ResponseEntity<MessageResponse> signUp(@PathVariable Long id, @Valid @RequestBody MessageSendRequest messageSendRequest) throws Exception {
        return new ResponseEntity<>(messageService.sendMessage(messageSendRequest, id), HttpStatus.OK);
    }
    
    @Operation(summary = "Create new chatroom")
    @PostMapping(value = "/create-chatroom")
    public ResponseEntity<ChatroomDataResponse> createNewChatroom(@Valid @RequestBody NewChatroomRequest newChatroomRequest) throws Exception {
    	return new ResponseEntity<>(chatroomService.createNewChatroom(newChatroomRequest), HttpStatus.OK);
    }
}