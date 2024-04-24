package com.fithub.services.mealplan.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.mealplan.dao.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
    @Query("SELECT u FROM UserEntity u WHERE u.firstName = ?1 AND u.lastName = ?2")
    Optional<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);
}