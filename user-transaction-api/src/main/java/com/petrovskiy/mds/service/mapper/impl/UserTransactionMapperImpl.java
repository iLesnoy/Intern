package com.petrovskiy.mds.service.mapper.impl;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTransactionMapperImpl {

    @Autowired
    private OrderMapper orderMapper;

    public ResponseTransactionDto transactionToDto(UserTransaction transactionDto, UserDto userDto, Order order) {
        return ResponseTransactionDto.builder()
                .id(transactionDto.getId())
                .transactionDate(transactionDto.getTransactionDate())
                .user(userDto)
                .orderDto(orderMapper.orderToDto(order))
                .build();
    }


}



