package com.fithub.services.membership.rest.paymentrecord;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.membership.api.PaymentRecordService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordPayRequest;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "payment-record", description = "Payment Record API")
@RestController
@RequestMapping(value = "payment-record", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    @Operation(summary = "Create unpayed record for a client")
    @PostMapping("/client/{uuid}")
    public ResponseEntity<PaymentRecordResponse> createUnpayedRecord(@PathVariable final String uuid) throws Exception {
        return new ResponseEntity<>(paymentRecordService.createUnpayedRecord(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Pay")
    @PutMapping
    public ResponseEntity<PaymentRecordResponse> pay(@RequestBody final PaymentRecordPayRequest paymentRecordPayRequest) throws Exception {
        return new ResponseEntity<>(paymentRecordService.setPayed(paymentRecordPayRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get all payment records for client")
    @GetMapping("/client/{uuid}")
    public ResponseEntity<List<PaymentRecordResponse>> getAll(@PathVariable final String uuid) throws ApiException {
        return new ResponseEntity<>(paymentRecordService.getAll(uuid), HttpStatus.OK);
    }

}