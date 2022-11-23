package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.PositionDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.TransactionService;
import com.petrovskiy.mds.service.dto.OrderStatus;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private PositionService positionService;
    private PositionDao positionDao;
    private final KafkaTemplate<String, TransactionResult> kafkaTemplate;


    @Override
    @KafkaListener(topics = "transaction", groupId = "group_id")
    public void manageTransaction(UserTransaction userTransaction) {
        validateTransaction(userTransaction.getOrder()).ifPresentOrElse(order -> {
            userTransaction.getOrder().getPositionList()
                    .forEach(position -> positionDao.updatePositionAmount
                            (position.getId(), position.getAmount()));
            send(TransactionResult.builder()
                    .orderStatus(OrderStatus.ACCEPTED)
                    .id(userTransaction.getId())
                    .build());

        }, () -> {
            send(TransactionResult.builder()
                    .orderStatus(OrderStatus.FAILED)
                    .id(userTransaction.getId())
                    .build());
        });

    }

    @Override
    public Optional<PositionDto> validateTransaction(Order order) {
        return order.getPositionList().stream().map(position ->
                positionService.findPositionById(position.getId(), order.getAmount())).findFirst();
    }

    @Override
    public void send(TransactionResult transactionResult) {
        kafkaTemplate.send("transactionResponse", transactionResult);

    }
}
