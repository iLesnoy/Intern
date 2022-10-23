package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Category;
import com.petrovskiy.mds.service.dto.CategoryDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper{

    CategoryDto entityToDto(Category entity);

    Category dtoToEntity(CategoryDto dto);
}
