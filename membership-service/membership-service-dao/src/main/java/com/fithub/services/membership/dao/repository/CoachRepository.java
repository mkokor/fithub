package com.fithub.services.membership.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.membership.dao.model.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {

}