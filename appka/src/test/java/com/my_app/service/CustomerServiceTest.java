package com.my_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.my_app.entities.customer.Customer;
import com.my_app.service.customer_service.CustomerService;
import com.my_app.service.customer_service.CustomerServiceR;

public class CustomerServiceTest {
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceR();
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);

        Customer createdCustomer = customerService.createCustomer(customer);
        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());
        assertEquals("Test Customer", createdCustomer.getName());
        assertEquals(1000, createdCustomer.getBalance());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        Customer createdCustomer = customerService.createCustomer(customer);

        Customer foundCustomer = customerService.getCustomerById(createdCustomer.getId());
        assertNotNull(foundCustomer);
        assertEquals(createdCustomer.getId(), foundCustomer.getId());
        assertEquals("Test Customer", foundCustomer.getName());
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        Customer createdCustomer = customerService.createCustomer(customer);

        createdCustomer.setName("Updated Customer");
        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), createdCustomer);
        assertNotNull(updatedCustomer);
        assertEquals("Updated Customer", updatedCustomer.getName());
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        Customer createdCustomer = customerService.createCustomer(customer);

        customerService.deleteCustomer(createdCustomer.getId());
        assertThrows(IllegalStateException.class, () -> {
            customerService.getCustomerById(createdCustomer.getId());
        });
    }

    
} 