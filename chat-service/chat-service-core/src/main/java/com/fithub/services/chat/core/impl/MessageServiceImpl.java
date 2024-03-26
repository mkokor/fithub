package com.fithub.services.chat.core.impl;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Override
    public String getAll() {
        return "Succes";
    }

}