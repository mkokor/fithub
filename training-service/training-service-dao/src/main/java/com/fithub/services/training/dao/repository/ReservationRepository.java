package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fithub.services.training.dao.model.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}