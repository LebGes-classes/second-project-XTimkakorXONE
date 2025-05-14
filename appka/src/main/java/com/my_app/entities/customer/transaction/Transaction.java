package com.my_app.entities.customer.transaction;


import java.time.LocalDateTime;

public class Transaction {
    private Integer id;
    private int customerId;
    private int amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    public Transaction() {
    }


    public Transaction(Integer id, int customerId, int amount, String description, TransactionType type) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    
}

