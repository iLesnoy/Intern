package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.model.UserTransaction;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
public class TransactionConsumerTest {

    private UserTransaction userTransaction;

    public TransactionConsumerTest(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }

    @KafkaListener(topics = "transaction", groupId = "group_id")
    public void transactionManager(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }
}
