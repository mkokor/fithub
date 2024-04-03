package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fithub.services.training.dao.model.SongRequestEntity;

public interface SongRequestRepository extends JpaRepository<SongRequestEntity, Long> {

}