package com.fithub.services.membership.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fithub.services.membership.dao.model.PaymentRecordEntity;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecordEntity, Long> {

    @Query("SELECT p FROM PaymentRecordEntity p WHERE p.membership.client.id = ?1 AND p.paid = false")
    List<PaymentRecordEntity> findUnpayedRecordsByClientId(Long clientId);

}