package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.PositionDaoImpl;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.dto.*;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionDaoImpl positionDao;
    private final PositionMapper positionMapper;
    private final ItemServiceImpl itemService;
    private final CompanyServiceImpl companyService;

    @Autowired
    public PositionServiceImpl(PositionDaoImpl positionDao, PositionMapper positionMapper,
                               ItemServiceImpl itemService,CompanyServiceImpl companyService) {
        this.positionDao = positionDao;
        this.positionMapper = positionMapper;
        this.itemService = itemService;
        this.companyService = companyService;
    }


    @Transactional
    @Override
    public PositionDto create(PositionDto positionDto) {
        ItemDto itemDto = itemService.findById(positionDto.getItemDto().getId());
        CompanyDto companyDto = companyService.findById(positionDto.getCompanyDto().getId());

        setDataToNewPosition(positionDto,itemDto,companyDto);

        Position position  =  positionDao.create(positionMapper.dtoToEntity(positionDto));
        return positionMapper.entityToDto(position);
    }

    private void setDataToNewPosition(PositionDto positionDto, ItemDto itemDto,
                                      CompanyDto companyDto){
        positionDto.setItemDto(itemDto);
        positionDto.setCompanyDto(companyDto);
    }

    @Transactional
    @Override
    public PositionDto update(BigInteger id, PositionDto positionDto) {
        Position position = positionDao.update(id,positionMapper.dtoToEntity(findById(id)));
        return positionMapper.entityToDto(position);
    }

    @Override
    public PositionDto findById(BigInteger id) {
        Position position = positionDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        return positionMapper.entityToDto(position);
    }

    @Override
    public CustomPage<PositionDto> findAll(CustomPageable pageable) {
        long userNumber = positionDao.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<PositionDto> userDtoList = positionDao.findAll(offset, pageable.getSize())
                .stream()
                .map(positionMapper::entityToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(BigInteger id) {
        positionDao.findById(id).ifPresentOrElse(positionDao::deleteById
                ,()->new SystemException(NON_EXISTENT_ENTITY));

    }
}
