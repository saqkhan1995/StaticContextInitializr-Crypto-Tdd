package com.sample.microservice.config.properties;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AggrData {
    private Map<String, String> entities;
    private List<String> entityDetails;
    private Map<String, List<String>> extraEntityDetails;

}
