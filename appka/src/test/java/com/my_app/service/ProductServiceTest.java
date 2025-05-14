package com.my_app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.my_app.service.product_service.ProductService;
import com.my_app.service.product_service.ProductServiceR;
import com.my_app.entities.product.Product;
import java.util.List;

public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceR();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);

        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(100, createdProduct.getPrice());
        assertEquals(10, createdProduct.getQuantity());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        Product foundProduct = productService.getProductById(createdProduct.getId());
        assertNotNull(foundProduct);
        assertEquals(createdProduct.getId(), foundProduct.getId());
        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        createdProduct.setName("Updated Product");
        createdProduct.setPrice(150);
        Product updatedProduct = productService.updateProduct(createdProduct.getId(), createdProduct);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(150, updatedProduct.getPrice());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        productService.deleteProduct(createdProduct.getId());
        assertThrows(IllegalStateException.class, () -> {
            productService.getProductById(createdProduct.getId());
        });
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setPrice(100);
        product1.setQuantity(10);
        productService.createProduct(product1);

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setPrice(200);
        product2.setQuantity(20);
        productService.createProduct(product2);

        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertTrue(products.size() >= 2);
    }

    @Test
    void testUpdateProductQuantity() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        productService.updateProductQuantity(createdProduct.getId(), 20);
        Product updatedProduct = productService.getProductById(createdProduct.getId());
        assertEquals(20, updatedProduct.getQuantity());
    }
} 