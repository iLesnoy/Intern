package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.User;
import com.petrovskiy.mds.service.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CompanyMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends BaseMapper<User, UserDto> {

    @Mapping(source = "company", target = "companyDto")
    @Override
    UserDto entityToDto(User entity);

    @Mapping(source = "companyDto", target = "company")
    @Override
    User dtoToEntity(UserDto dto);
}
