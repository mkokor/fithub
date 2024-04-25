package com.fithub.services.systemevents.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ActionLoggerService extends ActionLoggerServiceGrpc.ActionLoggerServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(ActionLoggerService.class);

    public void logMessage(ActionLogRequest actionLogRequest) {
        MDC.put("service", actionLogRequest.getMicroserviceName());
        MDC.put("response", actionLogRequest.getResponseType());
        MDC.put("action", actionLogRequest.getActionType());
        MDC.put("resource", actionLogRequest.getResourceTitle());

        logger.info("");
        MDC.clear();
    }

    @Override
    public void logAction(ActionLogRequest actionLogRequest, StreamObserver<VoidResponse> responseObserver) {
        logMessage(actionLogRequest);

        VoidResponse response = VoidResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}