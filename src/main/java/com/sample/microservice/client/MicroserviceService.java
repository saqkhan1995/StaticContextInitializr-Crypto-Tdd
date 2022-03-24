package com.sample.microservice.client;

import com.sample.microservice.config.properties.CommonProperties;
import com.sample.microservice.config.properties.CredentialsData;
import com.sample.microservice.exception.MicroserviceServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class MicroserviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceService.class);

    private RestTemplate restTemplate;
    private CommonProperties commonConfig;

    @Autowired
    private CredentialsData credentialsData;

    public MicroserviceService(RestTemplate restTemplate, CommonProperties commonConfig) {
        this.restTemplate = restTemplate;
        this.commonConfig = commonConfig;
    }

    public String someApiCall(String token) {

        final String userId = credentialsData.getUserId();
        LOGGER.info("userID:{}", userId);

        final String endpoint = commonConfig.getHost().concat(commonConfig.getPath().getEndpointSampleB());
        final StringBuilder uriBuilder = new StringBuilder(endpoint);
        uriBuilder.append("?userId=".concat(credentialsData.getUserId()));
        final String uri = uriBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", token);

        HttpEntity entity = new HttpEntity(headers/*,body*/);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            LOGGER.error("error :{}",e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("error :{}",e.getLocalizedMessage());
            throw e;
        }
    }
}
