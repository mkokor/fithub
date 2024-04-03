package com.fithub.services.mealplan.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(updatable = false)
    private String uuid;

    @Column(name = "first_name", nullable = false)
    @Size(min = 1, message = "Min size of the name must be at least 1 character.")
    @Size(max = 15, message = "Max size of the name cannot be more than 250 character.")
    @NotNull(message = "First name must be specified.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 1, message = "Min size of the name must be at least 1 character.")
    @Size(max = 15, message = "Max size of the name cannot be more than 250 character.")
    @NotNull(message = "Last name must be specified.")
    private String lastName;

   
    @OneToOne(mappedBy = "user")
    private ClientEntity client;

    @OneToOne(mappedBy = "user")
    private CoachEntity coach;



}