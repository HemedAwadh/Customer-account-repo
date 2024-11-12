package com.example.customer_microservice.repository;

import com.example.customer_microservice.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

    Optional<Accounts> findByCustomerId(int customerId);

    void deleteByCustomerId(int customerId);

}
