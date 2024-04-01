package com.fithub.services.mealplan.dao.seed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.dao.repository.CoachRepository;
import com.fithub.services.mealplan.dao.repository.DailyMealPlanRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final MealPlanRepository mealPlanRepository;
    private final DailyMealPlanRepository dailyMealPlanRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
    	
    	UserEntity userEntity1 = new UserEntity();
    	UserEntity userEntity2 = new UserEntity();
    	UserEntity userEntity3 = new UserEntity();
    	UserEntity userEntity4 = new UserEntity();
    	UserEntity userEntity5 = new UserEntity();
    	UserEntity userEntity6 = new UserEntity();
    	UserEntity userEntity7 = new UserEntity();
    	UserEntity userEntity8 = new UserEntity();
    	UserEntity userEntity9 = new UserEntity();
    	UserEntity userEntity10 = new UserEntity();
    	
    	if (userRepository.findAll().isEmpty()) {
    	//For coaches
    	
    	userEntity1.setUuid(UUID.randomUUID().toString());
    	userEntity1.setFirstName("John1");
    	userEntity1.setLastName("Doe1");
    	userRepository.save(userEntity1);

    	userEntity2.setUuid(UUID.randomUUID().toString());
    	userEntity2.setFirstName("John2");
    	userEntity2.setLastName("Doe2");
    	userRepository.save(userEntity2);

    	userEntity3.setUuid(UUID.randomUUID().toString());
    	userEntity3.setFirstName("John3");
    	userEntity3.setLastName("Doe3");
    	userRepository.save(userEntity3);

    	userEntity4.setUuid(UUID.randomUUID().toString());
    	userEntity4.setFirstName("John4");
    	userEntity4.setLastName("Doe4");
    	userRepository.save(userEntity4);

    	userEntity5.setUuid(UUID.randomUUID().toString());
    	userEntity5.setFirstName("John5");
    	userEntity5.setLastName("Doe5");
    	userRepository.save(userEntity5);
    	
    	//For clients
    	userEntity6.setUuid(UUID.randomUUID().toString());
    	userEntity6.setFirstName("Jane6");
    	userEntity6.setLastName("Smith6");
    	userRepository.save(userEntity6);

    	userEntity7.setUuid(UUID.randomUUID().toString());
    	userEntity7.setFirstName("Jane7");
    	userEntity7.setLastName("Smith7");
    	userRepository.save(userEntity7);

    	userEntity8.setUuid(UUID.randomUUID().toString());
    	userEntity8.setFirstName("Jane8");
    	userEntity8.setLastName("Smith8");
    	userRepository.save(userEntity8);

    	userEntity9.setUuid(UUID.randomUUID().toString());
    	userEntity9.setFirstName("Jane9");
    	userEntity9.setLastName("Smith9");
    	userRepository.save(userEntity9);

    	userEntity10.setUuid(UUID.randomUUID().toString());
    	userEntity10.setFirstName("Jane10");
    	userEntity10.setLastName("Smith10");
    	userRepository.save(userEntity10);

    	}


    	CoachEntity coachEntity1 = new CoachEntity();
    	CoachEntity coachEntity2 = new CoachEntity();
    	CoachEntity coachEntity3 = new CoachEntity();
    	CoachEntity coachEntity4 = new CoachEntity();
    	CoachEntity coachEntity5 = new CoachEntity();
    	
    	if (coachRepository.findAll().isEmpty()) {
    	    coachEntity1.setUser(userEntity1);
    	    coachRepository.save(coachEntity1);
    	    
    	    coachEntity2.setUser(userEntity2);
    	    coachRepository.save(coachEntity2);
    	    
    	    coachEntity3.setUser(userEntity3);
    	    coachRepository.save(coachEntity3);
    	    
    	    coachEntity4.setUser(userEntity4);
    	    coachRepository.save(coachEntity4);
    	    
    	    coachEntity5.setUser(userEntity5);
    	    coachRepository.save(coachEntity5);
    	}

    	ClientEntity clientEntity1 = new ClientEntity();
    	ClientEntity clientEntity2 = new ClientEntity();
    	ClientEntity clientEntity3 = new ClientEntity();
    	ClientEntity clientEntity4 = new ClientEntity();
    	ClientEntity clientEntity5 = new ClientEntity();
    	        
    	if (clientRepository.findAll().isEmpty()) {
    	    clientEntity1.setUser(userEntity6);
    	    clientEntity1.setCoach(coachEntity1);
    	    clientRepository.save(clientEntity1);
    	    
    	    clientEntity2.setUser(userEntity7);
    	    clientEntity2.setCoach(coachEntity2);
    	    clientRepository.save(clientEntity2);
    	    
    	    clientEntity3.setUser(userEntity8);
    	    clientEntity3.setCoach(coachEntity3);
    	    clientRepository.save(clientEntity3);
    	    
    	    clientEntity4.setUser(userEntity9);
    	    clientEntity4.setCoach(coachEntity4);
    	    clientRepository.save(clientEntity4);
    	    
    	    clientEntity5.setUser(userEntity10);
    	    clientEntity5.setCoach(coachEntity5);
    	    clientRepository.save(clientEntity5);
    	}

        
        MealPlanEntity mealPlanEntity1 = new MealPlanEntity();
        MealPlanEntity mealPlanEntity2 = new MealPlanEntity();
        MealPlanEntity mealPlanEntity3 = new MealPlanEntity();
        MealPlanEntity mealPlanEntity4 = new MealPlanEntity();
        MealPlanEntity mealPlanEntity5 = new MealPlanEntity();
        
        
        if (mealPlanRepository.findAll().isEmpty()) {
            mealPlanEntity1.setClient(clientEntity1);
            mealPlanEntity1.setModified(LocalDateTime.of(2024, 3, 14, 9, 13));
            mealPlanEntity1.setModifiedBy(coachEntity1);
            mealPlanRepository.save(mealPlanEntity1);
            
            mealPlanEntity2.setClient(clientEntity2);
            mealPlanEntity2.setModified(LocalDateTime.of(2024, 3, 24, 9, 23));
            mealPlanEntity2.setModifiedBy(coachEntity2);
            mealPlanRepository.save(mealPlanEntity2);
            
            mealPlanEntity3.setClient(clientEntity3);
            mealPlanEntity3.setModified(LocalDateTime.of(2024, 5, 3, 11, 33));
            mealPlanEntity3.setModifiedBy(coachEntity3);
            mealPlanRepository.save(mealPlanEntity3);
            
            mealPlanEntity4.setClient(clientEntity4);
            mealPlanEntity4.setModified(LocalDateTime.of(2024, 4, 4, 12, 44));
            mealPlanEntity4.setModifiedBy(coachEntity4);
            mealPlanRepository.save(mealPlanEntity4);
            
            mealPlanEntity5.setClient(clientEntity5);
            mealPlanEntity5.setModified(LocalDateTime.of(2024, 5, 8, 10, 55));
            mealPlanEntity5.setModifiedBy(coachEntity5);
            mealPlanRepository.save(mealPlanEntity5);
        }
        
        DailyMealPlanEntity dailyMealPlanEntity1 = new DailyMealPlanEntity();
        DailyMealPlanEntity dailyMealEntity2 = new DailyMealPlanEntity();
        DailyMealPlanEntity dailyMealEntity3 = new DailyMealPlanEntity();
        DailyMealPlanEntity dailyMealEntity4 = new DailyMealPlanEntity();
        DailyMealPlanEntity dailyMealEntity5 = new DailyMealPlanEntity();
        
        if (dailyMealPlanRepository.findAll().isEmpty()) {
            dailyMealPlanEntity1.setDay("Monday");
            dailyMealPlanEntity1.setBreakfast("Oatmeal");
            dailyMealPlanEntity1.setAmSnack("Apple");
            dailyMealPlanEntity1.setLunch("Grilled Chicken Salad");
            dailyMealPlanEntity1.setDinner("Salmon with Quinoa");
            dailyMealPlanEntity1.setPmSnack("Yogurt");
            dailyMealPlanEntity1.setMealPlan(mealPlanEntity1);
            dailyMealPlanRepository.save(dailyMealPlanEntity1);
            
            dailyMealEntity2.setDay("Tuesday");
            dailyMealEntity2.setBreakfast("Avocado Toast");
            dailyMealEntity2.setAmSnack("Orange");
            dailyMealEntity2.setLunch("Quinoa Salad");
            dailyMealEntity2.setDinner("Baked Chicken with Sweet Potatoes");
            dailyMealEntity2.setPmSnack("Mixed Nuts");
            dailyMealEntity2.setMealPlan(mealPlanEntity2);
            dailyMealPlanRepository.save(dailyMealEntity2);

            dailyMealEntity3.setDay("Wednesday");
            dailyMealEntity3.setBreakfast("Blueberry Pancakes");
            dailyMealEntity3.setAmSnack("Pear");
            dailyMealEntity3.setLunch("Vegetable Stir-Fry");
            dailyMealEntity3.setDinner("Shrimp Pasta");
            dailyMealEntity3.setPmSnack("Cottage Cheese");
            dailyMealEntity3.setMealPlan(mealPlanEntity3);
            dailyMealPlanRepository.save(dailyMealEntity3);

            dailyMealEntity4.setDay("Thursday");
            dailyMealEntity4.setBreakfast("Yogurt Parfait");
            dailyMealEntity4.setAmSnack("Grapes");
            dailyMealEntity4.setLunch("Turkey Sandwich");
            dailyMealEntity4.setDinner("Beef Stir-Fry");
            dailyMealEntity4.setPmSnack("Rice Cakes");
            dailyMealEntity4.setMealPlan(mealPlanEntity4);
            dailyMealPlanRepository.save(dailyMealEntity4);

            dailyMealEntity5.setDay("Friday");
            dailyMealEntity5.setBreakfast("Bagel with Cream Cheese");
            dailyMealEntity5.setAmSnack("Kiwi");
            dailyMealEntity5.setLunch("Salmon Wrap");
            dailyMealEntity5.setDinner("Vegetarian Pizza");
            dailyMealEntity5.setPmSnack("Trail Mix");
            dailyMealEntity5.setMealPlan(mealPlanEntity5);
            dailyMealPlanRepository.save(dailyMealEntity5);
        }


    }
}
