package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.service.dto.ItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class}
        ,injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemMapper extends BaseMapper<Item, ItemDto>{

    @Mapping(source = "category", target = "categoryDto")
    @Override
    ItemDto entityToDto(Item entity);

    @Mapping(source = "categoryDto", target = "category")
    @Override
    Item dtoToEntity(ItemDto dto);
}
