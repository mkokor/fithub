package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.services.training.dao.model.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {

    @Query("SELECT COUNT(c.id) FROM ClientEntity c WHERE c.coach.id = :coachId")
    Integer getNumberOfClients(@Param("coachId") Long coachId);

}