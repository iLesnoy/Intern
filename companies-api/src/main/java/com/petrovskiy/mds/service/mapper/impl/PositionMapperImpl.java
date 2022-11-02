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
        PositionDto positionDto = new PositionDto();
        positionDto.setId(position.getId());
        positionDto.setCreated_by(position.getCreated_by());
        positionDto.setCreated(position.getCreated());
        positionDto.setAmount(position.getAmount());
        positionDto.setItemDto(itemDto);
        positionDto.setCompanyDto(companyDto);
        return positionDto;
    }

    @Override
    public Position dtoToEntity(PositionDto positionDto) {
        Position position = new Position();
        position.setId(position.getId());
        position.setCreated_by(positionDto.getCreated_by());
        position.setCreated(positionDto.getCreated());
        position.setAmount(positionDto.getAmount());

        position.setCompanyId(positionDto.getCompanyDto().getId());
        position.setItemId(positionDto.getItemDto().getId());
        return position;
    }
}
