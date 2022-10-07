package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        UserDto created = userService.create(userDto);
        return created;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return userDto;
    }

    @GetMapping
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        CustomPage<UserDto> userDtos = userService.findAll(pageable);
        return userDtos;
    }


}
