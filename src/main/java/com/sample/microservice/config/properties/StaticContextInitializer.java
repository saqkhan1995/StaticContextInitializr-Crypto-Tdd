package com.sample.microservice.config.properties;

import com.sample.microservice.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StaticContextInitializer {

    @Autowired
    private CredentialsData credentialsData;

    @Autowired
    private AggrData aggrData;

    @PostConstruct
    public void init() {
        DataUtils.setAggregatorData(aggrData);
        DataUtils.setCredentials(credentialsData);
    }
}
