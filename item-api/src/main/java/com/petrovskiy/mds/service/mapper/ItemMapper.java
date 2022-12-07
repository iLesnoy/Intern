package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.dto.ItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class}
        ,injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemMapper{

    ItemDto entityToDto(Item entity);

    Item dtoToEntity(ItemDto dto);
}
