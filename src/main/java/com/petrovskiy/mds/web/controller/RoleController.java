package com.petrovskiy.mds.web.controller;

import com.petrovskiy.mds.service.RoleService;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.dto.RoleDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping
    public CustomPage<RoleDto> findAllRoles(CustomPageable pageable){
        return roleService.findAll(pageable);
    }

    @GetMapping("{id}")
    public RoleDto findRoleById(@PathVariable Long id){
        RoleDto roleDto = roleService.findById(id);
        return roleDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto createRole(@RequestBody RoleDto role){
        return roleService.create(role);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto updateRole(@PathVariable Long id,@RequestBody RoleDto roleDto){
        return roleService.update(id,roleDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRole(@PathVariable Long id){
        roleService.delete(id);
    }
}
