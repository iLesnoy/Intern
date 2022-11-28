package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "transaction", groupId = "group_id")
    public void consume(ConsumerRecord<String, UserTransaction> payload){
        transactionService.manageTransaction(payload.value());
        log.info("Topic: {}", "transaction");
        log.info("Headers: {}", payload.headers());
        log.info("Order: {}", payload.value());
    }

}