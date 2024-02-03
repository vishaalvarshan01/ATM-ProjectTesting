package com.wipro.atmcustomer.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class BankCheque {

    public BankCheque() {
    }

    public BankCheque(Long id, String accountNo, String chequeNo, String assignDate, String expireDate, Double amount) {
        this.id = id;
        this.accountNo = accountNo;
        this.chequeNo = chequeNo;
        this.assignDate = assignDate;
        this.expireDate = expireDate;
        this.amount = amount;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "account number cannot be null")
    private String accountNo;
    @NotNull(message = "cheque number cannot be null")
    private String chequeNo;
    @NotNull(message = "assign date number cannot be null")
    private String assignDate;
    @NotNull(message = "expire date cannot be null")
    private String expireDate;
    @NotNull(message = "amount cannot be null")
    private Double amount;



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

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
