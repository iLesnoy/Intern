package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.PositionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ItemMapper.class, CompanyMapper.class}
        ,injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PositionMapper extends BaseMapper<Position, PositionDto>{

    @Mapping(source = "item", target = "itemDto")
    @Mapping(source = "company", target = "companyDto")
    @Override
    PositionDto entityToDto(Position entity);

    @Mapping(source = "itemDto", target = "item")
    @Mapping(source = "companyDto", target = "company")
    @Override
    Position dtoToEntity(PositionDto dto);
}
