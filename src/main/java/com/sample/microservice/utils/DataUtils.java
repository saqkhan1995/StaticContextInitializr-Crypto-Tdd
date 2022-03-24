package com.sample.microservice.utils;

import com.sample.microservice.config.properties.AggrData;
import com.sample.microservice.config.properties.CredentialsData;

public class DataUtils {

    public static void setCredentials(CredentialsData credentials) {
        DataUtils.credentialsData = credentials;
    }

    public static void setAggregatorData(AggrData aggregatorData) {
        DataUtils.aggrData = aggregatorData;
    }

    private static CredentialsData credentialsData;
    private static AggrData aggrData;
}
