package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.base.dto.MyMessage;
import com.example.springbootrabbitmq.service.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/mq")
public class MessageController {

    private final MessageProducer messageProducer;

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<Boolean> publishMessage(@RequestParam String message) {
        Boolean isPublished = this.messageProducer.publishMessage(message);
        if (Boolean.TRUE.equals(isPublished)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PostMapping("/publish")
    public ResponseEntity<Boolean> publishMessage(@RequestBody MyMessage message) {
        Boolean isPublished = this.messageProducer.publishMessage(message);
        if (Boolean.TRUE.equals(isPublished)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
}
