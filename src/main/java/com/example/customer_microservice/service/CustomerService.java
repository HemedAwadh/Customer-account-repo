package com.example.customer_microservice.service;

import com.example.customer_microservice.models.CustomerDto;

public interface CustomerService {

     void createAccount(CustomerDto customer);

     CustomerDto fetchAccount (String mobileNumber);

     boolean updateAccount(CustomerDto customer);

     boolean deleteAccount(String mobileNumber);
}
