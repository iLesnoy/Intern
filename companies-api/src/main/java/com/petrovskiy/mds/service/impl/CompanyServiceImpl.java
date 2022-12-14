package com.petrovskiy.mds.service.impl;

import com.petrovskiy.mds.dao.CompanyDao;
import com.petrovskiy.mds.model.Company;
import com.petrovskiy.mds.service.CompanyService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.CustomPage;
import com.petrovskiy.mds.service.exception.SystemException;
import com.petrovskiy.mds.service.mapper.CompanyMapper;
import com.petrovskiy.mds.service.validation.PageValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_ENTITY;
import static com.petrovskiy.mds.service.exception.ExceptionCode.NON_EXISTENT_PAGE;

@Slf4j
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

    @Transactional
    @Cacheable(value = "companies", key = "#companyDto.name")
    @Override
    public CompanyDto create(CompanyDto companyDto) {
        Company company = createCompany(companyMapper.dtoToEntity(companyDto));
        log.info("created Company: "+ companyDto);
        return companyMapper.entityToDto(company);
    }

    public Company createCompany(Company company){
        return companyDao.findByName(company.getName()).orElseGet(()-> companyDao.save(company));
    }

    @Transactional
    @CachePut(value = "companies", key = "#companyDto.name")
    public CompanyDto createAndRefresh(CompanyDto companyDto) {
        Company company = createCompany(companyMapper.dtoToEntity(companyDto));
        log.info("created Company: " + companyDto);
        return companyMapper.entityToDto(company);
    }


    @Transactional
    @Override
    public CompanyDto update(BigInteger id, CompanyDto companyDto) {
        findById(id);
        Company company  = companyMapper.dtoToEntity(companyDto);
        companyDao.save(company);
        log.info("update Company: "+ companyDto);
        return companyMapper.entityToDto(company);
    }

    @Transactional(readOnly = true)
    @CachePut(value = "companies", key = "#id")
    @Override
    public CompanyDto findById(BigInteger id) {
        Company company = companyDao.findById(id).orElseThrow(() -> new SystemException(NON_EXISTENT_ENTITY));
        log.info("founded Company: "+ company);
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
    @CacheEvict("companies")
    public void delete(BigInteger id) {
        companyDao.findById(id).ifPresentOrElse(position -> companyDao.deleteById(id)
                ,()->new SystemException(NON_EXISTENT_ENTITY));
        log.info("Company deleted by id: "+ id);
    }
}
