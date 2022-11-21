package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.OrderService;
import com.petrovskiy.mds.service.PositionFeignClient;
import com.petrovskiy.mds.service.UserFeignClient;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import com.petrovskiy.mds.service.mapper.impl.UserTransactionMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserTransactionServiceImpl {

    private final UserTransactionMapperImpl mapper;
    private final OrderService orderService;
    private final UserTransactionDao transactionDao;
    private final PositionFeignClient positionService;
    private final TopicProducer topicProducer;
    private final UserFeignClient userService;
    private final PositionMapper positionMapper;

    @Autowired
    public UserTransactionServiceImpl(UserTransactionMapperImpl mapper, OrderService orderService, UserTransactionDao transactionDao,
                                      PositionFeignClient positionService,
                                      TopicProducer topicProducer, UserFeignClient userService,
                                      PositionMapper positionMapper) {
        this.mapper = mapper;
        this.orderService = orderService;
        this.transactionDao = transactionDao;
        this.positionService = positionService;
        this.topicProducer = topicProducer;
        this.userService = userService;
        this.positionMapper = positionMapper;
    }

    @Transactional
    public ResponseTransactionDto create(UserTransaction userTransaction) {

        Order order = orderService.findById(userTransaction.getOrder().getId());
        order.getPositionList().stream().map(position ->
                positionService.checkAmount(position.getId(), order.getAmount())).collect(Collectors.toList());

        UserDto user = userService.findById(order.getUserId());
        UserTransaction transaction = transactionDao.save(userTransaction);



        /*topicProducer.send(order.getPositionList());*/
        return mapper.transactionToDto(transaction,user,order);
    }



}