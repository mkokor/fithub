package com.fithub.services.membership.dao.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "membership")
public class MembershipEntity {
		
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(updatable = false)
	    private Long id;
	 	
	 	@Column(name = "amount", nullable = false)
	 	@NotNull(message = "The amount must be specified")
	 	private double amount;
	 	
	 	@OneToOne
	    @JoinColumn(name = "client_id", nullable = false)
	    private ClientEntity client;
	 	
	 	@OneToMany(mappedBy = "membership", cascade = CascadeType.ALL)
	    private List<PaymentRecordEntity> paymentRecord;
}
