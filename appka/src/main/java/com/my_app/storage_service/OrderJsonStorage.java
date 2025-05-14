package com.my_app.storage_service;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.order.Order;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class OrderJsonStorage extends JsonStorageService<Order> {

    public OrderJsonStorage() {
        super("storage/Order.json", Order.class);
    }

    public void saveOrder(Order order) throws IOException {
        if (order == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        List<Order> orders = readFile();
        orders.add(order);
        writeFile(orders);
    }
    
    public void deleteOrder(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("Id не может быть null");
        }
        List<Order> orders = readFile();
        if (orders.removeIf( p-> p.getId().equals(id))) {
            writeFile(orders);
        }
    }

    public List<Order> getAllOrders() throws IOException {
        return readFile();
    }

    public Order getOrderById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        return readFile().stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
    }

    public void updateOrder(Order order) throws IOException {
        if (order == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        List<Order> orders = readFile();
        for (int i = 0; i <orders.size(); i++) {
            if (Objects.equals(orders.get(i).getId(), order.getId())) {
               orders.set(i, order);
                writeFile(orders);
                return;
            }
        }        
    }

    public Integer getNextOrderId() throws IOException {
        List<Order> orders = readFile();
        return IdGenerator.getNextId(orders, Order::getId);
    }
}
