package com.fithub.services.auth.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fithub.services.auth.dao.model.PasswordResetCodeEntity;
import com.fithub.services.auth.dao.model.UserEntity;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCodeEntity, Long> {

    Optional<PasswordResetCodeEntity> findByUser(UserEntity user);

}