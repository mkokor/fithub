package com.fithub.services.training.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.training.dao.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}