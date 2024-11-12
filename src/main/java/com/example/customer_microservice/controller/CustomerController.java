package com.example.customer_microservice.controller;

import com.example.customer_microservice.AppConstants.AccountsConstants;
import com.example.customer_microservice.entity.Customer;
import com.example.customer_microservice.models.CustomerDto;
import com.example.customer_microservice.models.Response;
import com.example.customer_microservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount (@RequestBody CustomerDto customer){
         customerService.createAccount(customer);
         return ResponseEntity.status(HttpStatus.CREATED).body(new Response(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccountDetails(@RequestParam (value = "mobileNumber") String mobileNumber){
        CustomerDto customerDto =customerService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?>updateAccountDetails(@RequestBody CustomerDto customer){
        boolean isUpdated = customerService.updateAccount(customer);
        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new Response(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccountDetails(@RequestParam String mobileNumber){
        boolean isDeleted = customerService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new Response(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }
}
