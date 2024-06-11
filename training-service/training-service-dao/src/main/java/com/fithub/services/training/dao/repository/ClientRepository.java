package com.fithub.services.training.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fithub.services.training.dao.model.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findByCoachId(Long coachId);

}