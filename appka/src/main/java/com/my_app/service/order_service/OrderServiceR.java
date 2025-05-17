package com.my_app.service.order_service;

import java.io.IOException;
import java.util.List;

import com.my_app.entities.customer.Customer;
import com.my_app.entities.order.Order;
import com.my_app.entities.order.StatusOrder;
import com.my_app.entities.product.Product;
import com.my_app.service.customer_service.CustomerService;
import com.my_app.service.customer_service.CustomerServiceR;
import com.my_app.service.product_service.ProductService;
import com.my_app.service.product_service.ProductServiceR;
import com.my_app.storage_service.OrderJsonStorage;

public class OrderServiceR implements OrderService {
    private final OrderJsonStorage storage;
    private final ProductService productService;
    private final CustomerService customerService;
    
    public OrderServiceR() {
        this.storage = new OrderJsonStorage();
        this.productService = new ProductServiceR();
        this.customerService = new CustomerServiceR();
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            return storage.getAllOrders();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получение заказа: " + e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        try {
            Order order = storage.getOrderById(id);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получение заказа: " + e.getMessage());
        }
    }

    @Override
    public Order createOrder(Order order, Integer customerId) {
        if (order == null) {
            throw new IllegalArgumentException("Заказ не может быть null");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("ID клиента не может быть null");
        }
        try {
            Integer nextId = storage.getNextOrderId();
            order.setId(nextId);
            order.setCustomerId(customerId);
            
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalStateException("Клиент не найден");
            }
            if (order.getOrder() == null) {
                throw new IllegalStateException("Список товаров в заказе не может быть null");
            }
            if (order.getOrder().isEmpty()) {
                throw new IllegalStateException("Заказ не может быть пустым");
            }

            for (Product product : order.getOrder()) {
                if (product == null) continue;
                Product originalProduct = productService.getProductById(product.getId());
                if (originalProduct == null) {
                    throw new IllegalStateException("Товар с ID " + product.getId() + " не найден");
                }
                if (!originalProduct.hasEnoughtProduct(product.getQuantity())) {
                    throw new IllegalStateException("Недостаточно товара " + originalProduct.getName() + " на складе");
                }
            }

            int sumOrder = order.getOrder().stream()
                .filter(p -> p != null)
                .mapToInt(m -> m.getPrice() * m.getQuantity())
                .sum();
            if (sumOrder > customer.getBalance()) {
                throw new IllegalStateException("Недостаточно средств на балансе");
            }
            
            for (Product product : order.getOrder()) {
                if (product == null) continue;
                Product originalProduct = productService.getProductById(product.getId());
                originalProduct.decreaseQuantity(product.getQuantity());
                productService.updateProductQuantity(originalProduct.getId(), originalProduct.getQuantity());
            }
            
            customerService.addTransaction(
                customerId, 
                -sumOrder, 
                String.format("Оплата заказа %d", order.getId())
            );
            customer.setBalance(customer.getBalance() - sumOrder);
            
            customer.addOrder(order);
            customerService.updateCustomer(customerId, customer);
            storage.saveOrder(order);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    @Override
    public Order updateOrder(Integer id, Order order) {
       if (id == null || order == null) {
            throw new IllegalArgumentException("id и товар не могут быть null");
        }
        try {
            Order originalOrder = storage.getOrderById(id);
            if (originalOrder == null) {
                throw new IllegalStateException("Товар с ID " + id + " не найден");
            }
            order.setId(id);
            storage.updateOrder(order);
            return order;
        } catch (IOException e) {
             throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }        
        try {
            if (storage.getOrderById(id) == null) {
                throw new IllegalStateException("Товар  не найден");
            }
            storage.deleteOrder(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
    }

    @Override
    public void changeOrderStatus(Integer id, StatusOrder status) {
        if (id == null || status == null){
            throw new IllegalArgumentException("ID товара не может быть null");
        }
        try {
            Order order = storage.getOrderById(id);
            if (order == null) {
                throw new IllegalArgumentException("ID товара не может быть null");
            } 
            if (order.getStatus() == StatusOrder.IN_CUSTOMER) {
                throw new IllegalArgumentException("Продавец уже принял заказ");
            }
            order.setStatus(status);
            storage.updateOrder(order);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
    }

    @Override
    public void addProductToOrder(Integer orderId, Integer productId, int quantity) {
        if (orderId == null || productId == null) {
            throw new IllegalArgumentException("ID заказа и товара не могут быть null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        try {
            Order order = storage.getOrderById(orderId);
            if (order == null) {
                throw new IllegalStateException("Заказ не найден");
            } 
            if (order.getStatus() == StatusOrder.IN_CUSTOMER) {
                throw new IllegalStateException("Заказ уже доставлен клиенту");
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new IllegalStateException("Товар не найден");
            }
            if (!product.hasEnoughtProduct(quantity)) {
                throw new IllegalStateException("Недостаточно товара на складе");
            }

            product.decreaseQuantity(quantity);
            productService.updateProductQuantity(productId, product.getQuantity());

            product.setQuantity(quantity);
            order.getOrder().add(product);
            storage.updateOrder(order);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при добавлении товара в заказ: " + e.getMessage());
        }
    }

    @Override
    public void removeProductFromOrder(Integer orderId, Integer productId) {
        if (orderId == null || productId == null) {
            throw new IllegalArgumentException("ID заказа и товара не могут быть null");
        }
        try {
            Order order = storage.getOrderById(orderId);
            if (order == null) {
                throw new IllegalStateException("Заказ не найден");
            }
            if (order.getStatus() == StatusOrder.IN_CUSTOMER) {
                throw new IllegalStateException("Заказ уже доставлен клиенту");
            }

            Product product = order.getOrder().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Товар не найден в заказе"));
            
            Product originalProduct = productService.getProductById(productId);
            if (originalProduct != null) {
                originalProduct.increaseQuantity(product.getQuantity());
                productService.updateProductQuantity(productId, originalProduct.getQuantity());
            }

            order.getOrder().removeIf(p -> p.getId().equals(productId));
            storage.updateOrder(order);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении товара из заказа: " + e.getMessage());
        }
    }

    @Override
    public Integer calculateOrderTotalPrice(Integer orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("ID заказа не может быть null");
        }
        try {
            Order order = storage.getOrderById(orderId);
            if (order == null) {
                throw new IllegalStateException("Заказ с ID " + orderId + " не найден");
            }
            return order.getOrder().stream()
            .mapToInt(m -> m.calculateTotalPrice())
            .sum();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при расчете стоимости заказа: " + e.getMessage());
        }
    }

    
}
