package com.example.customer_microservice.service.impl;

import com.example.customer_microservice.AppConstants.AccountsConstants;
import com.example.customer_microservice.entity.Accounts;
import com.example.customer_microservice.entity.Customer;
import com.example.customer_microservice.exception.ResourceNotFoundException;
import com.example.customer_microservice.models.AccountsDto;
import com.example.customer_microservice.models.CustomerDto;
import com.example.customer_microservice.repository.AccountsRepository;
import com.example.customer_microservice.repository.CustomerRepository;
import com.example.customer_microservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;



    @Override
    public void createAccount(CustomerDto customerDto) {

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        Customer customerEntity = new Customer();

        customerEntity.setName(customerDto.getName());
        customerEntity.setMobileNumber(customerDto.getMobileNumber());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setCreatedAt(LocalDateTime.now());
        customerEntity.setCreatedBy("");
        customerEntity.setUpdatedAt(LocalDateTime.now());
        customerEntity.setUpdatedBy("");
        Customer customer = customerRepository.save(customerEntity);

        Accounts accountsEntity = new Accounts();
        accountsEntity.setCustomerId(Long.valueOf(customer.getCustomerId()));
        accountsEntity.setAccountNumber(randomAccNumber);
        accountsEntity.setAccountType(AccountsConstants.SAVINGS);
        accountsEntity.setBranchAddress(AccountsConstants.ADDRESS);
        accountsRepository.save(accountsEntity);

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("CustomerDto","mobileNumber",mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        // Create and populate CustomerDto
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());

        // Set account details in CustomerDto
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNo(accounts.getAccountNumber().toString());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setAddress(accounts.getBranchAddress());
        customerDto.setAccountsDto(accountsDto);

        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        //boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("CustomerDto","mobileNumber", customerDto.getMobileNumber()));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "AccountNumber", customerDto.toString()));

        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setAccountNumber(Long.valueOf(accountsDto.getAccountNo()));
        accounts.setBranchAddress(accountsDto.getAddress());

        customer.setName(customerDto.getName());

        customerRepository.save(customer);
        accountsRepository.save(accounts);
        return true;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("customer","mobileNumber",mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;

    }


}
