package com.my_app.service.order_service;

import java.util.List;

import com.my_app.entities.order.Order;
import com.my_app.entities.order.StatusOrder;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    Order createOrder(Order order, Integer customerId);
    Order updateOrder(Integer id, Order order);
    void deleteOrder(Integer id);
    void changeOrderStatus(Integer id, StatusOrder status);
    void addProductToOrder(Integer orderId, Integer productId, int quantity);
    void removeProductFromOrder(Integer orderId, Integer productId);
    Integer calculateOrderTotalPrice(Integer orderId);
} 