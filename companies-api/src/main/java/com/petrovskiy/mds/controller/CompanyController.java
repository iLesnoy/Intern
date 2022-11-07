package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;

    @GetMapping("{id}")
    public CompanyDto findCompanyById(@PathVariable BigInteger id){
        return companyService.findById(id);
    }

    @GetMapping
    public Page<CompanyDto> findAllCompanies(Pageable pageable){
        return companyService.findAll(pageable);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto createCompany(@RequestBody CompanyDto company){
        return companyService.create(company);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto updateCompany(@PathVariable BigInteger id,@RequestBody CompanyDto company){
        return companyService.update(id,company);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable BigInteger id){
        companyService.delete(id);
    }

}
