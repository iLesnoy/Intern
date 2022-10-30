package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "company-item", contextId="first", url = "http://localhost:8082/api")
public interface ItemFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/items")
    List<ItemDto> getItems();

    @RequestMapping(method = RequestMethod.GET, value = "/items/{id}", produces = "application/json")
    ItemDto findById(@PathVariable UUID id);

}