package com.fithub.services.training.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.client-registration-training}")
    private String clientRegistrationQueueTitle;

    @Value("${rabbitmq.queue.coach-capacity-update-auth}")
    private String coachCapacityUpdateAuthQueueTitle;

    @Value("${rabbitmq.queue.coach-capacity-update-training}")
    private String coachCapacityUpdateTrainingQueueTitle;

    @Value("${rabbitmq.exchange.direct}")
    private String directExchangeTitle;

    @Bean
    public Queue clientRegistrationQueue() {
        return new Queue(clientRegistrationQueueTitle, true);
    }

    @Bean
    public Queue coachCapacityUpdateAuthQueue() {
        return new Queue(coachCapacityUpdateAuthQueueTitle, true);
    }

    @Bean
    public Queue coachCapacityUpdateTrainingQueue() {
        return new Queue(coachCapacityUpdateTrainingQueueTitle, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeTitle);
    }

    @Bean
    public Binding binding(Queue clientRegistrationQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationQueue).to(directExchange).with(clientRegistrationQueueTitle);
    }

    @Bean
    public Binding bindingCoachCapacityUpdateAuthQueue(Queue coachCapacityUpdateAuthQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(coachCapacityUpdateAuthQueue).to(directExchange).with(coachCapacityUpdateAuthQueueTitle);
    }

    @Bean
    public Binding bindingCoachCapacityUpdateTrainingQueue(Queue coachCapacityUpdateTrainingQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(coachCapacityUpdateTrainingQueue).to(directExchange).with(coachCapacityUpdateTrainingQueueTitle);
    }

}