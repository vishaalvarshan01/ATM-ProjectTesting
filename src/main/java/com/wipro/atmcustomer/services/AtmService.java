package com.wipro.atmcustomer.services;

import com.wipro.atmcustomer.entities.MiniStatement;
import com.wipro.atmcustomer.entities.BankCheque;
import com.wipro.atmcustomer.entities.BankCustomer;
import com.wipro.atmcustomer.repositories.BankChequeRepository;
import com.wipro.atmcustomer.repositories.BankCustomerRepository;
import com.wipro.atmcustomer.repositories.MiniStatementRepository;
import com.wipro.atmcustomer.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.wipro.atmcustomer.utils.Utils.*;

import static com.wipro.atmcustomer.utils.Utils.*;

@Service
public class AtmService {

    @Autowired
    private BankCustomerRepository bankCustomerRepository;
    @Autowired
    private BankChequeRepository bankChequeRepository;
    @Autowired
    private MiniStatementRepository miniStatementRepository;

    public Optional<BankCustomer> login(String cardNo, String pin) {
        return bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(cardNo, pin);
    }
    public void createStatement(BankCustomer bankCustomer, Double amount, Boolean isDeposit){
        MiniStatement statement = new MiniStatement(null, amount, bankCustomer, LocalDate.now(), LocalTime.now(),isDeposit  ? "DEPOSITED" : "WITHDRAW");
        miniStatementRepository.save(statement);
    }

    public BankCustomer getBankCustomerIfBankCustomerExist(String username,String pin){
        Optional<BankCustomer> customerOptional = bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username, pin);
        return customerOptional.orElse(null);
    }

    @Transactional
    public String withdrawCashFromUser(String username, String atmPin, Double amount){
        BankCustomer customer = getBankCustomerIfBankCustomerExist(username,atmPin);

        if(customer == null){
            return INVALID_ATM_CARD_DETAILS;
        }
        BankCustomer bankCustomer = customer;
        if(bankCustomer.getBalance() < amount){
           return INSUFFICIENT_BALANCE;
        }
        bankCustomer.setBalance(bankCustomer.getBalance() - amount);
        bankCustomerRepository.save(bankCustomer);
        createStatement(bankCustomer, amount, false);
        return amount+ CASH_WITHDRAW_SUCCESSFUL;
    }
    public List<MiniStatement> generateStatement(String username, String atmPin){
        Optional<BankCustomer> customerOptional = bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username, atmPin);
        if(customerOptional.isEmpty()){
            return List.of();
        }
        BankCustomer bankCustomer = customerOptional.get();
        return miniStatementRepository.findByBankCustomer(bankCustomer);
    }

    public String resetPin(String username, String oldPin, String newPin){
        Optional<BankCustomer> customerOptional = bankCustomerRepository.findByUsernameIgnoreCaseAndAtmPinIgnoreCase(username, oldPin);
        if(customerOptional.isEmpty()){
            return INVALID_ATM_CARD_DETAILS;
        }
        BankCustomer bankCustomer = customerOptional.get();
        bankCustomer.setAtmPin(newPin);
        bankCustomerRepository.save(bankCustomer);
        return PIN_SUCCESSFULLY_CHANGED;
    }
    @Transactional
    public String createCheque(BankCheque bankCheque){
        Optional<BankCustomer> customerOptional = bankCustomerRepository.findByAccountNoIgnoreCase(bankCheque.getAccountNo());
        if(customerOptional.isEmpty()){
            return INVALID_ACCOUNT_DETAILS;
        }
        BankCustomer bankCustomer = customerOptional.get();
        bankCustomer.setBalance(bankCustomer.getBalance()+ bankCheque.getAmount());
        bankChequeRepository.save(bankCheque);
        bankCustomerRepository.save(bankCustomer);
        createStatement(bankCustomer, bankCheque.getAmount(), true);
        return CHEQUE_SUCCESSFULLY_DEPOSITED;
    }
}
