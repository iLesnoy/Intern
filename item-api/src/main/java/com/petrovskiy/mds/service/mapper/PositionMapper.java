package com.petrovskiy.mds.service.mapper;

import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.dto.PositionDto;

public interface PositionMapper {

    PositionDto entityToDto(Position position,ItemDto itemDto,CompanyDto companyDto);

    Position dtoToEntity(PositionDto dto);
}
