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
    	
    	if (userRepository.findAll().isEmpty()) {
            userEntity1.setUuid(UUID.randomUUID().toString());
            userEntity1.setFirstName("John");
            userEntity1.setLastName("Doe");
            userRepository.save(userEntity1);
            
            userEntity2.setUuid(UUID.randomUUID().toString());
            userEntity2.setFirstName("John");
            userEntity2.setLastName("Johnnn");
            userRepository.save(userEntity2);
    	}
    	
    	CoachEntity coachEntity1 = new CoachEntity();
        
    	if (coachRepository.findAll().isEmpty()) {
    	    coachEntity1.setUser(userEntity1);
    	    coachRepository.save(coachEntity1);
    	}

    	ClientEntity clientEntity = new ClientEntity();
    	        
    	if (clientRepository.findAll().isEmpty()) {
    	    clientEntity.setUser(userEntity1);
    	    clientEntity.setCoach(coachEntity1);
    	    clientRepository.save(clientEntity);
    	}

        
        // Stvori novi plan obroka i dodijeli mu klijenta i trenutno vrijeme
        MealPlanEntity mealPlanEntity = new MealPlanEntity();
        
        if (mealPlanRepository.findAll().isEmpty()) {
            mealPlanEntity.setClient(clientEntity);
            mealPlanEntity.setModified(LocalDateTime.of(2023, 5, 15, 12, 30));
            mealPlanEntity.setModifiedBy(coachEntity1);
            mealPlanRepository.save(mealPlanEntity);
        }

        

        
        
        // Provjeri postoji li već plan obroka u bazi podataka
            // Stvori novog korisnika
            /*UserEntity userEntity = new UserEntity();
            userEntity.setUuid(UUID.randomUUID().toString());
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");
            userRepository.save(userEntity);

            // Stvori novog trenera i dodijeli mu korisnika
            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setUser(userEntity);
            coachRepository.save(coachEntity);


            if (clientRepository.findAll().isEmpty()) {
                // Stvori novog klijenta i dodijeli mu korisnika i trenera
                ClientEntity clientEntity = new ClientEntity();
                clientEntity.setUser(userEntity);
                clientEntity.setCoach(coachEntity);
                clientRepository.save(clientEntity);
                
                // Stvori novi plan obroka i dodijeli mu klijenta i trenutno vrijeme
                MealPlanEntity mealPlanEntity = new MealPlanEntity();
                mealPlanEntity.setClient(clientEntity);
                mealPlanEntity.setModified(LocalDateTime.of(2023, 5, 15, 12, 30));
                mealPlanEntity.setModifiedBy(coachEntity);
                mealPlanRepository.save(mealPlanEntity);
                
                // Stvori novi dnevni plan obroka i dodijeli mu plan obroka
                DailyMealPlanEntity dailyMealPlanEntity = new DailyMealPlanEntity();
                dailyMealPlanEntity.setDay("Monday");
                dailyMealPlanEntity.setBreakfast("Oatmeal");
                dailyMealPlanEntity.setAmSnack("Apple");
                dailyMealPlanEntity.setLunch("Grilled Chicken Salad");
                dailyMealPlanEntity.setDinner("Salmon with Steamed Vegetables");
                dailyMealPlanEntity.setPmSnack("Greek Yogurt");
                dailyMealPlanEntity.setMealPlan(mealPlanEntity);
            }*/
            
        
    }
}
