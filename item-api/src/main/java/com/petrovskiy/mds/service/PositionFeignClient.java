package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.PositionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "item-position", contextId = "itemsPosition" ,url = "http://localhost:8081/api")
public interface PositionFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/positions")
    List<PositionDto> getPositions();

    @RequestMapping(method = RequestMethod.GET, value = "/positions/{id}", produces = "application/json")
    PositionDto findById(@PathVariable BigInteger id);

    @RequestMapping(method = RequestMethod.GET, value = "/positions/amount/{id}/{amount}", produces = "application/json")
    PositionDto checkAmount(@PathVariable BigInteger id, @PathVariable BigDecimal amount);
}
