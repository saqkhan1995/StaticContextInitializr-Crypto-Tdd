package com.sample.microservice.config.properties;

import lombok.Data;

import java.util.Map;

@Data
public class CredentialsData {
    private String userId;
    private String password;
    private Map<String, Credential> externalCredentials;
}
