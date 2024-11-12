package com.example.customer_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private String statusCode;
    private  String statusMessage;
}
