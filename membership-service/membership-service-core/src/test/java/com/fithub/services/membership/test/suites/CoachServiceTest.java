package com.fithub.services.membership.test.suites;

import org.testng.annotations.Test;

import com.fithub.services.membership.api.CoachService;
import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.core.impl.CoachServiceImpl;
import com.fithub.services.membership.core.impl.MembershipServiceImpl;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.CoachRepository;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.mapper.ClientMapper;
import com.fithub.services.membership.test.configuration.BasicTestConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class CoachServiceTest extends BasicTestConfiguration {
   
  private CoachService coachService;
	  
  private CoachRepository coachRepository;
	  
  @Autowired
  private ClientMapper clientMapper;
  
  @BeforeMethod
  public void beforeMethod() {
	  coachRepository = Mockito.mock(CoachRepository.class);
	  
	  coachService = new CoachServiceImpl(coachRepository, clientMapper);
  }
  
  @Test
  public void testGetClients_ValidClientIdIsProvided_ReturnClients() {
	  
	  
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
		  
		  List<ClientEntity> clientEntities = new ArrayList<>();
		  clientEntities.add(clientEntity);
		  coachEntity.setUser(userEntity);
		  coachEntity.setClients(clientEntities);
		  
		  List<ClientResponse> expectedResponse = new ArrayList<>();
		  ClientResponse clientResponse = new ClientResponse();
		  clientResponse.setId(1L);
		  clientResponse.setCoachId(1L);
		  expectedResponse.add(clientResponse);
		  
		  Mockito.when(coachRepository.findById(coachEntity.getId())).thenReturn(Optional.of(coachEntity));
		  
		  List<ClientResponse> actualResponse = coachService.getClients(clientResponse.getId());
		  
		  Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
		  
	} catch (Exception exception) {
		// TODO Auto-generated catch block
	    Assert.fail();
	}
	  
	  
	  
	  
  }

}
