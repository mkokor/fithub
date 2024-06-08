package com.fithub.services.gateway.core.helper;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fithub.services.gateway.core.model.EventStatus;
import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class SystemEventsHelper {

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;

    public void sendActionLogRequest(HttpMethod actionType, EventStatus responseType, String impactedService, String impactedResource) {
        ActionLogRequest actionLogRequest = ActionLogRequest.newBuilder().setMicroserviceName(impactedService)
                .setActionType(actionType.toString().toUpperCase()).setResourceTitle(impactedResource)
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