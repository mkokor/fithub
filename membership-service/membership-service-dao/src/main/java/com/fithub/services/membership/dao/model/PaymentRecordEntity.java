package com.fithub.services.membership.dao.model;

import java.time.LocalDateTime;

import com.fithub.services.membership.dao.validation.annotation.MonthOfYear;

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
@Table(name = "payment_record")
public class PaymentRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    @Min(2024)
    @NotNull(message = "The year of the payment record must be specified.")
    private Integer year;

    @Column(updatable = false)
    @MonthOfYear
    @NotNull(message = "The month of the payment record must be specified.")
    private String month;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    @NotNull(message = "The paid status must be specified.")
    private Boolean paid;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    private MembershipEntity membership;

}