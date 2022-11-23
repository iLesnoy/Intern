package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.service.dto.TransactionResult;
import com.petrovskiy.mds.service.exception.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;


@RequiredArgsConstructor
public class TopicConsumer {

    private final UserTransactionDao transactionDao;

    @KafkaListener(topics = "transactionResponse", groupId = "transactionResponse_id")
    public void consumeUserTransactionsResult(TransactionResult transactionResult) {
        transactionDao.findById(transactionResult.getId()).ifPresentOrElse(transaction->{
            transaction.setOrderStatus(transactionResult.getHttpStatus());
            transactionDao.save(transaction);
        },()->{
            throw new SystemException(NON_EXISTENT_ENTITY);
        });
    }
}
