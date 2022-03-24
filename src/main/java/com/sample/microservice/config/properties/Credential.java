package com.sample.microservice.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credential {

    private String username;
    private String password;
}
