package com.my_app.service.customer_service;

import java.util.List;

import com.my_app.entities.customer.Customer;
import com.my_app.entities.customer.transaction.Transaction;
import com.my_app.entities.order.Order;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Integer id, Customer customer);
    void deleteCustomer(Integer id);
    List<Order> getCustomerOrders(Integer id);

    int getCustomerBalance(Integer customerId);
    int replenishBalance(Integer customerId, int amount);
    int withdrawBalance(Integer customerId, int amount);
    List<Transaction> getCustomerTransactions(Integer customerId);
    Transaction addTransaction(Integer customerId, int amount, String description);
} 