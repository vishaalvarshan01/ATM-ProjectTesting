package com.wipro.atmcustomer.repositories;

import com.wipro.atmcustomer.entities.BankCustomer;
import com.wipro.atmcustomer.entities.MiniStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MiniStatementRepositoryTest {

    @Autowired
    private MiniStatementRepository miniStatementRepository;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    @Test
    public void testFindByBankCustomer(){
        BankCustomer customer = new BankCustomer();
        bankCustomerRepository.save(customer);


        MiniStatement statement1 = new MiniStatement();
        statement1.setCustomer(customer);
        miniStatementRepository.save(statement1);

        MiniStatement statement2 = new MiniStatement();
        statement2.setCustomer(customer);
        miniStatementRepository.save(statement2);


        List<MiniStatement> statements = miniStatementRepository.findByBankCustomer(customer);

        assertEquals(1, statements.size());
    }
}