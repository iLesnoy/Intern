package com.petrovskiy.mds.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RibbonConfigurationTest {

    @Autowired
    private WireMockServer mockCompService;

    @Autowired
    private WireMockServer secondMockCompService;

    @Bean(name = "mockCompService", initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksService() {
        return new WireMockServer(options().dynamicPort());
    }

    @Bean(name = "secondMockCompService", initMethod = "start", destroyMethod = "stop")
    public WireMockServer secondBooksMockService() {
        return new WireMockServer(options().dynamicPort());
    }

    @Bean
    public ServerList<Server> ribbonServerList() {
        return new StaticServerList<>(
                new Server("localhost", mockCompService.port()),
                new Server("localhost", secondMockCompService.port()));
    }

}
