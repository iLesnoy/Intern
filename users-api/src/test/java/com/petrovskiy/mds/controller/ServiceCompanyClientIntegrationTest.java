package com.petrovskiy.mds.controller;

import com.netflix.discovery.EurekaClient;
import com.petrovskiy.mds.UserApiApplication;
import com.petrovskiy.mds.config.EurekaContainerConfig;
import com.petrovskiy.mds.config.MockCompanyServiceConfig;
import com.petrovskiy.mds.service.CompanyFeignClient;
import com.petrovskiy.mds.service.dto.CompanyDto;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ActiveProfiles("eureka-test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserApiApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { MockCompanyServiceConfig.class },
        initializers = { EurekaContainerConfig.Initializer.class })
public class ServiceCompanyClientIntegrationTest {

    @Autowired
    private CompanyFeignClient companyClient;

    @Lazy
    @Autowired
    private EurekaClient eurekaClient;

    @BeforeEach
    void setUp() {
        await().atMost(60, SECONDS).until(() -> eurekaClient.getApplications().size() > 0);
    }

    @Test
    public void whenGetBooks_thenTheCorrectBooksAreReturned() {
        List companies = companyClient.getCompanies();

        assertEquals(5, companies.size());
        assertEquals(
                new CompanyDto(), companies.stream().findFirst().get());
    }

}
