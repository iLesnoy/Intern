package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "user-company", contextId = "second", url = "http://localhost:8081/api")
public interface CompanyFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/companies")
    List<CompanyDto> getCompanies();

    @RequestMapping(method = RequestMethod.GET, value = "/companies/{id}", produces = "application/json")
    CompanyDto findById(@PathVariable Long id);

}
