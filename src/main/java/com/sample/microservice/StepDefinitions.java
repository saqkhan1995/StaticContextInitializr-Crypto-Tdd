package com.sample.microservice;

import com.sample.microservice.client.MicroserviceService;
import com.sample.microservice.config.RestTemplateConfiguration;
import com.sample.microservice.config.properties.CredentialsData;
import com.sample.microservice.config.properties.StaticContextInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@RequiredArgsConstructor
@SpringBootTest(classes = {MicroserviceService.class, StaticContextInitializer.class, RestTemplateConfiguration.class,
                            CredentialsData.class})
@CucumberContextConfiguration
@EnableConfigurationProperties
@Slf4j
public class StepDefinitions {

    private String token;
    private String user;
    private String response;

    @Autowired
    private MicroserviceService client;

    @Autowired
    private CredentialsData credentialsData;

    @Given("^I have a token for the user")
    public void tokenForUser() {
        token = "12eouijnv c9p3tjc4ij";
    }

    @And("^the user is initiaiized")
    public void initializeUser() {
        user = credentialsData.getUserId();
    }

    @When("^I call the api")
    public void apiCall() {
        response = client.someApiCall(token);
    }

    @Then("^the response is (.*)$$")
    public void apiCall(String code) {
        //assert response statud code == code
    }

}
