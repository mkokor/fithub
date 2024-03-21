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
import lombok.Data;

@Entity
@Data
@Table(name = "chatrooms")
public class ChatroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "admin", nullable = false)
    private CoachEntity admin;
    
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

}