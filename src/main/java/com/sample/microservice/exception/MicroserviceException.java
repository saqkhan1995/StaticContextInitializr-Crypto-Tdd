package com.sample.microservice.exception;

public class MicroserviceException extends RuntimeException{


    public MicroserviceException(final String message) {
        super(message);
    }

}
