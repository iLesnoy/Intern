package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.impl.UserTransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/createTransaction")
public class UserTransactionController {

    @Autowired
    private UserTransactionServiceImpl userTransactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTransactionDto create(@RequestBody UserTransaction transactionDto) {
        return userTransactionService.create(transactionDto);
    }


}
