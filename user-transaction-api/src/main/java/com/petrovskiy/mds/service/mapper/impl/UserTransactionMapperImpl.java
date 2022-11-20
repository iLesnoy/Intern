package com.petrovskiy.mds.service.mapper.impl;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.OrderMapper;
import com.petrovskiy.mds.service.mapper.UserTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTransactionMapperImpl implements UserTransactionMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseTransactionDto transactionToDto(UserTransaction transactionDto, UserDto userDto, Order order) {
        return ResponseTransactionDto.builder()
                .id(transactionDto.getId())
                .transactionDate(transactionDto.getTransactionDate())
                .user(userDto)
                .orderDto(orderMapper.orderToDto(order))
                .build();
    }

    @Override
    public UserTransaction dtoToTransaction(ResponseTransactionDto transactionDto,List<Position> positionList) {
        return UserTransaction.builder()
                .order(orderMapper.dtoToOrder(transactionDto.getOrderDto(),positionList))
                .build();
    }
}



