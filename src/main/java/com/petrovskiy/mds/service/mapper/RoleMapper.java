package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Role;
import com.petrovskiy.mds.service.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends BaseMapper<Role, RoleDto>{
}
