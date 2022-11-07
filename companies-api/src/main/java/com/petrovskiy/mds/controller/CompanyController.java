package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.impl.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.logging.Logger;

@RestController
@Slf4j
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;

    private Logger logger;

    @GetMapping("{id}")
    public CompanyDto findCompanyById(@PathVariable BigInteger id) {
        logger.info("findCompanyById: " + id);
        return companyService.findById(id);
    }

    @GetMapping
    public Page<CompanyDto> findAllCompanies(Pageable pageable) {
        return companyService.findAll(pageable);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto createCompany(@RequestBody CompanyDto company) {
        logger.info("@RequestBody createCompany: " + company);
        return companyService.create(company);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto updateCompany(@PathVariable BigInteger id, @RequestBody CompanyDto company) {
        logger.info("@RequestBody updateCompany: " + company);
        return companyService.update(id, company);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable BigInteger id) {
        logger.info("Delete company by id: " + id);
        companyService.delete(id);
    }

}
