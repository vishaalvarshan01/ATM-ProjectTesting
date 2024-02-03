package com.wipro.atmcustomer.repositories;

import com.wipro.atmcustomer.entities.BankCheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankChequeRepository extends JpaRepository<BankCheque, Long> {
}
