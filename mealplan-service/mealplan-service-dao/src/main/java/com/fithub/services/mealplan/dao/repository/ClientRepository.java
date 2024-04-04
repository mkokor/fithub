package com.fithub.services.mealplan.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
	
    public Optional<ClientEntity> findByUserUuid(String userId);

}