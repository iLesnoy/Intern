package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<User, UserDto> {

    @Override
    UserDto entityToDto(User entity);

    @Override
    User dtoToEntity(UserDto dto);
}
