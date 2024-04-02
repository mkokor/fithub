package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fithub.services.training.dao.model.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
	
	@Query("select r from ReservationEntity r where r.client.id = ?2 and r.appointment.id = ?1")
	ReservationEntity findReservationByClientId(Long appointmentId, Long clientId);

}