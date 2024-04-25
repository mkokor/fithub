package com.fithub.services.mealplan.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlanEntity, Long> {
	
	@Query("select r from MealPlanEntity r where r.client.id = ?1")
	MealPlanEntity findMealPlanByClientId(Long clientId);
	
	//boolean existsByClient(ClientEntity client);
}