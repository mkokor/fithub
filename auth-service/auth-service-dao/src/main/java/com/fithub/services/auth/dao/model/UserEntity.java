package com.fithub.services.auth.dao.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fithub.services.auth.api.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false)
    private String uuid;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "The first name must not be blank.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "The last name must not be blank.")
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "The username must not be blank.")
    private String username;

    @Column(name = "password_hash", nullable = false)
    @NotBlank(message = "The password must not be blank.")
    private String passwordHash;

    @Column(unique = true, nullable = false)
    @Email(message = "The email address is not valid.")
    private String email;

    @Column(name = "email_confirmed", nullable = false)
    private Boolean emailConfirmed;

    @OneToOne(mappedBy = "user")
    private ClientEntity client;

    @OneToOne(mappedBy = "user")
    private CoachEntity coach;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshTokenEntity> refreshTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (client == null) {
            return List.of(new SimpleGrantedAuthority(Role.COACH.getValue()));
        }

        return List.of(new SimpleGrantedAuthority(Role.CLIENT.getValue()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}