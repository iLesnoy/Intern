package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.service.dto.CompanyDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompanyMapper{

    CompanyDto entityToDto(Company entity);

    Company dtoToEntity(CompanyDto dto);
}
