package org.example.contentservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_CONTENT = "content-events-exchange";
    public static final String QUEUE_JOIN = "room-join-queue";
    public static final String QUEUE_LEAVE = "room-leave-queue";
    public static final String ROUTING_KEY_CONTENT = "content.created";

    @Bean
    public Queue joinQueue() {
        return new Queue(QUEUE_JOIN);
    }

    @Bean
    public Queue leaveQueue() {
        return new Queue(QUEUE_LEAVE);
    }

    @Bean
    public TopicExchange contentExchange() {
        return new TopicExchange(EXCHANGE_CONTENT);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
