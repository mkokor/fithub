package com.fithub.services.membership.test.suites;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.core.impl.MembershipServiceImpl;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.mapper.PaymentRecordMapper;
import com.fithub.services.membership.test.configuration.BasicTestConfiguration;

public class MembershipServiceTest extends BasicTestConfiguration {

    private MembershipService membershipService;

    private MembershipRepository membershipRepository;

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @BeforeMethod
    public void beforeMethod() {

        membershipRepository = Mockito.mock(MembershipRepository.class);

        membershipService = new MembershipServiceImpl(membershipRepository, paymentRecordMapper);
    }

    @Test
    public void testGetPaymentRecord_ValidPaymentRecordIsProvided_ReturnPaymentRecord() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setFirstName("Joe");
            userEntity.setLastName("Doe");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            userEntity.setCoach(coachEntity);

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setCoach(coachEntity);
            clientEntity.setUser(userEntity);

            MembershipEntity membershipEntity = new MembershipEntity();
            membershipEntity.setId(1L);
            membershipEntity.setAmount(1000);
            membershipEntity.setClient(clientEntity);

            PaymentRecordEntity paymentRecordEntity = new PaymentRecordEntity();
            paymentRecordEntity.setId(1L);
            paymentRecordEntity.setPaid(false);
            paymentRecordEntity.setMembership(membershipEntity);

            List<PaymentRecordEntity> paymentRecordEntities = new ArrayList<>();
            paymentRecordEntities.add(paymentRecordEntity);
            membershipEntity.setPaymentRecord(paymentRecordEntities);

            List<PaymentRecordResponse> expectedResponse = new ArrayList<>();
            PaymentRecordResponse paymentRecordResponse = new PaymentRecordResponse();
            paymentRecordResponse.setId(1L);
            paymentRecordResponse.setMembershipId(1L);
            expectedResponse.add(paymentRecordResponse);

            Mockito.when(membershipRepository.findById(membershipEntity.getId())).thenReturn(Optional.of(membershipEntity));

            List<PaymentRecordResponse> actualResponse = membershipService.getPaymentRecord(paymentRecordResponse.getId());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);

        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
