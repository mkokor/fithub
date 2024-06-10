package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordPayRequest;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;

public interface PaymentRecordService {

    PaymentRecordResponse createUnpayedRecord(final String clientUuid) throws ApiException;

    PaymentRecordResponse setPayed(final PaymentRecordPayRequest payRequest) throws Exception;

    List<PaymentRecordResponse> getAll(final String clientUuid) throws ApiException;

}