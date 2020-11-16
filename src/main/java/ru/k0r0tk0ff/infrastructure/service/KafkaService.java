package ru.k0r0tk0ff.infrastructure.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.k0r0tk0ff.domain.TextMessage;

import java.util.ArrayList;
import java.util.List;

@Service("KafkaService")
@ConditionalOnBean(name = "KafkaSender")
public class KafkaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    private List<TextMessage> messageList;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaService(@Qualifier("KafkaSender") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        messageList = new ArrayList<>();
    }

    public void sendMessage(String key, String message) {
        LOGGER.info("[Outgoing message >>> [key:{}] [message:{}]]", key, message);
        kafkaTemplate.send("test", key, message);
    }

    public List<TextMessage> getMessages() {

        return this.messageList;
    }

    public void flushMessages() {

        this.messageList.clear();
    }

    @KafkaListener(topics = "test", groupId = "myGroup")
    void listener(ConsumerRecord<String, String> record) {
        LOGGER.info("Message has been received: offset = {}, key = {}, value = {}",
                record.offset(),
                record.key(),
                record.value());
        this.messageList.add(new TextMessage.Builder()
                .withKey(record.key())
                .withMessageBody(record.value())
                .build());
    }
}
