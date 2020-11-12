package ru.k0r0tk0ff.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.k0r0tk0ff.domain.TextMessage;
import ru.k0r0tk0ff.infrastructure.service.KafkaService;

@RestController
public class MessageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private KafkaService service;

    @Autowired
    public MessageController(KafkaService service) {
        this.service = service;
    }

    // http://127.0.0.1:8080/sendMessage?key=KEY,message=MESSAGE
    @PostMapping(value = "/sendMessage", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<String> sendMessage(@RequestBody TextMessage textMessage) {

        LOGGER.info("[Incoming message <<< [key:{}] [messageBody:{}]]", textMessage.getKey(), textMessage.getMessageBody());
        service.sendMessage(textMessage.getKey(), textMessage.getMessageBody());

        return new ResponseEntity<>("The message has been received.", HttpStatus.OK);
    }
}
