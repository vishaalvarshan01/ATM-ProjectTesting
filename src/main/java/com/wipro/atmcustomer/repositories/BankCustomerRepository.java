package com.wipro.atmcustomer.repositories;

import com.wipro.atmcustomer.entities.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankCustomerRepository extends JpaRepository<BankCustomer, Long> {
    boolean existsByUsernameIgnoreCaseAndAtmPinIgnoreCase(String username, String atmPin);
    Optional<BankCustomer> findByAccountNoIgnoreCase(String accountNo);
    Optional<BankCustomer> findByUsernameIgnoreCaseAndAtmPinIgnoreCase(String username, String atmPin);
    Optional<BankCustomer> findByUsernameIgnoreCase(String username);
}
