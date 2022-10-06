package com.petrovskiy.mds.service.dto;

import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String username;
    private String name;
    private Company company;
    private LocalDateTime created;
    private LocalDateTime updated;

    private String email;
    private Set<Role> role;
}
