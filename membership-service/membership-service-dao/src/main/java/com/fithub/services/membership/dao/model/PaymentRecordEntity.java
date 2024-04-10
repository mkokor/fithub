package com.fithub.services.membership.dao.model;

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
@Table(name = "payment_record")
public class PaymentRecordEntity {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
	
	@Column(name = "paid", nullable = false)
	@NotNull(message = "The paid status must be specified")
	private boolean paid;
	
	@ManyToOne
	@JoinColumn(name = "membership_id", nullable = false)
	private MembershipEntity membership;
	
	@Column(name = "month", nullable = false)
	@NotNull(message = "The month must be specified")
	private String month;
}
