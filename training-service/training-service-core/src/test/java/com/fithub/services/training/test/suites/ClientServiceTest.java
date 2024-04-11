package com.fithub.services.training.test.suites;

import static org.testng.Assert.assertThrows;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.training.api.ClientService;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.membership.ClientMembershipResponse;
import com.fithub.services.training.api.membership.MembershipPaymentReportResponse;
import com.fithub.services.training.api.model.client.CoachChangeRequest;
import com.fithub.services.training.api.model.client.CoachChangeResponse;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.core.impl.ClientServiceImpl;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.ClientMapper;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

import jakarta.validation.Validator;

public class ClientServiceTest extends BasicTestConfiguration {

    @Autowired
    private ClientMapper clientMapper;

    private UserRepository userRepository;
    private MembershipServiceClient membershipServiceClient;

    private ClientService clientService;

    private Validator validator;

    @BeforeMethod
    public void beforeMethod() {
        userRepository = Mockito.mock(UserRepository.class);
        membershipServiceClient = Mockito.mock(MembershipServiceClient.class);

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        clientService = new ClientServiceImpl(membershipServiceClient, userRepository, clientMapper, validator);

    }

    @Test
    public void testChangeCoach_ClientHasUnresolvedDebt_ThrowsAnBadRequestException() {
        try {
            UserEntity clientUserEntity = new UserEntity();
            clientUserEntity.setUuid("mary-ann-client");

            CoachChangeRequest coachChangeRequest = new CoachChangeRequest();
            coachChangeRequest.setCoachUuid("test");

            ClientMembershipResponse clientMembershipResponse = new ClientMembershipResponse();
            clientMembershipResponse.setUuid("mary-ann-client");

            MembershipPaymentReportResponse membershipPaymentReportResponse = new MembershipPaymentReportResponse();
            membershipPaymentReportResponse.setClient(clientMembershipResponse);
            membershipPaymentReportResponse.setHasDebt(true);

            Mockito.when(userRepository.findById("mary-ann-client")).thenReturn(Optional.of(clientUserEntity));
            Mockito.when(membershipServiceClient.getMembershipPaymentReport("mary-ann-client"))
                    .thenReturn(new ResponseEntity<>(membershipPaymentReportResponse, HttpStatus.OK));

            assertThrows(BadRequestException.class, () -> clientService.changeCoach(coachChangeRequest));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeCoach_ValidDataIsSent_ReturnsUpdatedState() {
        try {
            UserEntity clientUserEntity = new UserEntity();
            clientUserEntity.setUuid("mary-ann-client");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(clientUserEntity);
            clientUserEntity.setClient(clientEntity);

            UserEntity coachUserEntity = new UserEntity();
            coachUserEntity.setUuid("coach-test");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(coachUserEntity);
            coachUserEntity.setCoach(coachEntity);

            CoachChangeRequest coachChangeRequest = new CoachChangeRequest();
            coachChangeRequest.setCoachUuid("coach-test");

            ClientMembershipResponse clientMembershipResponse = new ClientMembershipResponse();
            clientMembershipResponse.setUuid("mary-ann-client");

            MembershipPaymentReportResponse membershipPaymentReportResponse = new MembershipPaymentReportResponse();
            membershipPaymentReportResponse.setClient(clientMembershipResponse);
            membershipPaymentReportResponse.setHasDebt(false);

            Mockito.when(userRepository.findById("mary-ann-client")).thenReturn(Optional.of(clientUserEntity));
            Mockito.when(userRepository.findById("coach-test")).thenReturn(Optional.of(coachUserEntity));
            Mockito.when(membershipServiceClient.getMembershipPaymentReport("mary-ann-client"))
                    .thenReturn(new ResponseEntity<>(membershipPaymentReportResponse, HttpStatus.OK));

            CoachChangeResponse expectedResponse = new CoachChangeResponse();
            expectedResponse.setClientUuid("mary-ann-client");
            expectedResponse.setNewCoachUuid("coach-test");

            CoachChangeResponse actualResponse = clientService.changeCoach(coachChangeRequest);

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}