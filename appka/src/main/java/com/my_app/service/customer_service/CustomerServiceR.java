package com.my_app.service.customer_service;

import java.io.IOException;
import java.util.List;

import com.my_app.entities.customer.Customer;
import com.my_app.entities.customer.transaction.Transaction;
import com.my_app.entities.customer.transaction.TransactionType;
import com.my_app.entities.order.Order;
import com.my_app.storage_service.CustomerJsonStorage;
import com.my_app.storage_service.TransactionJsonStorage;

public class CustomerServiceR implements CustomerService {
    private final CustomerJsonStorage storage;
    private final TransactionJsonStorage transactionStorage;

    public CustomerServiceR() {
        this.storage = new CustomerJsonStorage();
        this.transactionStorage = new TransactionJsonStorage();
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            return storage.getAllCustomers();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении списка клиентов: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не null");
        }
        try {
            Customer customer = storage.getCustomerById(id);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            return customer;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении клиента: " + e.getMessage());
        }
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Клиент не может быть null");
        }
        try {
            Integer nextId = storage.getNextCustomerId();
            customer.setId(nextId);
            storage.saveCustomer(customer);
            return customer;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании клиента: " + e.getMessage());
        }
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        if (id == null || customer == null) {
            throw new IllegalArgumentException("ID не null");
        }
        try {
            Customer existingCustomer = storage.getCustomerById(id);
            if (existingCustomer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            customer.setId(id);
            storage.updateCustomer(customer);
            return customer;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении клиента: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не null");
        }
        try {
            Customer customer = storage.getCustomerById(id);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            storage.deleteCustomer(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении клиента: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getCustomerOrders(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не  null");
        }
        try {
            Customer customer = storage.getCustomerById(id);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            return customer.getOrders();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении заказов клиента: " + e.getMessage());
        }
    }

    @Override
    public int getCustomerBalance(Integer customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        try {
            Customer customer = storage.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            return customer.getBalance();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении баланса клиента: " + e.getMessage());
        }
    }

    @Override
    public int replenishBalance(Integer customerId, int amount) {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("положительной");
        }
        try {
            Customer customer = storage.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            customer.setBalance(customer.getBalance() + amount);
            storage.updateCustomer(customer);
            addTransaction(customerId, amount, "Пополнение баланса");
            return customer.getBalance();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при пополнении баланса: " + e.getMessage());
        }
    }

    @Override
    public int withdrawBalance(Integer customerId, int amount) {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("сумма должна быть положительной");
        }
        try {
            Customer customer = storage.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            int balance = customer.getBalance();
            if (balance <= amount) {
                throw new IllegalStateException(String.format("Недостаточно средств. Текущий баланс: %d, требуемая сумма: %d", 
                balance, amount));
            }
            customer.setBalance(customer.getBalance() - amount);
            storage.updateCustomer(customer);
            addTransaction(customerId, -amount, "Вывод средств");
            return customer.getBalance();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при Вывод средств: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getCustomerTransactions(Integer customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        try {
            return transactionStorage.getCustomerTransactions(customerId);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении транзакций клиента: " + e.getMessage());
        }
    }

    @Override
    public Transaction addTransaction(Integer customerId, int amount, String description) {
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Описание пустое");
        }
        try {
            Transaction transaction = new Transaction(
                transactionStorage.getNextTransactionId(),
                customerId,
                amount,
                description,
                amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL
            );
            transactionStorage.saveTransaction(transaction);
            return transaction;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании транзакции: " + e.getMessage());
        }
    }
} 