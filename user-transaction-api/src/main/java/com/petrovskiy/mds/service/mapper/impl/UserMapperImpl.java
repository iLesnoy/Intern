package com.petrovskiy.mds.service.mapper.impl;

import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import com.petrovskiy.mds.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToDto(User user, CompanyDto companyDto) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .created(user.getCreated())
                .companyDto(companyDto)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .updated(user.getUpdated())
                .build();
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .created(userDto.getCreated())
                .companyId(userDto.getCompanyDto().getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .updated(userDto.getUpdated())
                .build();
    }
}
