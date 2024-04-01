package com.fithub.services.training.dao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "song_requests")
public class SongRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "The creation date must not be null.")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "The client who created song request must be specified.")
    private ClientEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @NotNull(message = "The appointment must not be null.")
    private AppointmentEntity appointment;

    @Column(name = "spotify_id", nullable = false)
    @NotBlank(message = "The spotify ID of a song must not be null.")
    private String songSpotifyId;

}