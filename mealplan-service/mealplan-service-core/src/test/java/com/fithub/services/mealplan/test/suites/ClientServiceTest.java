package com.fithub.services.mealplan.test.suites;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.core.impl.ClientServiceImpl;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.mapper.MealPlanMapper;
import com.fithub.services.mealplan.test.configuration.BasicTestConfiguration;


public class ClientServiceTest extends BasicTestConfiguration {

    @Autowired
    private MealPlanMapper mealPlanMapper;

    private ClientRepository clientRepository;
    
    private ClientService clientService;


    @BeforeMethod
    public void beforeMethod() {
        clientRepository = Mockito.mock(ClientRepository.class);

        clientService = new ClientServiceImpl(clientRepository, mealPlanMapper);
    }

    @Test
    public void testGetMealPlan_ValidClientIdIsProvided_ReturnsMealPlan() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            userEntity.setCoach(coachEntity);

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(userEntity);
            clientEntity.setCoach(coachEntity);
            userEntity.setClient(clientEntity);
            List<ClientEntity> clients = new ArrayList<>();
            clients.add(clientEntity);
            coachEntity.setClients(clients);
            
            MealPlanEntity mealPlanEntity = new MealPlanEntity();
            mealPlanEntity.setId(1L);
            mealPlanEntity.setClient(clientEntity);
            mealPlanEntity.setModified(LocalDateTime.of(2023, 5, 15, 12, 30));
            mealPlanEntity.setModifiedBy(coachEntity);
            clientEntity.setMealPlan(mealPlanEntity);
            List<MealPlanEntity> mealPlans = new ArrayList();
            mealPlans.add(mealPlanEntity);
            coachEntity.setMealPlans(mealPlans);
            
            MealPlanResponse expectedResponse = new MealPlanResponse();
            expectedResponse.setId(1L);
            expectedResponse.setClientId(1L);
            expectedResponse.setModifiedBy(coachEntity.getId());
            expectedResponse.setModified(LocalDateTime.of(2024, 3, 14, 19, 13));

            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));

            MealPlanResponse actualResponse = clientService.getMealPlan(clientEntity.getId());
            

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
