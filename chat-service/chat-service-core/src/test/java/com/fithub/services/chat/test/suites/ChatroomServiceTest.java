package com.fithub.services.chat.test.suites;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.core.impl.ChatroomServiceImpl;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.CoachRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;

import jakarta.validation.Validator;

public class ChatroomServiceTest extends BasicTestConfiguration {

    @Autowired
    private MessageMapper messageMapper;
    private ChatroomMapper chatroomMapper;
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;
    private CoachRepository coachRepository;
    private UserRepository userRepository;

    private ChatroomService chatroomService;
    
    private Validator validator;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);
        
        
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        chatroomService = new ChatroomServiceImpl(chatroomRepository, coachRepository, userRepository, messageMapper, chatroomMapper, userMapper, validator);
    }

}
