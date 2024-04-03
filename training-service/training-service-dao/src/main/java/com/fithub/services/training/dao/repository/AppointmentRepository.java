package com.fithub.services.training.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.training.dao.model.AppointmentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
	@Query("select a from AppointmentEntity a where a.coach.id = ?1 and size(a.reservations) < a.capacity")
	List<AppointmentEntity> findAvailableAppointmentsByCoachId(Long coachId);
}