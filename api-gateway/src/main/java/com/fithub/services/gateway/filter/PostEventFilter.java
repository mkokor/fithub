package com.fithub.services.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;

import com.fithub.services.gateway.core.helper.SystemEventsHelper;
import com.fithub.services.gateway.core.model.EventStatus;

import reactor.core.publisher.Mono;

@Component
public class PostEventFilter extends AbstractGatewayFilterFactory<PostEventFilter.Config> {

    public static class Config {
    }

    private final SystemEventsHelper systemEventsHelper;

    public PostEventFilter(SystemEventsHelper systemEventsHelper) {
        super(Config.class);

        this.systemEventsHelper = systemEventsHelper;
    }

    private EventStatus convertToEventStatus(final HttpStatusCode httpStatusCode) {
        if (httpStatusCode.isError()) {
            return EventStatus.ERROR;
        }

        return EventStatus.SUCCESS;
    }

    private String extractServiceName(final RequestPath requestPath) {
        return requestPath.toString().trim().split("/")[1];
    }

    private String extractResourceName(final RequestPath requestPath) {
        return requestPath.toString().trim().split("/")[2];
    }

    @Override
    public GatewayFilter apply(PostEventFilter.Config config) {
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            final HttpStatusCode httpStatusCode = exchange.getResponse().getStatusCode();

            final HttpMethod actionType = exchange.getRequest().getMethod();
            final EventStatus eventStatus = convertToEventStatus(httpStatusCode);
            final String serviceName = extractServiceName(exchange.getRequest().getPath());
            final String resourceName = extractResourceName(exchange.getRequest().getPath());

            systemEventsHelper.sendActionLogRequest(actionType, eventStatus, serviceName, resourceName);
        })));
    }

}