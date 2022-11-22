package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/reserved/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findByIdHystrix() {
        return userService.findById(UUID.fromString("ab7148c3-3226-4324-924f-05b5630f55c8"));
    }


    @PatchMapping("/{id}")
    public UserDto update(@PathVariable UUID id, @Valid UserDto userDto){
        return userService.update(id,userDto);
    }

    @GetMapping
    public Page<UserDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

}
