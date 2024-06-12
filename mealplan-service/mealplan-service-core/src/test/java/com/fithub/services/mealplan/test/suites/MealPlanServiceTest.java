package com.fithub.services.mealplan.test.suites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.core.context.UserContext;
import com.fithub.services.mealplan.core.impl.MealPlanServiceImpl;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.DailyMealPlanRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;
import com.fithub.services.mealplan.mapper.MealPlanMapper;
import com.fithub.services.mealplan.test.configuration.BasicTestConfiguration;

public class MealPlanServiceTest extends BasicTestConfiguration {

    @Autowired
    private MealPlanMapper mealPlanMapper;

    @Autowired
    private DailyMealPlanMapper dailyMealPlanMapper;

    private UserRepository userRepository;
    private MealPlanRepository mealPlanRepository;
    private DailyMealPlanRepository dailyMealPlanRepository;
    private MealPlanService mealPlanService;

    @BeforeMethod
    public void beforeMethod() {
        userRepository = Mockito.mock(UserRepository.class);
        mealPlanRepository = Mockito.mock(MealPlanRepository.class);
        dailyMealPlanRepository = Mockito.mock(DailyMealPlanRepository.class);

        mealPlanService = new MealPlanServiceImpl(userRepository, mealPlanRepository, dailyMealPlanRepository, mealPlanMapper,
                dailyMealPlanMapper);
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
    public void testGetMealPlanByClientUuid_ValidClientUuidProvided_ReturnsMealPlan() {
        try {
            authenticateCoach();
            CoachEntity coach = constructCoachUser().getCoach();

            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("test-client");
            userEntity.setFirstName("Test");
            userEntity.setLastName("Client");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(userEntity);
            clientEntity.setId(1L);
            clientEntity.setCoach(coach);
            userEntity.setClient(clientEntity);

            LocalDateTime currentDateTime = LocalDateTime.now();

            List<DailyMealPlanEntity> dailyMealPlanEntities = new ArrayList<>();

            DailyMealPlanEntity mondayDailyMealPlanEntity = new DailyMealPlanEntity();
            mondayDailyMealPlanEntity.setDay("Monday");
            mondayDailyMealPlanEntity.setBreakfast("/");
            mondayDailyMealPlanEntity.setAmSnack("/");
            mondayDailyMealPlanEntity.setDinner("/");
            mondayDailyMealPlanEntity.setLunch("/");
            mondayDailyMealPlanEntity.setPmSnack("/");
            mondayDailyMealPlanEntity.setId(1L);
            dailyMealPlanEntities.add(mondayDailyMealPlanEntity);

            DailyMealPlanEntity tuesdayDailyMealPlanEntity = new DailyMealPlanEntity();
            tuesdayDailyMealPlanEntity.setDay("Tuesday");
            tuesdayDailyMealPlanEntity.setBreakfast("/");
            tuesdayDailyMealPlanEntity.setAmSnack("/");
            tuesdayDailyMealPlanEntity.setDinner("/");
            tuesdayDailyMealPlanEntity.setLunch("/");
            tuesdayDailyMealPlanEntity.setPmSnack("/");
            tuesdayDailyMealPlanEntity.setId(2L);
            dailyMealPlanEntities.add(tuesdayDailyMealPlanEntity);

            DailyMealPlanEntity wednesdayDailyMealPlanEntity = new DailyMealPlanEntity();
            wednesdayDailyMealPlanEntity.setDay("Wednesday");
            wednesdayDailyMealPlanEntity.setBreakfast("/");
            wednesdayDailyMealPlanEntity.setAmSnack("/");
            wednesdayDailyMealPlanEntity.setDinner("/");
            wednesdayDailyMealPlanEntity.setLunch("/");
            wednesdayDailyMealPlanEntity.setPmSnack("/");
            wednesdayDailyMealPlanEntity.setId(3L);
            dailyMealPlanEntities.add(wednesdayDailyMealPlanEntity);

            DailyMealPlanEntity thursdayDailyMealPlanEntity = new DailyMealPlanEntity();
            thursdayDailyMealPlanEntity.setDay("Thursday");
            thursdayDailyMealPlanEntity.setBreakfast("/");
            thursdayDailyMealPlanEntity.setAmSnack("/");
            thursdayDailyMealPlanEntity.setDinner("/");
            thursdayDailyMealPlanEntity.setLunch("/");
            thursdayDailyMealPlanEntity.setPmSnack("/");
            thursdayDailyMealPlanEntity.setId(4L);
            dailyMealPlanEntities.add(thursdayDailyMealPlanEntity);

            DailyMealPlanEntity fridayDailyMealPlanEntity = new DailyMealPlanEntity();
            fridayDailyMealPlanEntity.setDay("Friday");
            fridayDailyMealPlanEntity.setBreakfast("/");
            fridayDailyMealPlanEntity.setAmSnack("/");
            fridayDailyMealPlanEntity.setDinner("/");
            fridayDailyMealPlanEntity.setLunch("/");
            fridayDailyMealPlanEntity.setPmSnack("/");
            fridayDailyMealPlanEntity.setId(5L);
            dailyMealPlanEntities.add(fridayDailyMealPlanEntity);

            DailyMealPlanEntity saturdayDailyMealPlanEntity = new DailyMealPlanEntity();
            saturdayDailyMealPlanEntity.setDay("Saturday");
            saturdayDailyMealPlanEntity.setBreakfast("/");
            saturdayDailyMealPlanEntity.setAmSnack("/");
            saturdayDailyMealPlanEntity.setDinner("/");
            saturdayDailyMealPlanEntity.setLunch("/");
            saturdayDailyMealPlanEntity.setPmSnack("/");
            saturdayDailyMealPlanEntity.setId(6L);
            dailyMealPlanEntities.add(saturdayDailyMealPlanEntity);

            DailyMealPlanEntity sundayDailyMealPlanEntity = new DailyMealPlanEntity();
            sundayDailyMealPlanEntity.setDay("Sunday");
            sundayDailyMealPlanEntity.setBreakfast("/");
            sundayDailyMealPlanEntity.setAmSnack("/");
            sundayDailyMealPlanEntity.setDinner("/");
            sundayDailyMealPlanEntity.setLunch("/");
            sundayDailyMealPlanEntity.setPmSnack("/");
            sundayDailyMealPlanEntity.setId(7L);
            dailyMealPlanEntities.add(sundayDailyMealPlanEntity);

            MealPlanEntity mealPlanEntity = new MealPlanEntity();
            mealPlanEntity.setClient(clientEntity);
            mealPlanEntity.setId(1L);
            mealPlanEntity.setLastModified(currentDateTime);
            mealPlanEntity.setLastModifiedBy(coach);
            clientEntity.setMealPlan(mealPlanEntity);

            ClientResponse clientResponseExpected = new ClientResponse();
            clientResponseExpected.setUuid(userEntity.getUuid());
            clientResponseExpected.setFirstName(userEntity.getFirstName());
            clientResponseExpected.setLastName(userEntity.getLastName());

            List<DailyMealPlanResponse> dailyMealPlanResponses = new ArrayList<>();

            DailyMealPlanResponse mondayDailyMealPlanResponse = new DailyMealPlanResponse();
            mondayDailyMealPlanResponse.setDay("Monday");
            mondayDailyMealPlanResponse.setBreakfast("/");
            mondayDailyMealPlanResponse.setAmSnack("/");
            mondayDailyMealPlanResponse.setDinner("/");
            mondayDailyMealPlanResponse.setLunch("/");
            mondayDailyMealPlanResponse.setPmSnack("/");
            mondayDailyMealPlanResponse.setId(1L);
            dailyMealPlanResponses.add(mondayDailyMealPlanResponse);

            DailyMealPlanResponse tuesdayDailyMealPlanResponse = new DailyMealPlanResponse();
            tuesdayDailyMealPlanResponse.setDay("Tuesday");
            tuesdayDailyMealPlanResponse.setBreakfast("/");
            tuesdayDailyMealPlanResponse.setAmSnack("/");
            tuesdayDailyMealPlanResponse.setDinner("/");
            tuesdayDailyMealPlanResponse.setLunch("/");
            tuesdayDailyMealPlanResponse.setPmSnack("/");
            tuesdayDailyMealPlanResponse.setId(2L);
            dailyMealPlanResponses.add(tuesdayDailyMealPlanResponse);

            DailyMealPlanResponse wednesdayDailyMealPlanResponse = new DailyMealPlanResponse();
            wednesdayDailyMealPlanResponse.setDay("Wednesday");
            wednesdayDailyMealPlanResponse.setBreakfast("/");
            wednesdayDailyMealPlanResponse.setAmSnack("/");
            wednesdayDailyMealPlanResponse.setDinner("/");
            wednesdayDailyMealPlanResponse.setLunch("/");
            wednesdayDailyMealPlanResponse.setPmSnack("/");
            wednesdayDailyMealPlanResponse.setId(3L);
            dailyMealPlanResponses.add(wednesdayDailyMealPlanResponse);

            DailyMealPlanResponse thursdayDailyMealPlanResponse = new DailyMealPlanResponse();
            thursdayDailyMealPlanResponse.setDay("Thursday");
            thursdayDailyMealPlanResponse.setBreakfast("/");
            thursdayDailyMealPlanResponse.setAmSnack("/");
            thursdayDailyMealPlanResponse.setDinner("/");
            thursdayDailyMealPlanResponse.setLunch("/");
            thursdayDailyMealPlanResponse.setPmSnack("/");
            thursdayDailyMealPlanResponse.setId(4L);
            dailyMealPlanResponses.add(thursdayDailyMealPlanResponse);

            DailyMealPlanResponse fridayDailyMealPlanResponse = new DailyMealPlanResponse();
            fridayDailyMealPlanResponse.setDay("Friday");
            fridayDailyMealPlanResponse.setBreakfast("/");
            fridayDailyMealPlanResponse.setAmSnack("/");
            fridayDailyMealPlanResponse.setDinner("/");
            fridayDailyMealPlanResponse.setLunch("/");
            fridayDailyMealPlanResponse.setPmSnack("/");
            fridayDailyMealPlanResponse.setId(5L);
            dailyMealPlanResponses.add(fridayDailyMealPlanResponse);

            DailyMealPlanResponse saturdayDailyMealPlanResponse = new DailyMealPlanResponse();
            saturdayDailyMealPlanResponse.setDay("Saturday");
            saturdayDailyMealPlanResponse.setBreakfast("/");
            saturdayDailyMealPlanResponse.setAmSnack("/");
            saturdayDailyMealPlanResponse.setDinner("/");
            saturdayDailyMealPlanResponse.setLunch("/");
            saturdayDailyMealPlanResponse.setPmSnack("/");
            saturdayDailyMealPlanResponse.setId(6L);
            dailyMealPlanResponses.add(saturdayDailyMealPlanResponse);

            DailyMealPlanResponse sundayDailyMealPlanResponse = new DailyMealPlanResponse();
            sundayDailyMealPlanResponse.setDay("Sunday");
            sundayDailyMealPlanResponse.setBreakfast("/");
            sundayDailyMealPlanResponse.setAmSnack("/");
            sundayDailyMealPlanResponse.setDinner("/");
            sundayDailyMealPlanResponse.setLunch("/");
            sundayDailyMealPlanResponse.setPmSnack("/");
            sundayDailyMealPlanResponse.setId(7L);
            dailyMealPlanResponses.add(sundayDailyMealPlanResponse);

            CoachResponse coachResponse = new CoachResponse();
            coachResponse.setUuid(coach.getUser().getUuid());
            coachResponse.setFirstName(coach.getUser().getFirstName());
            coachResponse.setLastName(coach.getUser().getLastName());

            MealPlanResponse mealPlanResponseExpected = new MealPlanResponse();
            mealPlanResponseExpected.setClient(clientResponseExpected);
            mealPlanResponseExpected.setDailyMealPlans(dailyMealPlanResponses);
            mealPlanResponseExpected.setLastModified(currentDateTime);
            mealPlanResponseExpected.setLastModifiedBy(coachResponse);
            mealPlanResponseExpected.setId(mealPlanEntity.getId());

            Mockito.when(userRepository.findById(userEntity.getUuid())).thenReturn(Optional.of(userEntity));
            Mockito.when(dailyMealPlanRepository.findByMealPlanId(mealPlanEntity.getId())).thenReturn(dailyMealPlanEntities);
            Mockito.when(mealPlanRepository.findByClientUuid(clientEntity.getUser().getUuid())).thenReturn(Optional.of(mealPlanEntity));

            MealPlanResponse mealPlanResponseActual = mealPlanService.getMealPlanByClientUuid(clientEntity.getUser().getUuid());

            Assert.assertEquals(mealPlanResponseActual, mealPlanResponseExpected);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetMealPlanByClientUuid_InvalidClientUuidProvided_ThrowsNotFoundException() {
        try {
            authenticateCoach();
            final String invalidClientUuid = "invalid-uuid";

            Mockito.when(userRepository.findById(invalidClientUuid)).thenReturn(Optional.empty());

            Assert.assertThrows(NotFoundException.class, () -> mealPlanService.getMealPlanByClientUuid(invalidClientUuid));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}