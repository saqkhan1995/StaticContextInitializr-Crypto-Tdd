package com.sample.microservice.config;

import com.sample.microservice.config.properties.AggrData;
import com.sample.microservice.config.properties.CommonProperties;
import com.sample.microservice.config.properties.CredentialsData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableRetry
@EnableConfigurationProperties
public class CommonTestConfig {

    @Bean("commonConfig")   //can ignore bean name "commonConfig", obly require if we have multiple beans of same type, then we need to differentiate
    @ConfigurationProperties(prefix = "common")
    private CommonProperties commonProperties() {
        return new CommonProperties();
    }

    @Bean("credentialsData")
    @ConfigurationProperties(prefix = "credentials")
    private CredentialsData credentialsData() {
        return new CredentialsData();
    }

    @Bean("aggrData")
    @ConfigurationProperties(prefix = "aggregator")
    private AggrData aggrData() {
        return new AggrData();
    }


}
