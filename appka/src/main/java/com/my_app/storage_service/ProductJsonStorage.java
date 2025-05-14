package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.product.Product;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class ProductJsonStorage extends JsonStorageService<Product> {
    public ProductJsonStorage() {
        super("storage/Products.json", Product.class);
    }

    public void saveProduct(Product product) throws IOException {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        List<Product> products = readFile();
        products.add(product);
        writeFile(products);
    }
    
    public void deleteProduct(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("Id не может быть null");
        }
        List<Product> products = readFile();
        if (products.removeIf( p-> p.getId().equals(id))) {
            writeFile(products);
        }
    }

    public List<Product> getAllProducts() throws IOException {
        return readFile();
    }

    public Product getProductById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        return readFile().stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
    }

    public void updateProduct(Product product) throws IOException {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        List<Product> products = readFile();
        for (int i = 0; i < products.size(); i++) {
            if (Objects.equals(products.get(i).getId(), product.getId())) {
                products.set(i, product);
                writeFile(products);
                return;
            }
        }        
    }
    
    public Integer getNextProductId() throws IOException {
        List<Product> products = readFile();
        return IdGenerator.getNextId(products, Product::getId);
    }
}
