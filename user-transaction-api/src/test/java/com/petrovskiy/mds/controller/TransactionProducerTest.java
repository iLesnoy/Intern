package com.petrovskiy.mds.controller;


import com.petrovskiy.mds.service.dto.TransactionResult;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
public class TransactionProducerTest {

    private  KafkaTemplate<String, TransactionResult> kafkaTemplate;

    public TransactionProducerTest(KafkaTemplate<String, TransactionResult> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(TransactionResult result) {
        kafkaTemplate.send("transactionsResponse", result);
    }
}
