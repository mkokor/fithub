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
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "The weigth must be specified.")
    @Min(value = 0, message = "The wight must not be negative.")
    private Double weight;

    @NotNull(message = "The height must be specified.")
    @Min(value = 0, message = "The height PR value must not be negative.")
    private Double height;

    @JoinColumn(name = "deadlift_pr")
    @Min(value = 0, message = "The deadlift PR value must not be negative.")
    private Double deadliftPr;

    @JoinColumn(name = "squat_pr")
    @Min(value = 0, message = "The squat PR value must not be negative.")
    private Double squatPr;

    @JoinColumn(name = "bench_pr")
    @Min(value = 0, message = "The bench PR value must not be negative.")
    private Double benchPr;

    @JoinColumn(name = "treadmill_pr")
    @Min(value = 0, message = "The treadmill PR value must not be negative.")
    private Double treadmillPr;

}