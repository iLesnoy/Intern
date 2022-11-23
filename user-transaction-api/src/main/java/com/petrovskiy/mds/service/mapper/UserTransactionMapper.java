package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.dto.UserDto;

import java.util.List;

public interface UserTransactionMapper{

    ResponseTransactionDto transactionToDto(UserTransaction transactionDto, UserDto userDto, Order order);

    UserTransaction dtoToTransaction(ResponseTransactionDto transactionDto,List<Position> positionList);
}
