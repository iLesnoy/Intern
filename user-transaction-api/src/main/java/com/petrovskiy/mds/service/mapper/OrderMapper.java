package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.RequestOrderDto;

import java.util.List;

public interface OrderMapper {

    RequestOrderDto orderToDto(Order order);

    Order dtoToOrder(RequestOrderDto dto, List<Position> positionDto);
}
