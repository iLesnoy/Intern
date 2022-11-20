package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.UserTransactionDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.OrderService;
import com.petrovskiy.mds.service.PositionFeignClient;
import com.petrovskiy.mds.service.UserFeignClient;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.RequestOrderDto;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import com.petrovskiy.mds.service.mapper.UserTransactionMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTransactionServiceImpl {

    private final UserTransactionMapper mapper;
    private final OrderService orderService;
    private final UserTransactionDao transactionDao;
    private final PositionFeignClient positionService;
    private final TopicProducer topicProducer;
    private final UserFeignClient userService;
    private final PositionMapper positionMapper;

    public UserTransactionServiceImpl(UserTransactionMapper mapper, OrderService orderService, UserTransactionDao transactionDao,
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
        userTransaction.setOrder(order);
        UserDto user = userService.findById(order.getUserId());
        ResponseTransactionDto responseTransactionDto = mapper.transactionToDto(userTransaction,user,order);

        order.getPositionList().stream().map(position ->
                positionService.checkAmount(position.getId(), order.getAmount()));

        /*transactionDao.save(mapper.dtoToTransaction(responseTransactionDto,order.getPositionList()));*/

        topicProducer.send(String.valueOf(order.getAmount()));
        return responseTransactionDto;
    }



}