package com.my_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.my_app.entities.customer.Customer;
import com.my_app.entities.order.Order;
import com.my_app.entities.order.StatusOrder;
import com.my_app.entities.product.Product;
import com.my_app.service.customer_service.CustomerService;
import com.my_app.service.customer_service.CustomerServiceR;
import com.my_app.service.order_service.OrderService;
import com.my_app.service.order_service.OrderServiceR;
import com.my_app.service.product_service.ProductService;
import com.my_app.service.product_service.ProductServiceR;

public class OrderServiceTest {
    private OrderService orderService;
    private CustomerService customerService;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceR();
        customerService = new CustomerServiceR();
        productService = new ProductServiceR();
    }

    @Test
    void testCreateOrder() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        order.setOrder(createdProduct);

        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());
        assertNotNull(createdOrder);
        assertNotNull(createdOrder.getId());
        assertEquals(StatusOrder.IN_WAREHOUSE, createdOrder.getStatus());
    }

    @Test
    void testGetOrderById() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());

        Order foundOrder = orderService.getOrderById(createdOrder.getId());
        assertNotNull(foundOrder);
        assertEquals(createdOrder.getId(), foundOrder.getId());
        assertEquals(StatusOrder.IN_WAREHOUSE, foundOrder.getStatus());
    }

    @Test
    void testUpdateOrder() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());

        createdOrder.setStatus(StatusOrder.IN_PATH);
        Order updatedOrder = orderService.updateOrder(createdOrder.getId(), createdOrder);
        assertNotNull(updatedOrder);
        assertEquals(StatusOrder.IN_PATH, updatedOrder.getStatus());
    }

    @Test
    void testDeleteOrder() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());

        orderService.deleteOrder(createdOrder.getId());
        assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(createdOrder.getId());
        });
    }

    @Test
    void testChangeOrderStatus() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());

        orderService.changeOrderStatus(createdOrder.getId(), StatusOrder.IN_SALE_POINT);
        Order updatedOrder = orderService.getOrderById(createdOrder.getId());
        assertEquals(StatusOrder.IN_SALE_POINT, updatedOrder.getStatus());
    }

    @Test
    void testCalculateOrderTotalPrice() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setBalance(1000);
        customer.setAdress("Test Address");
        Customer createdCustomer = customerService.createCustomer(customer);

        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setPrice(100);
        product1.setQuantity(2);
        Product createdProduct1 = productService.createProduct(product1);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setPrice(200);
        product2.setQuantity(1);
        Product createdProduct2 = productService.createProduct(product2);

        Order order = new Order();
        order.setStatus(StatusOrder.IN_WAREHOUSE);
        order.setOrder(createdProduct1);
        order.setOrder(createdProduct2);
        Order createdOrder = orderService.createOrder(order, createdCustomer.getId());

        Integer totalPrice = orderService.calculateOrderTotalPrice(createdOrder.getId());
        assertEquals(400, totalPrice); 
    }
} 