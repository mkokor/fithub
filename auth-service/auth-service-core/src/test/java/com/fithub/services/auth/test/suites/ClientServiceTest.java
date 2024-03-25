package com.fithub.services.auth.test.suites;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertThrows;

import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.auth.api.ClientService;
import com.fithub.services.auth.api.model.client.ClientResponse;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;
import com.fithub.services.auth.core.impl.ClientServiceImpl;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.ClientRepository;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;
import com.fithub.services.auth.mapper.ClientMapper;
import com.fithub.services.auth.test.configuration.BasicTestConfiguration;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class ClientServiceTest extends BasicTestConfiguration {

    @Autowired
    private ClientMapper clientMapper;

    private CoachRepository coachRepository;
    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private Validator validator;

    private ClientService clientService;

    @BeforeMethod
    public void beforeMethod() {
        coachRepository = Mockito.mock(CoachRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        clientService = new ClientServiceImpl(coachRepository, clientRepository, userRepository, clientMapper, validator);
    }

    @Test
    public void testSignUp_InvalidEmailProvided_ThrowsMethodArgumentNotValidException() {
        try {
            ClientSignUpRequest clientSignUpRequest = new ClientSignUpRequest();
            clientSignUpRequest.setFirstName("John");
            clientSignUpRequest.setFirstName("Doe");
            clientSignUpRequest.setUsername("johndoe");
            clientSignUpRequest.setEmail("invalid");
            clientSignUpRequest.setCoachId(1L);
            clientSignUpRequest.setPassword("password123");

            assertThrows(ConstraintViolationException.class, () -> {
                clientService.signUp(clientSignUpRequest);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testSignUp_ValidDataProvided_ReturnsSignedUpClient() {
        try {
            UserEntity coachUser = new UserEntity();
            coachUser.setEmail("maryann@email.com");
            coachUser.setFirstName("Mary");
            coachUser.setLastName("Ann");
            coachUser.setPasswordHash("password123");
            coachUser.setUsername("maryann");
            coachUser.setUuid("coach-id");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(coachUser);

            ClientSignUpRequest clientSignUpRequest = new ClientSignUpRequest();
            clientSignUpRequest.setFirstName("John");
            clientSignUpRequest.setLastName("Doe");
            clientSignUpRequest.setUsername("johndoe");
            clientSignUpRequest.setEmail("johndoe@email.com");
            clientSignUpRequest.setCoachId(1L);
            clientSignUpRequest.setPassword("password123");

            ClientResponse expectedResponse = new ClientResponse();
            expectedResponse.setUsername("johndoe");
            expectedResponse.setEmail("johndoe@email.com");
            expectedResponse.setLastName("Doe");
            expectedResponse.setFirstName("John");
            expectedResponse.setCoachId(1L);

            Mockito.when(coachRepository.findById(clientSignUpRequest.getCoachId())).thenReturn(Optional.of(coachEntity));

            ClientResponse actualResponse = clientService.signUp(clientSignUpRequest);

            assertThat(actualResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}