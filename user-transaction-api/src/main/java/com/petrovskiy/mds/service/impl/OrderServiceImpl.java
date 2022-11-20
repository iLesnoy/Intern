package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.OrderDao;
import com.petrovskiy.mds.model.Order;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.OrderService;
import com.petrovskiy.mds.service.PositionFeignClient;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.dto.RequestOrderDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.OrderMapper;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_PAGE;

@Service
public class OrderServiceImpl implements OrderService {

    private final PageValidation validation;
    private OrderMapper orderMapper;
    private OrderDao orderDao;
    private PositionFeignClient positionService;
    private PositionMapper positionMapper;

    public OrderServiceImpl(PageValidation validation, OrderMapper orderMapper, OrderDao orderDao, PositionFeignClient positionService,
                            PositionMapper positionMapper) {
        this.validation = validation;
        this.orderMapper = orderMapper;
        this.orderDao = orderDao;
        this.positionService = positionService;
        this.positionMapper = positionMapper;
    }

    @Transactional
    public Order create(RequestOrderDto requestOrderDto) {
        List<Position> positionDto = requestOrderDto.getPositionList().stream()
                .map(positionService::findById).map(positionMapper::dtoToEntity).collect(Collectors.toList());
        Order order = orderMapper.dtoToOrder(requestOrderDto, positionDto);
        return orderDao.save(order);
    }

    @Transactional
    public Order update(UUID id, RequestOrderDto requestOrderDto) {
        Order order = orderDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        setUpdatedFields(requestOrderDto,order);
        return orderDao.save(order);
    }

    @Override
    public Order findById(UUID id) {
        return orderDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        Page<Order> orderPage = orderDao.findAll(pageable);
        if (!validation.isPageExists(pageable, orderPage.getTotalElements())) {
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements());
    }

    @Override
    public void delete(UUID id) {
        Order order = orderDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        orderDao.delete(order);
    }

    private void setUpdatedFields(RequestOrderDto requestOrderDto, Order order) {
        order.setAmount(requestOrderDto.getAmount());
        order.setUserId(requestOrderDto.getUserId());
        List<Position> positionList = requestOrderDto.getPositionList().stream()
                .map(positionService::findById)
                .map(positionMapper::dtoToEntity)
                .collect(Collectors.toCollection(ArrayList::new));
        positionList.stream().forEach(System.out::println);
        order.setPositionList(positionList);
    }

}
