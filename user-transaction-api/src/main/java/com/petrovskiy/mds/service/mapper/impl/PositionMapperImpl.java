package com.petrovskiy.mds.service.mapper.impl;

import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public PositionDto entityToDto(Position position,ItemDto itemDto,CompanyDto companyDto) {
        return PositionDto.builder()
                .id(position.getId())
                .createdBy(position.getCreatedBy())
                .created(position.getCreated())
                .amount(position.getAmount())
                .itemDto(itemDto)
                .companyDto(companyDto)
                .build();
    }

    @Override
    public Position dtoToEntity(PositionDto positionDto) {
        return Position.builder()
                .id(positionDto.getId())
                .createdBy(positionDto.getCreatedBy())
                .created(positionDto.getCreated())
                .amount(positionDto.getAmount())
                .companyId(positionDto.getCompanyDto().getId())
                .itemId(positionDto.getItemDto().getId())
                .build();
    }
}
