package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonPropertyOrder({"id", "name","username", "role","created","companyDto","updated","email"})
public class UserDto {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("company")
    private CompanyDto companyDto;
    @JsonProperty("created")
    private LocalDateTime created;
    @JsonProperty("updated")
    private LocalDateTime updated;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Role role;

}
