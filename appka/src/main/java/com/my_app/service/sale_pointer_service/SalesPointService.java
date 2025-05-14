package com.my_app.service.sale_pointer_service;

import java.util.List;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.product.Product;
import com.my_app.entities.sale_point.SalePoint;

public interface SalesPointService {
    SalePoint createSalePoint(SalePoint salePoint);
    void deleteSalePoint(Integer id);
    List<SalePoint> getAllSalePoints();
    SalePoint getSalePointById(Integer id);
    SalePoint updateSalePoint(Integer id, SalePoint salePoint);
    void addEmployee(Integer salePointId, Employee employee);
    void removeEmployee(Integer salePointId, Employee employee);
    void addProduct(Integer salePointId, Product product, int quantity);
    void removeProduct(Integer salePointId, Product product, int quantity);
    boolean hasEnoughProduct(Integer salePointId, Integer productId, int quantity);
    int getProductQuantity(Integer salePointId, Product product);
} 