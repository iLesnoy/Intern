package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.PositionDao;
import com.petrovskiy.mds.model.Position;
import com.petrovskiy.mds.service.ItemFeignClient;
import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.ItemDto;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionDao positionDao;
    private final PositionMapper positionMapper;
    private final ItemFeignClient itemService;
    private final CompanyServiceImpl companyService;

    @Autowired
    public PositionServiceImpl(PositionDao positionDao, PositionMapper positionMapper,
                               ItemFeignClient itemService,CompanyServiceImpl companyService) {
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

        Position position  =  positionDao.save(positionMapper.dtoToEntity(positionDto));
        return positionMapper.entityToDto(position,itemDto,companyDto);
    }


    @Transactional
    @Override
    public PositionDto update(BigInteger id, PositionDto positionDto) {
        Position position = positionDao.save(positionMapper.dtoToEntity(findById(id)));
        ItemDto itemDto = itemService.findById(position.getItemId());
        CompanyDto companyDto = companyService.findById(positionDto.getCompanyDto().getId());

        return positionMapper.entityToDto(position,itemDto,companyDto);
    }

    @Override
    public PositionDto findById(BigInteger id) {
        Position position = positionDao.findById(id).orElseThrow(()->new SystemException(NON_EXISTENT_ENTITY));
        ItemDto itemDto = itemService.findById(position.getItemId());
        CompanyDto companyDto = companyService.findById(position.getCompanyId());

        return positionMapper.entityToDto(position,itemDto,companyDto);
    }

    @Override
    public Page<PositionDto> findAll(Pageable pageable) {
        Page<Position> orderPage = positionDao.findAll(pageable);
        /*if(!validator.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }*/
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(position -> {
                    ItemDto itemDto = itemService.findById(position.getItemId());
                    CompanyDto companyDto = companyService.findById(position.getId());
                    companyService.findById(position.getCompanyId());

                    return positionMapper.entityToDto(position,itemDto,companyDto);
                });
    }

    @Override
    public void delete(BigInteger id) {
        positionDao.findById(id).ifPresentOrElse(position -> positionDao.deleteById(id)
                ,()->new SystemException(NON_EXISTENT_ENTITY));

    }
}
