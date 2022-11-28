package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, UserTransaction> kafkaTemplate;

    public void send(UserTransaction message){
        log.info("Send MESSAGE: {}" ,message);
        kafkaTemplate.send(topicName, message);
    }

}