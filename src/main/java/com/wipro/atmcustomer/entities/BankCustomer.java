package com.wipro.atmcustomer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class BankCustomer {

    public BankCustomer() {
    }

    public BankCustomer(Long id, String accountNo, String username, String name, String atmPin, String branchName, String branchAddress, String branchIfscCode, Double balance) {
        this.id = id;
        this.accountNo = accountNo;
        this.username = username;
        this.name = name;
        this.atmPin = atmPin;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchIfscCode = branchIfscCode;
        this.balance = balance;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "account number cannot be null")
    private String accountNo;
    @NotNull(message = "username cannot be null")
    private String username;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "atm pin cannot be null")
    private String atmPin;
    @NotNull(message = "branch name cannot be null")
    private String branchName;
    @NotNull(message = "branch address cannot be null")
    private String branchAddress;

    @NotNull(message = "ifsc code cannot be null")
    private String branchIfscCode;

    @NotNull(message = "balance cannot be null")
    private Double balance;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAtmPin() {
        return atmPin;
    }

    public void setAtmPin(String atmPin) {
        this.atmPin = atmPin;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchIfscCode() {
        return branchIfscCode;
    }

    public void setBranchIfscCode(String branchIfscCode) {
        this.branchIfscCode = branchIfscCode;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
/*
INSERT INTO `atmsystemclient`.`bank_customer`
(`account_no`, `atm_pin`, `balance`, `branch_address`, `branch_ifsc_code`, `branch_name`, `name`, `username`)
VALUES ('123322112233', '1122', '3000', 'Near Bank', 'SBIN0025SN', 'Mumbai Main', 'Amar Kumar', '1111111111111111');

*/