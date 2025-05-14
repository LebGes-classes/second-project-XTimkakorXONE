package com.my_app.entities.warehouse_cell;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.my_app.entities.product.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cell {
    private int id;
    private Volume capacity;
    private int warehouseId;
    private int quantity;
    private List<Product> products;

    public Cell() {
        this.products = new ArrayList<>();
    }

    public Cell(int id, Volume capacity, int warehouseId, int quantity) {
        this.id = id;
        this.capacity = capacity;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.products = new ArrayList<>();
    }

    public Cell(int id, Volume capacity, int warehouseId, int quantity, List<Product> products) {
        this.id = id;
        this.capacity = capacity;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.products = products;
    }

    public void addProduct(Product product, int quantity) {
        if (this.quantity + quantity <= capacity.getValue()) {
            this.quantity += quantity;
            products.add(product);
        }
    }

    public void removeProduct(Product product, int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
            products.remove(product);
        }
    }

    public boolean containProduct() {
        return !products.isEmpty();
    }
    
    @JsonIgnore
    public int getFreeSpace() {
        return capacity.getValue() - quantity;
    }

    public boolean add_posiblle(int amount) {
        return amount >= getFreeSpace();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Volume getCapacity() {
        return capacity;
    }

    public void setCapacity(Volume capacity) {
        this.capacity = capacity;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProductsCell() {
        return products;
    }
}
