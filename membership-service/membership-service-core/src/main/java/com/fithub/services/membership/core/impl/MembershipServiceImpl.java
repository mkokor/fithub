package com.fithub.services.membership.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.exception.BadRequestException;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.mapper.MembershipMapper;
import com.fithub.services.membership.mapper.PaymentRecordMapper;
import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;
import com.fithub.services.membership.api.enums.ActionType;
import com.fithub.services.membership.api.enums.ResourceType;
import com.fithub.services.membership.api.enums.ResponseType;

import io.grpc.stub.StreamObserver;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentRecordMapper paymentRecordMapper;
    private final MembershipMapper membershipMapper;
    

    @Value("${spring.application.name}")
    private String microserviceName;

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;
    
    public MembershipServiceImpl(MembershipRepository membershipRepository, UserRepository userRepository,
    		PaymentRecordRepository paymentRecordRepository, PaymentRecordMapper paymentRecordMapper,
    		MembershipMapper membershipMapper) {
    	
    	this.membershipRepository = membershipRepository;
    	this.userRepository = userRepository;
    	this.paymentRecordRepository = paymentRecordRepository;
    	this.paymentRecordMapper = paymentRecordMapper;
    	this.membershipMapper = membershipMapper;
    }

    @Override
    public List<PaymentRecordResponse> getPaymentRecord(Long membershipId) throws Exception {

        Optional<MembershipEntity> membershipEntity = membershipRepository.findById(membershipId);

        if (membershipEntity.isEmpty()) {
        	
            throw new NotFoundException("The membership with the provided ID could not be found");
        }
        return paymentRecordMapper.entitiesToDtos(membershipEntity.get().getPaymentRecord());
    }

    @Override
    public MembershipPaymentReportResponse getMembershipPaymentReport(String clientUuid) throws ApiException {
        Optional<UserEntity> userEntity = userRepository.findById(clientUuid);

        if (userEntity.isEmpty()) {
        	sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
            throw new NotFoundException("The user with the provided ID could not be found.");
        }

        ClientEntity clientEntity = userEntity.get().getClient();
        if (clientEntity == null) {
        	sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
            throw new BadRequestException("The provided ID is associated with a coach entity.");
        }

        MembershipPaymentReportResponse membershipPaymentReport = membershipMapper
                .clientEntityToMembershipPaymentReportResponse(clientEntity);
        membershipPaymentReport.setHasDebt(!paymentRecordRepository.findUnpayedRecordsByClientId(clientEntity.getId()).isEmpty());
        
        sendActionLogRequest(ActionType.GET, ResponseType.SUCCESS);
        return membershipPaymentReport;
    }
    
    private void sendActionLogRequest(ActionType actionType, ResponseType responseType) {
        ActionLogRequest actionLogRequest = ActionLogRequest.newBuilder().setMicroserviceName(microserviceName)
                .setActionType(actionType.toString()).setResourceTitle(ResourceType.MEMBERSHIP.toString())
                .setResponseType(responseType.toString()).build();

        systemEventsClient.logAction(actionLogRequest, new StreamObserver<VoidResponse>() {

            @Override
            public void onNext(VoidResponse response) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }

        });
    }

}