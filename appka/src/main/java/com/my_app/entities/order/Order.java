package com.my_app.entities.order;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.my_app.entities.product.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
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
        this.order = order != null ? order : new ArrayList<>();
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("customerId")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("status")
    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    @JsonProperty("order")
    public List<Product> getOrder() {
        return order;
    }

    public void setOrder(List<Product> order) {
        this.order = order != null ? order : new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            if (this.order == null) {
                this.order = new ArrayList<>();
            }
            this.order.add(product);
        }
    }
}
