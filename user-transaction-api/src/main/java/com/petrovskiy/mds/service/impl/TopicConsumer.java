package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.service.dto.TransactionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class TopicConsumer {

    private final UserTransactionDao transactionDao;

    @KafkaListener(topics = "transactionResponse", groupId = "transactionResponse_id")
    public void consumeUserTransactionsResult(ConsumerRecord<String, TransactionResult> transactionResult) {
        log.info("Receive Message {}",transactionResult);
        transactionDao.findById(transactionResult.value().getId()).ifPresent(transaction->{
            transaction.setOrderStatus(transactionResult.value().getOrderStatus());
            log.info("Save transaction {}",transaction);
            transactionDao.save(transaction);
        });
    }
}
