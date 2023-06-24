package com.example.springbootrabbitmq.config;

import com.example.springbootrabbitmq.base.params.RuntimeProfile;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * RabbitMQConfig class is a Configuration class which is responsible for defining and configuring the necessary beans
 * and components related to RabbitMQ. It typically includes settings for establishing a connection to RabbitMQ,
 * declaring queues, exchanges, and bindings, and configuring message listeners.
 *
 * @author Md Nisar Ahmed
 */
@Configuration
@Profile(RuntimeProfile.RABBITMQ)
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    public String queueName;

    @Value("${rabbitmq.exchange.name}")
    public String exchangeName;

    @Value("${rabbitmq.routing-key}")
    public String routingKey;

    @Value("${rabbitmq.json.queue.name}")
    public String jsonQueueName;

    @Value("${rabbitmq.json.exchange.name}")
    public String jsonExchangeName;

    @Value("${rabbitmq.json.routing-key}")
    public String jsonRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public TopicExchange jsonExchange() {
        return new TopicExchange(jsonExchangeName);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder.bind(jsonQueue())
                .to(jsonExchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
