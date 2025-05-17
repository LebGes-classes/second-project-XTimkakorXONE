package com.my_app.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.my_app.entities.product.Product;
import com.my_app.entities.warehouse.Warehouse;
import com.my_app.entities.warehouse_cell.Cell;
import com.my_app.entities.warehouse_cell.Volume;
import com.my_app.service.product_service.ProductService;
import com.my_app.service.product_service.ProductServiceR;
import com.my_app.service.warehouse_service.WarehouseService;
import com.my_app.service.warehouse_service.WarehouseServiceR;

public class WarehouseServiceTest {
    private WarehouseService warehouseService;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        warehouseService = new WarehouseServiceR();
        productService = new ProductServiceR();
    }

    @Test
    void testCreateWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setAdress("Test Address");
        warehouse.setActive(true);

        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
        assertNotNull(createdWarehouse);
        assertNotNull(createdWarehouse.getId());
        assertEquals("Test Address", createdWarehouse.getAdress());
        assertTrue(createdWarehouse.isActive());
    }

    @Test
    void testGetWarehouseById() {
        Warehouse warehouse = new Warehouse();
        warehouse.setAdress("Test Address");
        warehouse.setActive(true);
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);

        Warehouse foundWarehouse = warehouseService.getWarehouseById(createdWarehouse.getId());
        assertNotNull(foundWarehouse);
        assertEquals(createdWarehouse.getId(), foundWarehouse.getId());
        assertEquals("Test Address", foundWarehouse.getAdress());
    }

    @Test
    void testUpdateWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setAdress("Test Address");
        warehouse.setActive(true);
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);

        createdWarehouse.setAdress("Updated Address");
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(createdWarehouse.getId(), createdWarehouse);
        assertNotNull(updatedWarehouse);
        assertEquals("Updated Address", updatedWarehouse.getAdress());
    }

    

    @Test
    void testGetAllWarehouses() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setAdress("Test Address 1");
        warehouse1.setActive(true);
        warehouseService.createWarehouse(warehouse1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setAdress("Test Address 2");
        warehouse2.setActive(true);
        warehouseService.createWarehouse(warehouse2);

        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        assertNotNull(warehouses);
        assertTrue(warehouses.size() >= 2);
    }

    @Test
    void testGetWarehouseStock() {
        // Создаем склад
        Warehouse warehouse = new Warehouse();
        warehouse.setAdress("Test Address");
        warehouse.setActive(true);
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);

        // Создаем продукт
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);
        Product createdProduct = productService.createProduct(product);

        // Добавляем ячейку с продуктом
        Cell cell = new Cell();
        cell.setCapacity(Volume.BIG);
        cell.setQuantity(5);
        cell.addProduct(createdProduct, 5);
        createdWarehouse.addCell(cell);
        warehouseService.updateWarehouse(createdWarehouse.getId(), createdWarehouse);

        int stock = warehouseService.getWarehouseStock(createdWarehouse.getId());
        assertEquals(5, stock);
    }

    
} 