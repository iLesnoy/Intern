package com.petrovskiy.mds.service;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.service.dto.RequestOrderDto;

import java.util.UUID;

public interface OrderService extends BaseService<Order, UUID> {
    Order create(RequestOrderDto requestOrderDto);

}
