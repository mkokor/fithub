package com.fithub.services.mealplan.test.suites;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.core.impl.ClientServiceImpl;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;
import com.fithub.services.mealplan.mapper.MealPlanMapper;
import com.fithub.services.mealplan.mapper.UserMapper;
import com.fithub.services.mealplan.test.configuration.BasicTestConfiguration;
import static org.mockito.ArgumentMatchers.any;



public class ClientServiceTest extends BasicTestConfiguration {

    @Autowired
    private MealPlanMapper mealPlanMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private DailyMealPlanMapper dailyMealPlanMapper;

    private ClientRepository clientRepository;
    private MealPlanRepository mealPlanRepository;
    //private ClientRepository clientRepository1;
    
    private ClientService clientService;
    
    @Mock
    private MealPlanService mealPlanService;


    @BeforeMethod
    public void beforeMethod() {
        clientRepository = Mockito.mock(ClientRepository.class);
        mealPlanRepository = Mockito.mock(MealPlanRepository.class);
        //clientRepository1 = Mockito.mock(ClientRepository.class);

        clientService = new ClientServiceImpl(clientRepository, mealPlanMapper, userMapper, dailyMealPlanMapper, mealPlanService);

    }

    @Test
    public void testGetMealPlan_ValidClientIdIsProvided_ReturnsMealPlan1() {
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
            mealPlanEntity.setModified(LocalDateTime.of(2024, 3, 14, 19, 13));
            mealPlanEntity.setModifiedBy(coachEntity);
            clientEntity.setMealPlan(mealPlanEntity);
            List<MealPlanEntity> mealPlans = new ArrayList();
            mealPlans.add(mealPlanEntity);
            coachEntity.setMealPlans(mealPlans);
            
            MealPlanResponse expectedResponse = new MealPlanResponse();
            expectedResponse.setId(mealPlanEntity.getId());
            expectedResponse.setClientId(clientEntity.getId());
            expectedResponse.setModifiedBy(coachEntity.getId());
            expectedResponse.setModified(mealPlanEntity.getModified());

            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));

            MealPlanResponse actualResponse = clientService.getMealPlan(clientEntity.getId());

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
            
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetMealPlan_ValidClientIdIsProvided_ReturnsMealPlan2() {
        try {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            
            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            
            MealPlanEntity mealPlanEntity = new MealPlanEntity();
            mealPlanEntity.setId(1L);
            mealPlanEntity.setClient(clientEntity);
            mealPlanEntity.setModified(LocalDateTime.of(2024, 7, 2, 14, 1));
            mealPlanEntity.setModifiedBy(coachEntity);
            clientEntity.setMealPlan(mealPlanEntity);
            
            MealPlanResponse expectedResponse = new MealPlanResponse();
            expectedResponse.setId(mealPlanEntity.getId());
            expectedResponse.setClientId(clientEntity.getId());
            expectedResponse.setModifiedBy(coachEntity.getId());
            expectedResponse.setModified(mealPlanEntity.getModified());

            // Mockanje clientRepository da vrati klijenta s praznim planom obroka
            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));
            
            // Pozivanje metode koja se testira
            MealPlanResponse actualResponse = clientService.getMealPlan(clientEntity.getId());

            // Provjera da actualResponse nije null
            Assertions.assertThat(actualResponse).isNotNull();

            // Usporedba actualResponse s očekivanom vrijednošću
            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        } catch (Exception exception) {
            // Ako se dogodi iznimka, test će propasti
            Assert.fail();
        }
    }
    @Test
    public void testGetMealPlan_NullClientId_ReturnsNotFoundException() {
        try {
            Long nullClientId = null; // A null client ID

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getMealPlan(nullClientId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetMealPlan_EmptyClientId_ReturnsNotFoundException() {
        try {
            Long emptyClientId = 0L; // An empty client ID

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getMealPlan(emptyClientId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetMealPlan_InvalidClientId_ReturnsNotFoundException() {
        try {
            Long invalidClientId = 999L; // A non-existent client ID

            Mockito.when(clientRepository.findById(invalidClientId)).thenReturn(Optional.empty());

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getMealPlan(invalidClientId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetClientNameAndLastName_ValidUserIdIsProvided_ReturnsNameAndLastName() {
        try {
        	 // Kreirajte korisnika za kojeg ćemo tražiti klijenta
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id-client");
            userEntity.setFirstName("Augustine II");
            userEntity.setLastName("Cartier II");

            // Kreirajte klijenta povezanog s tim korisnikom
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(userEntity);
            userEntity.setClient(clientEntity);

            // Očekivani odgovor
            UserResponse expectedResponse = new UserResponse();
            expectedResponse.setFirstName("Augustine II");
            expectedResponse.setLastName("Cartier II");

            // Mockanje metode findByUserUuid
            Mockito.when(clientRepository.findByUserUuid(userEntity.getUuid())).thenReturn(Optional.of(clientEntity));
            
            UserResponse actualResponse = clientService.getClientNameAndLastName(clientEntity.getUser().getUuid());


            // Provjera jesu li očekivani i stvarni odgovor isti
            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetClientNameAndLastName_InvalidUserId_ReturnsNotFoundException() {
        try {
            String invalidUserId = "invalid-user-id"; // A non-existent user ID

            Mockito.when(clientRepository.findByUserUuid(invalidUserId)).thenReturn(Optional.empty());

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getClientNameAndLastName(invalidUserId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetClientNameAndLastName_NullUserId_ReturnsNotFoundException() {
        try {
            String nullUserId = null; // A null user ID

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getClientNameAndLastName(nullUserId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetClientNameAndLastName_EmptyUserId_ReturnsNotFoundException() {
        try {
            String emptyUserId = ""; // An empty user ID

            Assert.assertThrows(NotFoundException.class, () -> {
                clientService.getClientNameAndLastName(emptyUserId);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetDailyMealPlanByClientId_ValidClientId_ReturnsDailyMealPlanList() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("uuid");
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(userEntity);
            userEntity.setClient(clientEntity);

            MealPlanEntity mealPlanEntity = new MealPlanEntity();
            mealPlanEntity.setId(1L);
            mealPlanEntity.setClient(clientEntity);
            mealPlanEntity.setMealPlans(new ArrayList<>());
            clientEntity.setMealPlan(mealPlanEntity);
            
            DailyMealPlanEntity dailyMealPlanEntity = new DailyMealPlanEntity();
            dailyMealPlanEntity.setId(1L);
            dailyMealPlanEntity.setDay("Monday");
            dailyMealPlanEntity.setBreakfast("Oatmeal");
            dailyMealPlanEntity.setAmSnack("Snack1");
            dailyMealPlanEntity.setLunch("Chicken Salad");
            dailyMealPlanEntity.setDinner("Grilled Fish");
            dailyMealPlanEntity.setPmSnack("Snack2");
            List<DailyMealPlanEntity> dailyMealPlanEntities = new ArrayList<>();
            dailyMealPlanEntities.add(dailyMealPlanEntity);
            mealPlanEntity.setMealPlans(dailyMealPlanEntities);
            
            List<DailyMealPlanResponse> expectedResponse = new ArrayList<>();
            
            DailyMealPlanResponse dailyMealPlanResponse = new DailyMealPlanResponse();
            dailyMealPlanResponse.setDay("Monday");
            dailyMealPlanResponse.setBreakfast("Oatmeal");
            dailyMealPlanResponse.setAmSnack("Snack1");
            dailyMealPlanResponse.setLunch("Chicken Salad");
            dailyMealPlanResponse.setDinner("Grilled Fish");
            dailyMealPlanResponse.setPmSnack("Snack2");
            
        	expectedResponse.add(dailyMealPlanResponse);

            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));
            Mockito.when(mealPlanService.getDailyMealByDay(any(Long.class))).thenReturn(expectedResponse);

            List<DailyMealPlanResponse> actualResponse = clientService.getDailyMealPlanByClientId(clientEntity.getId());

            Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);


        } catch (Exception exception) {
            Assert.fail("Exception occurred: " + exception.getMessage());
        }
    }


    @Test
    public void testGetDailyMealPlanByClientId_InvalidClientId_ReturnsNotFoundException() {
        try {
            Long invalidClientId = 999L; // A non-existent client ID

            // Mockanje poziva metode getMealPlan koja će vratiti null
            Mockito.when(clientService.getMealPlan(any(Long.class))).thenReturn(null);

            // Pozivanje metode koja se testira
            clientService.getDailyMealPlanByClientId(invalidClientId);

            // Ako metoda ne baci iznimku, test bi trebao propasti
            Assert.fail("NotFoundException expected, but method executed without throwing an exception");
        } catch (NotFoundException e) {
            // Ako se očekuje iznimka, test bi trebao uspjeti
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
