package com.fithub.services.chat.dao.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "chatrooms")
public class ChatroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(unique = true, name = "room_name", nullable = false)
    @NotNull(message = "Chatroom name must not be blank.")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "admin", nullable = false)
    @NotNull(message = "The admin must not be blank.")
    private CoachEntity admin;
    
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

}