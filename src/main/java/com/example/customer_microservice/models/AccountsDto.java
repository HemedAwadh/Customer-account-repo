package com.example.customer_microservice.models;

import lombok.Data;

@Data
public class AccountsDto {
    private String accountNo;
    private String accountType;
    private String address;
}
