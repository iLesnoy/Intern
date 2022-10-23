package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.dto.PositionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

public interface PositionMapper{

    PositionDto entityToDto(Position entity, ItemDto itemDto, CompanyDto companyDto);

    Position dtoToEntity(PositionDto dto);
}
