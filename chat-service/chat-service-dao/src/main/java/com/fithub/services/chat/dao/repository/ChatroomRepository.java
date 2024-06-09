package com.fithub.services.chat.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.chat.dao.model.ChatroomEntity;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Long> {

    @Query("SELECT c FROM ChatroomEntity c WHERE c.admin.id = ?1")
    Optional<ChatroomEntity> findByCoachId(Long coachId);

}