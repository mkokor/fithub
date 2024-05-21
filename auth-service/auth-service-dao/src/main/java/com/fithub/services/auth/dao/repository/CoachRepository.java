package com.fithub.services.auth.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.dao.model.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {

    @Query("SELECT new com.fithub.services.auth.api.coach.CoachLoV(c.id, CONCAT(c.user.firstName, ' ', c.user.lastName)) FROM CoachEntity c WHERE SIZE(c.clients) < c.clientCapacity")
    List<CoachLoV> findAvailableCoaches();

}