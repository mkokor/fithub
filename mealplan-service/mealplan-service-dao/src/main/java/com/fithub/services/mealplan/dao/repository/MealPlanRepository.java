package com.fithub.services.mealplan.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.MealPlanEntity;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlanEntity, Long> {

    @Query("SELECT m FROM MealPlanEntity m WHERE m.client.user.uuid = :clientUuid")
    Optional<MealPlanEntity> findByClientUuid(@Param("clientUuid") String clientUuid);

}