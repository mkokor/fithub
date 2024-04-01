package com.fithub.services.mealplan.test.suites;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.mealplan.api.CoachService;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.core.impl.CoachServiceImpl;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.CoachRepository;
import com.fithub.services.mealplan.mapper.UserMapper;
import com.fithub.services.mealplan.test.configuration.BasicTestConfiguration;


public class CoachServiceTest extends BasicTestConfiguration {

    @Autowired
    private UserMapper userMapper;

    private CoachRepository coachRepository;
    
    private CoachService coachService;


    @BeforeMethod
    public void beforeMethod() {
        coachRepository = Mockito.mock(CoachRepository.class);

        coachService = new CoachServiceImpl(coachRepository, userMapper);
    }

    @Test
    public void testGetCoachNameAndLastName_ValidUserIdIsProvided_ReturnsNameAndLastName() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            userEntity.setCoach(coachEntity);

            UserResponse expectedResponse = new UserResponse();
            expectedResponse.setFirstName("John");
            expectedResponse.setLastName("Doe");
            

            Mockito.when(coachRepository.findByUserUuid(userEntity.getUuid())).thenReturn(Optional.of(coachEntity));

            UserResponse actualResponse = coachService.getCoachNameAndLastName(coachEntity.getUser().getUuid());
            		
            

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
