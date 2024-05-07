package com.fithub.services.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class PostEventFilter extends AbstractGatewayFilterFactory<PostEventFilter.Config> {

    public static class Config {
    }

    public PostEventFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(PostEventFilter.Config config) {
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println(exchange.getResponse().getStatusCode());
        })));
    }

}