package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.TransactionService;
import com.petrovskiy.mds.service.dto.OrderStatus;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private PositionService positionService;
    private final KafkaTemplate<String, ResponseTransactionDto> kafkaTemplate;


    @Override
    @KafkaListener(topics = "topic.transaction", groupId = "group_id")
    public void manageTransaction(UserTransaction userTransaction) {
        validateTransaction(userTransaction.getOrder()).ifPresentOrElse(order->{

        }, () -> {
            create(ResponseTransactionDto.builder()
                    .orderStatus(OrderStatus.FAILED)
                    .id(userTransaction.getId())
                    .build());
        });

    }

    public Optional<PositionDto> validateTransaction(Order order) {
        return order.getPositionList().stream().map(position ->
                positionService.findPositionById(position.getId(), order.getAmount())).findFirst();
    }

    @Override
    public void create(ResponseTransactionDto responseTransactionDto) {
        kafkaTemplate.send("responseUsertransaction",responseTransactionDto);

    }
}
