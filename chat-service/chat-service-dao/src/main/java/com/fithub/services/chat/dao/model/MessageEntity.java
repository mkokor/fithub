package com.fithub.services.chat.dao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "created", nullable = true)
    private LocalDateTime created;
    
    @Column(name = "content", nullable = false)
    @NotNull(message = "The content must not be blank.")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "The user who created the message must not be blank.")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @NotNull(message = "The chatroom must not be blank.")
    private ChatroomEntity chatroom;

}