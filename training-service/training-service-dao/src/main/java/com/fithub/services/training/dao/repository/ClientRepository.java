package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fithub.services.training.dao.model.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

}