package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CompanyDao;
import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.service.CompanyService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CompanyMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_PAGE;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;
    private final CompanyMapper companyMapper;
    private final PageValidation validation;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, CompanyMapper companyMapper,
                              PageValidation validation) {
        this.companyDao = companyDao;
        this.companyMapper = companyMapper;
        this.validation = validation;
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        Company company = createCompany(companyMapper.dtoToEntity(companyDto));
        return companyMapper.entityToDto(company);
    }

    public Company createCompany(Company company){
        return companyDao.findByName(company.getName()).orElseGet(()-> companyDao.save(company));
    }


    @Transactional
    @Override
    public CompanyDto update(BigInteger id, CompanyDto companyDto) {
        Company company = companyDao.save(companyMapper.dtoToEntity(findById(id)));
        return companyMapper.entityToDto(company);
    }

    @Override
    public CompanyDto findById(BigInteger id) {
        Company company = companyDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        return companyMapper.entityToDto(company);
    }

    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        Page<Company> orderPage = companyDao.findAll(pageable);
        if(!validation.isPageExists(pageable,orderPage.getTotalElements())){
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        return new CustomPage<>(orderPage.getContent(), orderPage.getPageable(), orderPage.getTotalElements())
                .map(companyMapper::entityToDto);
    }

    @Override
    public void delete(BigInteger id) {
        companyDao.findById(id).ifPresentOrElse(position -> companyDao.deleteById(id)
                ,()->new SystemException(NON_EXISTENT_ENTITY));
    }
}
