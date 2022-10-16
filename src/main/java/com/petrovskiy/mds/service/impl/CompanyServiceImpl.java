package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.impl.CompanyDaoImpl;
import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.service.CompanyService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.dto.CustomPageable;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDaoImpl companyDao;
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyServiceImpl(CompanyDaoImpl companyDao, CompanyMapper companyMapper) {
        this.companyDao = companyDao;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        Company company = createCompany(companyMapper.dtoToEntity(companyDto));
        return companyMapper.entityToDto(company);
    }

    public Company createCompany(Company company){
        return companyDao.findByName(company.getName()).orElseGet(()-> companyDao.create(company));
    }


    @Transactional
    @Override
    public CompanyDto update(BigInteger id, CompanyDto companyDto) {
        Company company = companyDao.update(id, companyMapper.dtoToEntity(findById(id)));
        return companyMapper.entityToDto(company);
    }

    @Override
    public CompanyDto findById(BigInteger id) {
        Company company = companyDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        return companyMapper.entityToDto(company);
    }

    @Override
    public CustomPage<CompanyDto> findAll(CustomPageable pageable) {
        long userNumber = companyDao.findEntityNumber();
        int offset = calculateOffset(pageable);
        List<CompanyDto> userDtoList = companyDao.findAll(offset, pageable.getSize())
                .stream()
                .map(companyMapper::entityToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(BigInteger id) {
        companyDao.findById(id).ifPresentOrElse(companyDao::deleteById,
                () -> new SystemException(NON_EXISTENT_ENTITY));
    }
}
