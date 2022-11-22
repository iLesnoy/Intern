package com.petrovskiy.mds.service.mapper.impl;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.RequestOrderDto;
import com.petrovskiy.mds.service.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class OrderMapperImpl implements OrderMapper {


    @Override
    public RequestOrderDto orderToDto(Order order) {
        return RequestOrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .positionList(order.getPositionList()
                        .stream().map(Position::getId).collect(Collectors.toList()))
                .amount(order.getAmount())
                .build();
    }

    @Override
    public Order dtoToOrder(RequestOrderDto dto, List<Position> positionList) {
        return Order.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .positionList(positionList)
                .build();
    }
}
