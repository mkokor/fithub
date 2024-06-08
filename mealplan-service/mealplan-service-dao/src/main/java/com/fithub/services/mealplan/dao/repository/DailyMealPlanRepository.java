package com.fithub.services.mealplan.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;

@Repository
public interface DailyMealPlanRepository extends JpaRepository<DailyMealPlanEntity, Long> {

    @Override
    Optional<DailyMealPlanEntity> findById(Long clientId);

    @Query("SELECT d FROM DailyMealPlanEntity d WHERE d.mealPlan.id = :mealPlanId")
    List<DailyMealPlanEntity> findByMealPlanId(@Param("mealPlanId") Long mealPlanId);

}