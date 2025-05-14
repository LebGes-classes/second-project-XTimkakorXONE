package com.my_app.service.product_service;

import java.io.IOException;
import java.util.List;

import com.my_app.entities.product.Product;
import com.my_app.storage_service.ProductJsonStorage;

public class ProductServiceR implements ProductService {
    ProductJsonStorage storage = new ProductJsonStorage();
    public ProductServiceR() {
    }

    @Override
    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        try {
            Integer nextId = storage.getNextProductId();
            product.setId(nextId);
            storage.saveProduct(product);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании товара: " + e.getMessage());
        }
    }
    @Override
    public void deleteProduct(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }        
        try {
            if (storage.getProductById(id) == null) {
                throw new IllegalStateException("Товар  не найден");
            }
            storage.deleteProduct(id);
        } catch (IOException e) {
             throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
    }
    @Override
    public List<Product> getAllProducts() {
        try {
            return storage.getAllProducts();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }
    @Override
    public Product getProductById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID товара не может быть null");
        }    
        try {
            return storage.getProductById(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении списка товара: " + e.getMessage());
        }
    }
    @Override
    public Product updateProduct(Integer id, Product product) {
        if (id == null || product == null) {
            throw new IllegalArgumentException("id и товар не могут быть null");
        }
        try {
            Product originalProduct = storage.getProductById(id);
            if (originalProduct == null) {
                throw new IllegalStateException("Товар с ID " + id + " не найден");
            }
            product.setId(id);
            storage.updateProduct(product);
            return product;
        } catch (IOException e) {
             throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
}

    @Override
    public void updateProductQuantity(Integer id, int quantity) {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        try {
            Product product = storage.getProductById(id);
            if (product == null) {
                throw new IllegalStateException("Товар с ID " + id + " не найден");
            }
            if (quantity < 0) {
                throw new IllegalArgumentException("Количество не может быть отрицательным");
            }
            product.setQuantity(quantity);
            storage.updateProduct(product);
        } catch (IOException e) {
             throw new RuntimeException("Ошибка при обновлении количества товара: " + e.getMessage());
        }
        
    }
    
}
