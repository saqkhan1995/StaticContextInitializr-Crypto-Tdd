package com.sample.microservice.config;

import com.sample.microservice.config.properties.CommonProperties;
import com.sample.microservice.config.properties.CredentialsData;
import com.sample.microservice.interceptor.RestTemplateInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties
public class RestTemplateConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfiguration.class);

    private CommonProperties commonProperties;

    public RestTemplateConfiguration(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    @Bean
    private HttpComponentsClientHttpRequestFactory getRequestFactory() {
        logger.info("Creating request factory to configure timeout");
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30_000);
        requestFactory.setConnectionRequestTimeout(30_000);
        requestFactory.setReadTimeout(30_000);
        return requestFactory;
    }

    // Use restTemplate variable names in service classes as bean names here for desired functionality

    @Bean
    public RestTemplate restTemplate() {  //with interceptor enabled
        logger.info("Creating our own restTemplate");
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(getRequestFactory());
        restTemplate.getInterceptors().add(new RestTemplateInterceptor(commonProperties));
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplateWithoutInterception() {
        logger.info("Creating our own restTemplate");
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(getRequestFactory());
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplateWitoutRedirection() {
        logger.info("Creating our own restTemplate");
        final RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(
                        HttpClientBuilder.create()
                        .disableContentCompression()
                        .disableRedirectHandling()   // without redirection (for location header in response)
                        .build()
                )
        );
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplateWithProxy() {
        logger.info("Creating our own restTemplate");
        final RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(
                        HttpClientBuilder.create()
                                .setProxy(new HttpHost("some.proxy.com", 8080))
                                .build()
                )
        );
        return restTemplate;
    }


}
