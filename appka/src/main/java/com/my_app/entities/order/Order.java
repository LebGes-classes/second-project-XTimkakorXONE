package com.my_app.entities.order;

import java.util.ArrayList;
import java.util.List;

import com.my_app.entities.product.Product;

public class Order {
    private Integer id;
    private Integer customerId;
    private StatusOrder status;
    private List<Product> order;

    public Order() {
        this.order = new ArrayList<>();
    }

    public Order(Integer customerId, StatusOrder status, List<Product> order) {
        this.customerId = customerId;
        this.status = status;
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public List<Product> getOrder() {
        return order;
    }

    public void setOrder(Product product) {
        if (product != null) {
            order.add(product);
        }
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
