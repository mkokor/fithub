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

    @Value("${rabbitmq.queue.client-registration-training}")
    private String clientRegistrationTrainingQueueTitle;

    @Value("${rabbitmq.queue.client-registration-membership}")
    private String clientRegistrationMembershipQueueTitle;

    @Value("${rabbitmq.queue.client-registration-mealplan}")
    private String clientRegistrationMealplanQueueTitle;

    @Value("${rabbitmq.queue.client-registration-chat}")
    private String clientRegistrationChatQueueTitle;

    @Value("${rabbitmq.queue.coach-capacity-update-training}")
    private String coachCapacityUpdateTrainingQueueTitle;

    @Value("${rabbitmq.queue.coach-capacity-update-auth}")
    private String coachCapacityUpdateAuthQueueTitle;

    @Value("${rabbitmq.exchange.direct}")
    private String directExchangeTitle;

    @Bean
    public Queue clientRegistrationTrainingQueue() {
        return new Queue(clientRegistrationTrainingQueueTitle, true);
    }

    @Bean
    public Queue clientRegistrationMembershipQueue() {
        return new Queue(clientRegistrationMembershipQueueTitle, true);
    }

    @Bean
    public Queue clientRegistrationMealplanQueue() {
        return new Queue(clientRegistrationMealplanQueueTitle, true);
    }

    @Bean
    public Queue clientRegistrationChatQueue() {
        return new Queue(clientRegistrationChatQueueTitle, true);
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
    public Binding bindingClientRegistrationTrainingQueue(Queue clientRegistrationTrainingQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationTrainingQueue).to(directExchange).with(clientRegistrationTrainingQueueTitle);
    }

    @Bean
    public Binding bindingClientRegistrationMembershipQueue(Queue clientRegistrationMembershipQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationMembershipQueue).to(directExchange).with(clientRegistrationMembershipQueueTitle);
    }

    @Bean
    public Binding bindingClientRegistrationMealplanQueue(Queue clientRegistrationMealplanQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationMealplanQueue).to(directExchange).with(clientRegistrationMealplanQueueTitle);
    }

    @Bean
    public Binding bindingClientRegistrationChatQueue(Queue clientRegistrationChatQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(clientRegistrationChatQueue).to(directExchange).with(clientRegistrationChatQueueTitle);
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