package com.sample.microservice.config.properties;

import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class CommonProperties {
    private String testType;
    private String host;
    private String clientId;
    private Path path;
    private MultiValueMap<String, String> authHeaders;
}
