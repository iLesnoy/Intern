package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "trans-users", contextId = "transUser", url = "http://localhost:8084/api")
public interface UserFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<UserDto> getUsers();

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}", produces = "application/json")
    UserDto findById(@PathVariable UUID id);
}
