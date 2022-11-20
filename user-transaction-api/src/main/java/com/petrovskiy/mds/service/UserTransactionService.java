package com.petrovskiy.mds.service;

import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.RequestOrderDto;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;

import java.util.UUID;

public interface UserTransactionService extends BaseService<ResponseTransactionDto, UUID>{

    public ResponseTransactionDto create(UserTransaction requestOrderDto);

}
