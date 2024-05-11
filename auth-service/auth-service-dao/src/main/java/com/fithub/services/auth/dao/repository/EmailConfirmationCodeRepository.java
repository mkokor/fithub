package com.fithub.services.auth.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.auth.dao.model.EmailConfirmationCodeEntity;
import com.fithub.services.auth.dao.model.UserEntity;

@Repository
public interface EmailConfirmationCodeRepository extends JpaRepository<EmailConfirmationCodeEntity, Long> {

    Optional<EmailConfirmationCodeEntity> findByUser(UserEntity user);

}