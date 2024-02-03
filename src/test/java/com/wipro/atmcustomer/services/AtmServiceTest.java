package com.wipro.atmcustomer.services;

import com.wipro.atmcustomer.entities.BankCheque;
import com.wipro.atmcustomer.entities.BankCustomer;
import com.wipro.atmcustomer.entities.MiniStatement;
import com.wipro.atmcustomer.repositories.BankChequeRepository;
import com.wipro.atmcustomer.repositories.BankCustomerRepository;
import com.wipro.atmcustomer.repositories.MiniStatementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static com.wipro.atmcustomer.utils.Utils.*;

@ExtendWith(MockitoExtension.class)
public class AtmServiceTest {

    @InjectMocks
    private AtmService atmService;

    @Mock
    private BankCustomerRepository bankCustomerRepository;
    @Mock
    private BankChequeRepository bankChequeRepository;
    @Mock
    private MiniStatementRepository miniStatementRepository;

    @Test
    public void testLogin_ValidCredentials() {
        String cardNo = "1234567890";
        String pin = "1234";

        BankCustomer expectedCustomer = new BankCustomer();
        expectedCustomer.setUsername(cardNo);
        expectedCustomer.setAtmPin(pin);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(cardNo,pin)).thenReturn(Optional.of(expectedCustomer));

        Optional<BankCustomer> result = atmService.login(cardNo,pin);

        assertEquals(expectedCustomer,result.orElse(null));
    }

    @Test
    public void testLogin_InvalidCredentials() {
        BankCustomer expectedCustomer = new BankCustomer();
        expectedCustomer.setUsername("1234567899");
        expectedCustomer.setAtmPin("1234");

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase("1234567890","1234")).thenReturn(Optional.empty());

        Optional<BankCustomer> result = atmService.login("1234567890","1234");

        assertEquals(Optional.empty(),result);
    }

    @Test
    public void testCreateStatement(){
        BankCustomer bankCustomer = new BankCustomer();
        bankCustomer.setId(1L);
        bankCustomer.setUsername("John");

        Double amount = 100.0;
        Boolean isDeposit = true;

        ArgumentCaptor<MiniStatement> miniStatementCaptor = ArgumentCaptor.forClass(MiniStatement.class);

        when(miniStatementRepository.save(miniStatementCaptor.capture()))
                .thenReturn(new MiniStatement());


        atmService.createStatement(bankCustomer, amount, isDeposit);

        // returns the argument passed to the method
        MiniStatement capturedStatement = miniStatementCaptor.getValue();
        assertEquals(amount, capturedStatement.getAmount());
    }

    @Test
    public void testWithDrawCashFromUser_InvalidCardDetails(){
        String username = "1234567890";
        String pin = "1234";
        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username,pin)).thenReturn(Optional.empty());
        String result = atmService.withdrawCashFromUser(username,pin,10000.0);
        assertEquals(INVALID_ATM_CARD_DETAILS,result);
    }

    @Test
    public void testWithDrawCashFromUser_InsufficientBalance(){
        String username = "1234567890";
        String pin = "1234";
        double amount = 5000.0;
        BankCustomer customer = new BankCustomer();
        customer.setId(1L);
        customer.setBalance(1000.0);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username,pin)).thenReturn(Optional.of(customer));

        String result = atmService.withdrawCashFromUser(username,pin,10000.0);
        assertEquals(INSUFFICIENT_BALANCE,result);
    }

    @Test
    public void testWithDrawCashFromUser_CashWithDrawSuccessful(){
        String username = "1234567890";
        String pin = "1234";
        Double amount = 50.0;

        BankCustomer customer = new BankCustomer();
        customer.setBalance(100.0);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username,pin)).thenReturn(Optional.of(customer));

        String result = atmService.withdrawCashFromUser(username,pin,amount);

        assertEquals(amount+CASH_WITHDRAW_SUCCESSFUL, result);
        assertEquals(50.0,customer.getBalance());

        verify(bankCustomerRepository).save(customer);
    }

    @Test
    public void testGenerateMiniStatement_List(){
        String username = "1234567890";
        String pin = "1234";

        BankCustomer bankCustomer = new BankCustomer();
        bankCustomer.setId(1L);

        MiniStatement statement1 = new MiniStatement(1L, 50.0, bankCustomer, LocalDate.now(), LocalTime.now(), "WITHDRAW");
        MiniStatement statement2 = new MiniStatement(2L, 100.0, bankCustomer, LocalDate.now(), LocalTime.now(), "DEPOSIT");

        List<MiniStatement> statements = List.of(statement1,statement2);


        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username, pin))
                .thenReturn(Optional.of(bankCustomer));

        when(miniStatementRepository.findByBankCustomer(any()))
                .thenReturn(statements);

        List<MiniStatement> result = atmService.generateStatement(username, pin);

        assertEquals(statements,result);
    }

    @Test
    public void testGenerateMiniStatement_EmptyList(){
        String username = "1234567890";
        String pin = "1234";

        BankCustomer bankCustomer = new BankCustomer();
        bankCustomer.setId(1L);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username, pin))
                .thenReturn(Optional.empty());

        List<MiniStatement> result = atmService.generateStatement(username, pin);
        assertEquals(List.of(),result);
    }

    @Test
    public void testResetPin_InvalidCredential(){
        String username = "1234567890";
        String pin = "1234";

        BankCustomer customer = new BankCustomer();
        customer.setId(1L);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username,pin)).thenReturn(Optional.empty());

        String result = atmService.resetPin(username,"1234",pin);

        assertEquals(INVALID_ATM_CARD_DETAILS,result);
    }

    @Test
    public void testResetPin_ValidCredential(){
        String username = "1234567890";
        String pin = "1234";

        BankCustomer customer = new BankCustomer();
        customer.setId(1L);

        when(bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username,pin)).thenReturn(Optional.of(customer));

        String result = atmService.resetPin(username,"1234",pin);

        assertEquals(PIN_SUCCESSFULLY_CHANGED,result);

        verify(bankCustomerRepository,times(1)).save(customer);
    }

    @Test
    public void testCreateCheque_InvalidAccountDetails(){
        BankCustomer customer = new BankCustomer();
        customer.setId(1L);
        when(bankCustomerRepository.findByAccountNoIgnoreCase("1234567890")).thenReturn(Optional.empty());

        BankCheque cheque = new BankCheque();
        cheque.setAccountNo("1234567890");
        String result = atmService.createCheque(cheque);

        assertEquals(INVALID_ACCOUNT_DETAILS,result);
    }

    @Test
    public void testCreateCheque_ValidAccountDetails() {
        BankCheque cheque = new BankCheque();
        cheque.setAccountNo("1234567890");
        cheque.setAmount(100.0);
        BankCustomer customer = new BankCustomer();
        customer.setId(1L);
        customer.setBalance(1000.0);
        when(bankCustomerRepository.findByAccountNoIgnoreCase(cheque.getAccountNo())).thenReturn(Optional.of(customer));
        atmService.createStatement(customer,100.0,true);

        String result = atmService.createCheque(cheque);
        assertEquals(CHEQUE_SUCCESSFULLY_DEPOSITED,result);
    }
}