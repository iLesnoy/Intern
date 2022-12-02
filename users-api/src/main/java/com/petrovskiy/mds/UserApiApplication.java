package com.petrovskiy.mds;

import com.petrovskiy.mds.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@RibbonClient(
        name = "ping-a-server",
        configuration = RibbonConfiguration.class)
@EnableCaching
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class UserApiApplication{

    private static final String ERROR_MESSAGES_FILE = "error_messages";
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames(ERROR_MESSAGES_FILE);
        messages.setDefaultEncoding(ENCODING);
        return messages;
    }
}
