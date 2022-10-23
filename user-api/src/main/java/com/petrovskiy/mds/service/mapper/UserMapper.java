package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

public interface UserMapper{

    UserDto userToDto(User entity, CompanyDto companyDto);

    User dtoToUser(UserDto dto);
}
