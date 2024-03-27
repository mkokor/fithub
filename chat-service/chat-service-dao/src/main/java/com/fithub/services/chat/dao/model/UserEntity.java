package com.fithub.services.chat.dao.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false)
    private String uuid;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "The username must not be blank.")
    private String username;

    @OneToOne(mappedBy = "user")
    private ClientEntity client;

    @OneToOne(mappedBy = "user")
    private CoachEntity coach;

}