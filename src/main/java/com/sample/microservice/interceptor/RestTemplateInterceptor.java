package com.sample.microservice.interceptor;


import com.sample.microservice.config.properties.CommonProperties;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Getter
@Setter
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private CommonProperties commonProperties;

    private static Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

    public RestTemplateInterceptor(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        if (httpRequest.getURI().toString().equals(commonProperties.getHost().concat(commonProperties.getPath().getEndpointSampleA()))) {
            httpRequest.getHeaders().clear();
            httpRequest.getHeaders().addAll(commonProperties.getAuthHeaders());
        }

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, body);
        return response;
    }
}
