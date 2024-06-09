package com.fithub.services.chat.rest.message;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "message", description = "Message API")
@RestController
@RequestMapping(value = "message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send a message to chatroom")
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageSendRequest messageSendRequest) throws Exception {
        return new ResponseEntity<>(messageService.sendMessage(messageSendRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get all messages")
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAllMessages(
            @RequestParam(name = "page_number", required = false, defaultValue = "0") final Integer pageNumber,
            @RequestParam(name = "page_size", required = false) Integer pageSize) {
        if (Objects.isNull(pageSize)) {
            pageSize = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("created"));

        return new ResponseEntity<>(messageService.getMessages(pageable), HttpStatus.OK);
    }

}