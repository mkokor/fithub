package com.fithub.services.training.dao.model;

import java.time.LocalTime;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    @NotNull(message = "The coach must not be null.")
    private CoachEntity coach;

    @Column(name = "start_time", nullable = false)
    @JoinColumn(name = "coach_id", nullable = false)
    @NotNull(message = "The start time must be specified.")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull(message = "The end time must be specified.")
    private LocalTime endTime;

    private Integer capacity;

    @Column(name = "day", nullable = false)
    @NotNull(message = "The day must be specified.")
    private String day;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<SongRequestEntity> songRequests;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;

}