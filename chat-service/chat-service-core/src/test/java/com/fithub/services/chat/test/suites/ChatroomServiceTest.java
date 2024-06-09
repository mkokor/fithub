package com.fithub.services.chat.test.suites;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.core.impl.ChatroomServiceImpl;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;

public class ChatroomServiceTest extends BasicTestConfiguration {

    @Autowired
    private ChatroomMapper chatroomMapper;
    @Autowired
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;

    private ChatroomService chatroomService;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);

        chatroomService = new ChatroomServiceImpl(chatroomRepository, chatroomMapper, userMapper);
    }

}