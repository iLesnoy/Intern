package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "trans-item", contextId = "transItem" ,url = "http://localhost:8081/api")
public interface ItemFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/items")
    List<ItemDto> getItems();

    @RequestMapping(method = RequestMethod.GET, value = "/items/{id}", produces = "application/json")
    ItemDto findById(@PathVariable BigInteger id);


}
