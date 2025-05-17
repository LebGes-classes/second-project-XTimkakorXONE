package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.customer.Customer;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class CustomerJsonStorage extends JsonStorageService<Customer> {
    public CustomerJsonStorage() {
        super("storage/Customers.json", Customer.class);
    }

    public void saveCustomer(Customer customer) throws IOException {
        if (customer == null) {
            throw new IllegalArgumentException("Клиент не может быть null");
        }
        List<Customer> customers = readFile();
        boolean found = false;
        for (int i = 0; i < customers.size(); i++) {
            if (Objects.equals(customers.get(i).getId(), customer.getId())) {
                customers.set(i, customer);
                found = true;
                break;
            }
        }
        if (!found) {
            customers.add(customer);
        }
        writeFile(customers);
    }

    public List<Customer> getAllCustomers() throws IOException {
        return readFile();
    }

    public Customer getCustomerById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        return readFile().stream()
            .filter(c -> Objects.equals(c.getId(), id))
            .findFirst()
            .orElse(null);
    }

    public void deleteCustomer(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        List<Customer> customers = readFile();
        if (customers.removeIf(c -> Objects.equals(c.getId(), id))) {
            writeFile(customers);
        }
    }

    public Customer updateCustomer(Customer customer) throws IOException {
        if (customer == null) {
            throw new IllegalArgumentException("Клиент не может быть null");
        }
        List<Customer> customers = readFile();
        for (int i = 0; i < customers.size(); i++) {
            if (Objects.equals(customers.get(i).getId(), customer.getId())) {
                customers.set(i, customer);
                writeFile(customers);
                return customer;
            }
        }
        throw new IllegalStateException("Клиент с ID " + customer.getId() + " не найден");
    }

    public Integer getNextCustomerId() throws IOException {
        List<Customer> customers = readFile();
        return IdGenerator.getNextId(customers, Customer::getId);
    }
} 