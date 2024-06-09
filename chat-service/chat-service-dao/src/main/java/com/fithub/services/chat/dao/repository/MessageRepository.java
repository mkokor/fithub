package com.fithub.services.chat.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.services.chat.dao.model.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("SELECT m FROM MessageEntity m WHERE m.chatroom.id = :chatroomId")
    List<MessageEntity> findByChatroomId(@Param("chatroomId") Long chatroomId, Pageable pageable);

    int countByChatroomId(Long chatroomId);

}