package com.my_app.service.product_service;

import java.util.List;

import com.my_app.entities.product.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
    void updateProductQuantity(Integer id, int quantity);
} 