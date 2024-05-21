package com.fithub.services.auth.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.client-registration}")
    private String clientRegistrationQueueTitle;

    @Value("${rabbitmq.exchange.direct}")
    private String directExchangeTitle;

    @Bean
    public Queue clientRegistrationQueue() {
        return new Queue(clientRegistrationQueueTitle, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeTitle);
    }

    @Bean
    public Binding binding(Queue clientRegistrationQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationQueue).to(directExchange).with(clientRegistrationQueueTitle);
    }

}