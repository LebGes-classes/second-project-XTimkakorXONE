package com.my_app.entities.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my_app.entities.customer.transaction.Transaction;
import com.my_app.entities.order.Order;

public class Customer {
    private Integer id;
    private String name;
    private int balance;
    private List<Order> orders;
    private List<Transaction> transactions;
    private String adress;
    private int totalOrders;

    public Customer() {
        this.orders = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.totalOrders = 0;
    }

    public Customer(String name, int balance, String adress) {
        this.name = name;
        this.balance = balance;
        this.orders = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.adress = adress;
        this.totalOrders = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        this.totalOrders = orders.size();
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @JsonIgnore
    public boolean hasOrders() {
        return !orders.isEmpty();
    }
    
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        this.totalOrders = orders.size();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }   

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
