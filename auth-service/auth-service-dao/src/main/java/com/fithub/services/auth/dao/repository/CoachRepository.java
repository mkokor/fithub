package com.fithub.services.auth.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.auth.dao.model.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {

}