package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.OrderService;
import com.petrovskiy.mds.service.UserFeignClient;
import com.petrovskiy.mds.service.UserTransactionService;
import com.petrovskiy.mds.service.dto.OrderStatus;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.impl.UserTransactionMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTransactionServiceImpl implements UserTransactionService {

    private final UserTransactionMapperImpl mapper;
    private final OrderService orderService;
    private final UserTransactionDao transactionDao;
    private final TopicProducer topicProducer;
    private final UserFeignClient userService;

    @Autowired
    public UserTransactionServiceImpl(UserTransactionMapperImpl mapper, OrderService orderService, UserTransactionDao transactionDao,
                                      TopicProducer topicProducer, UserFeignClient userService) {
        this.mapper = mapper;
        this.orderService = orderService;
        this.transactionDao = transactionDao;
        this.topicProducer = topicProducer;
        this.userService = userService;
    }

    @Transactional
    @Override
    public ResponseTransactionDto create(UserTransaction userTransaction) {

        Order order = orderService.findById(userTransaction.getOrder().getId());
        UserDto user = userService.findById(order.getUserId());
        setOrderStatus(userTransaction);

        UserTransaction createdTransaction = transactionDao.save(userTransaction);
        topicProducer.send(createdTransaction);

        return mapper.transactionToDto(createdTransaction, user, order);
    }

    void setOrderStatus(UserTransaction userTransaction){
        userTransaction.setOrderStatus(OrderStatus.IN_PROGRESS);
    }


}