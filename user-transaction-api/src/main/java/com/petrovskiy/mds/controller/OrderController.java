package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.UserTransaction;
import com.petrovskiy.mds.service.OrderService;
import com.petrovskiy.mds.service.dto.RequestOrderDto;
import com.petrovskiy.mds.service.dto.ResponseTransactionDto;
import com.petrovskiy.mds.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody @Valid RequestOrderDto transactionDto) {
        return orderService.create(transactionDto);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @PatchMapping("/{id}")
    public Order update(@PathVariable UUID id,@RequestBody RequestOrderDto orderDto) {
        return orderService.update(id,orderDto);
    }

    @GetMapping
    public Page<Order> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        orderService.delete(id);
    }
}
