package com.fithub.services.mealplan.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;

@Repository
public interface DailyMealPlanRepository extends JpaRepository<DailyMealPlanEntity, Long> {
		
	Optional<DailyMealPlanEntity> findById(Long clientId);
	
}