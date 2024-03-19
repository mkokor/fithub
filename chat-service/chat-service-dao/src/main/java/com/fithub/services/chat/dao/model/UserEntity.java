package com.fithub.services.chat.dao.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(updatable = false)
    private String uuid;

    private String username;

    @OneToOne(mappedBy = "user")
    private ClientEntity client;

    @OneToOne(mappedBy = "user")
    private CoachEntity coach;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;

}