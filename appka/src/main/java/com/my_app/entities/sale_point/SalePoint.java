package com.my_app.entities.sale_point;

import java.util.ArrayList;
import java.util.List;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.product.Product;

public class SalePoint {
    private Integer id;
    private String adress;
    private List<Product> products;
    private boolean isActive;
    private List<Employee> employees;

    public SalePoint() {
        this.products = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public SalePoint(String adress, List<Product> products, boolean isActive) {
        this.adress = adress;
        this.products = products;
        this.isActive = isActive;
        this.employees = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
