package com.example.springbootrabbitmq.service.consumer;

import com.example.springbootrabbitmq.base.dto.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MessageConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void receiveMessage(Message message) {
        byte[] body = message.getBody();
        String receivedMessage = new String(body);
        log.info("MyMessage Received Successfully! :: {}", receivedMessage);
    }

    @RabbitListener(queues = {"${rabbitmq.json.queue.name}"})
    public void receiveMessage(MyMessage message) {
        log.info("MyMessage Received Successfully! :: {}", message.toString());
    }
}
