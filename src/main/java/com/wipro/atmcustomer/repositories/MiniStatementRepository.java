package com.wipro.atmcustomer.repositories;

import com.wipro.atmcustomer.entities.MiniStatement;
import com.wipro.atmcustomer.entities.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiniStatementRepository extends JpaRepository<MiniStatement, Long> {
    List<MiniStatement> findByBankCustomer(BankCustomer bankCustomer);
}
