package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.PositionDao;
import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.TransactionService;
import com.petrovskiy.mds.service.dto.OrderStatus;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.TransactionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private PositionService positionService;
    private PositionDao positionDao;
    private UserTransactionDao transactionDao;
    private  KafkaTemplate<String, TransactionResult> kafkaTemplate;

    @Autowired
    public TransactionServiceImpl(PositionService positionService, PositionDao positionDao, UserTransactionDao transactionDao,
                                  KafkaTemplate<String, TransactionResult> kafkaTemplate) {
        this.positionService = positionService;
        this.positionDao = positionDao;
        this.transactionDao = transactionDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    @KafkaListener(topics = "transaction", groupId = "group_id")
    public void manageTransaction(UserTransaction userTransaction) {
        log.info("Received MESSAGE: {}", userTransaction);
        transactionDao.findById(userTransaction.getId()).ifPresent(transaction -> {

            validateTransaction(transaction.getOrder()).ifPresentOrElse(order -> {
                transaction.getOrder().getPositionList()
                        .forEach(position -> positionDao.updatePositionAmount
                                (position.getId(), transaction.getOrder().getAmount()));
                send(TransactionResult.builder()
                        .orderStatus(OrderStatus.ACCEPTED)
                        .id(userTransaction.getId())
                        .build());

            }, () -> {
                log.info("Received MESSAGE with Failed transaction: {}", userTransaction);
                send(TransactionResult.builder()
                        .orderStatus(OrderStatus.FAILED)
                        .id(userTransaction.getId())
                        .build());
            });
        });

    }

    @Override
    public Optional<List<PositionDto>> validateTransaction(Order order) {
        log.info("Validate Transaction");
        return Optional.of(order.getPositionList().stream().map(position ->
                positionService.findPositionById(position.getId(), order.getAmount())).collect(Collectors.toList()));
    }

    @Override
    public void send(TransactionResult transactionResult) {
        log.info("Send to topic transactionResponse: {}", transactionResult);
        kafkaTemplate.send("transactionResponse", transactionResult);
    }
}
