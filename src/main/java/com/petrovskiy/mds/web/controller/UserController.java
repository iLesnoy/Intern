package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable UUID id, @Valid UserDto userDto){
        return userService.update(id,userDto);
    }

    @GetMapping
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        return userService.findAll(pageable);
    }

}
