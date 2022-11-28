package com.petrovskiy.mds.service;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.TransactionResult;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    void manageTransaction(UserTransaction userTransaction);

    Optional<List<PositionDto>> validateTransaction(Order order);

    void send(TransactionResult transactionResult);
}
