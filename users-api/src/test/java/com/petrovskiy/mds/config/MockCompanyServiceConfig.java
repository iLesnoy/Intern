package com.petrovskiy.mds.config;

import com.petrovskiy.mds.service.dto.CompanyDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Configuration
@ActiveProfiles("eureka-test")
@RestController
public class MockCompanyServiceConfig {

    @RequestMapping("/companies")
    public List getCompanies() {
        return Collections.singletonList(new CompanyDto());
    }
}