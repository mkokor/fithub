package com.fithub.services.mealplan.test.suites;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.core.impl.MealPlanServiceImpl;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;
import com.fithub.services.mealplan.test.configuration.BasicTestConfiguration;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

public class MealPlanServiceTest extends BasicTestConfiguration {
	
    @Autowired
    private DailyMealPlanMapper dailyMealPlanMapper;
    
    private MealPlanRepository mealPlanRepository;
    
    private MealPlanService mealPlanService;
    
    @BeforeMethod
    public void beforeMethod() {
    	mealPlanRepository = Mockito.mock(MealPlanRepository.class);
        //clientRepository1 = Mockito.mock(ClientRepository.class);

    	mealPlanService = new MealPlanServiceImpl(mealPlanRepository, dailyMealPlanMapper);
    }
    
    @Test
    public void testGetDailyMealByDay_ValidMealPlanIdIsProvided_ReturnsMealPlans() {
        try {
        	
            MealPlanEntity mealPlanEntity = new MealPlanEntity();
            mealPlanEntity.setId(1L);
            
            DailyMealPlanEntity dailyMealPlanEntity = new DailyMealPlanEntity();
            dailyMealPlanEntity.setDay("Monday");
        	dailyMealPlanEntity.setBreakfast("Oatmeal"); 
        	dailyMealPlanEntity.setAmSnack("Apple"); 
        	dailyMealPlanEntity.setLunch("Grilled Chicken Salad"); 
        	dailyMealPlanEntity.setDinner("Salmon with Quinoa"); 
        	dailyMealPlanEntity.setPmSnack("Yogurt"); 
        	dailyMealPlanEntity.setMealPlan(mealPlanEntity);
        	
            List<DailyMealPlanEntity> dailyMealPlanEntities = new ArrayList<>();
            dailyMealPlanEntities.add(dailyMealPlanEntity);
            mealPlanEntity.setMealPlans(dailyMealPlanEntities);
            
            List<DailyMealPlanResponse> expectedResponse = new ArrayList();
            DailyMealPlanResponse dailyMealPlanResponse = new DailyMealPlanResponse();
            dailyMealPlanResponse.setDay("Monday");
        	dailyMealPlanResponse.setBreakfast("Oatmeal"); 
        	dailyMealPlanResponse.setAmSnack("Apple"); 
        	dailyMealPlanResponse.setLunch("Grilled Chicken Salad"); 
        	dailyMealPlanResponse.setDinner("Salmon with Quinoa"); 
        	dailyMealPlanResponse.setPmSnack("Yogurt"); 
        	
        	expectedResponse.add(dailyMealPlanResponse);

            Mockito.when(mealPlanRepository.findById(mealPlanEntity.getId())).thenReturn(Optional.of(mealPlanEntity));

            List<DailyMealPlanResponse> actualResponse = mealPlanService.getDailyMealByDay(mealPlanEntity.getId());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
            
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    

}
