package com.fithub.services.training.dao.model;

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
public class UserEntity {

    @Id
    private String uuid;

    @Column(name = "first_name", nullable = true)
    @NotBlank(message = "The first name must not be blank.")
    private String firstName;

    @Column(name = "last_name", nullable = true)
    @NotBlank(message = "The last name must not be blank.")
    private String lastName;

    @OneToOne(mappedBy = "user")
    private ClientEntity client;

    @OneToOne(mappedBy = "user")
    private CoachEntity coach;

}