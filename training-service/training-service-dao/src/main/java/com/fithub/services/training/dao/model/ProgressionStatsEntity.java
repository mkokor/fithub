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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "progression_stats")
public class ProgressionStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "The creation date of a progression stats must be specified.")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @NotNull(message = "The creator must be specified.")
    private CoachEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "The client must be specified.")
    private ClientEntity client;

    private Double weight;

    @JoinColumn(name = "pr_stats")
    private String prStats;

}