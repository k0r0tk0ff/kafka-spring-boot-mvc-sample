package ru.k0r0tk0ff.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaListenerConfig {

    Logger LOGGER = LoggerFactory.getLogger(KafkaListenerConfig.class);

/*    @KafkaListener(topics = "test", groupId = "myGroup")
    //void listener(String data) {
    void listener(ConsumerRecord<String, String> record) {
        //LOGGER.info(data);
        LOGGER.info("Message has been received: offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
    }*/

}
