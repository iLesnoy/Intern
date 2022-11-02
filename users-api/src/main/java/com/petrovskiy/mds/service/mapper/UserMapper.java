package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;

public interface UserMapper{

    UserDto userToDto(User entity, CompanyDto companyDto);

    User dtoToUser(UserDto dto);
}
