package com.example.springbootrabbitmq.service.producer;

import com.example.springbootrabbitmq.base.dto.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.json.exchange.name}")
    private String jsonExchangeName;

    @Value("${rabbitmq.json.routing-key}")
    private String jsonRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean publishMessage(String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
            log.info("MyMessage published successfully! :: {}", message);
            return true;
        } catch (Exception e) {
            log.info("Failed to publish MyMessage! :: {}", message);
            return false;
        }
    }

    public Boolean publishMessage(MyMessage message) {
        try {
            rabbitTemplate.convertAndSend(jsonExchangeName, jsonRoutingKey, message);
            log.info("MyMessage published successfully! :: {}", message);
            return true;
        } catch (Exception e) {
            log.info("Failed to publish MyMessage! :: {}", message.toString());
            return false;
        }
    }
}
