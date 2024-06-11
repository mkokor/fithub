package com.fithub.services.membership.test.suites;

import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.membership.api.PaymentRecordService;
import com.fithub.services.membership.api.exception.UnauthorizedException;
import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.core.context.UserContext;
import com.fithub.services.membership.core.impl.PaymentRecordServiceImpl;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.mapper.PaymentRecordMapper;
import com.fithub.services.membership.test.configuration.BasicTestConfiguration;

import jakarta.validation.Validator;

public class PaymentRecordServiceTest extends BasicTestConfiguration {

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @Autowired
    private Validator validator;

    private UserRepository userRepository;
    private PaymentRecordRepository paymentRecordRepository;
    private PaymentRecordService paymentRecordService;

    @BeforeMethod
    public void beforeMethod() {
        userRepository = Mockito.mock(UserRepository.class);
        paymentRecordRepository = Mockito.mock(PaymentRecordRepository.class);

        paymentRecordService = new PaymentRecordServiceImpl(userRepository, paymentRecordRepository, paymentRecordMapper, validator);
    }

    private UserEntity constructCoachUser() {
        UserEntity authenticatedCoach = new UserEntity();
        authenticatedCoach.setUuid("john-doe-coach");
        authenticatedCoach.setFirstName("John");
        authenticatedCoach.setLastName("Doe");

        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setId(1L);
        coachEntity.setUser(authenticatedCoach);

        authenticatedCoach.setCoach(coachEntity);

        return authenticatedCoach;
    }

    private void authenticateCoach() {
        UserEntity authenticatedCoach = constructCoachUser();

        UserContext userContext = new UserContext();
        userContext.setUser(authenticatedCoach);

        UserContext.setCurrentContext(userContext);
    }

    @Test
    public void testGetAll_ValidClientIdIsProvided_ReturnPaymentRecords() {
        try {
            authenticateCoach();

            UserEntity coachUser = constructCoachUser();

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("michael-jordan-client");
            clientUser.setFirstName("Michael");
            clientUser.setLastName("Jordan");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(clientUser);
            clientEntity.setId(1L);
            clientEntity.setCoach(coachUser.getCoach());
            clientUser.setClient(clientEntity);

            MembershipEntity membershipEntity = new MembershipEntity();
            membershipEntity.setClient(clientEntity);
            membershipEntity.setId(1L);
            clientEntity.setMembership(membershipEntity);

            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setId(1L);
            paymentRecordEntity.setMembership(membershipEntity);
            paymentRecordEntity.setMonth("JANUARY");
            paymentRecordEntity.setYear(2024);
            paymentRecordEntity.setPaid(Boolean.FALSE);

            ClientResponse clientResponse = new ClientResponse();
            clientResponse.setUuid(clientUser.getUuid());
            clientResponse.setFirstName(clientUser.getFirstName());
            clientResponse.setLastName(clientUser.getLastName());

            PaymentRecordResponse paymentRecordResponse = new PaymentRecordResponse();
            paymentRecordResponse.setClient(clientResponse);
            paymentRecordResponse.setId(paymentRecordEntity.getId());
            paymentRecordResponse.setMonth(paymentRecordEntity.getMonth());
            paymentRecordResponse.setYear(paymentRecordEntity.getYear());
            paymentRecordResponse.setPaid(paymentRecordEntity.getPaid());

            Mockito.when(paymentRecordRepository.findByMembershipId(membershipEntity.getId())).thenReturn(List.of(paymentRecordEntity));
            Mockito.when(userRepository.findById(clientUser.getUuid())).thenReturn(Optional.of(clientUser));

            List<PaymentRecordResponse> paymentRecords = paymentRecordService.getAll(clientUser.getUuid());

            Assert.assertEquals(List.of(paymentRecordResponse), paymentRecords);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetAll_InvalidClientIdIsSent_ThrowsUnauthorizedException() {
        try {
            authenticateCoach();

            CoachEntity coachUser = new CoachEntity();
            coachUser.setId(10L);

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("michael-jordan-client");
            clientUser.setFirstName("Michael");
            clientUser.setLastName("Jordan");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(clientUser);
            clientEntity.setId(1L);
            clientEntity.setCoach(coachUser);
            clientUser.setClient(clientEntity);

            Mockito.when(userRepository.findById(clientUser.getUuid())).thenReturn(Optional.of(clientUser));

            Assert.assertThrows(UnauthorizedException.class, () -> paymentRecordService.getAll(clientEntity.getUser().getUuid()));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}