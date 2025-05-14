package com.my_app.service.warehouse_service;

import java.io.IOException;
import java.util.List;

import com.my_app.entities.product.Product;
import com.my_app.entities.warehouse.Warehouse;
import com.my_app.entities.warehouse_cell.Cell;
import com.my_app.storage_service.WarehouseJsonStorage;

public class WarehouseServiceR implements WarehouseService {
    WarehouseJsonStorage storage = new WarehouseJsonStorage();
    public WarehouseServiceR() {
    }
    
    @Override
    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Склад не может быть null");
        }
        try {
            Integer nextId = storage.getNextWarehouseId();
            warehouse.setId(nextId);
            storage.saveWarehouse(warehouse);
            return warehouse;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании склада: " + e.getMessage());
        }
    }

    @Override
    public void deleteWarehouse(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID не  null");
            }
            Warehouse warehouse = storage.getWarehouseById(id);
            if (warehouse == null) {
                throw new IllegalStateException("Склад не найден");
            }
            boolean hasProducts = warehouse.getCells().stream()
                .anyMatch(cell -> cell != null && cell.getQuantity() > 0);
            if (hasProducts) {
                throw new IllegalStateException("Невозможно удалить склад, содержащий товары");
            }
            storage.deleteWarehouse(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        try {
            return storage.getAllWarehouses();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Warehouse getWarehouseById(Integer id) {
        try {
            return storage.getWarehouseById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getWarehouseStock(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не  null");
        }
        try {
            Warehouse warehouse = storage.getWarehouseById(id);
            
            return warehouse.getCells().stream()
            .mapToInt(Cell::getQuantity)
            .sum();
    }
    catch (IOException e) {
        throw new RuntimeException("Ошибка при деактивации сотрудника: " + e.getMessage());
    }
}

    @Override
    public void transferStock(Integer fromWarehouseId, Integer toWarehouseId, Integer productId, int quantity) {
        try {
            Warehouse fromWarehouse = storage.getWarehouseById(fromWarehouseId);
            Warehouse toWarehouse = storage.getWarehouseById(toWarehouseId);
            if (fromWarehouse == null) {
                throw new IllegalStateException("СкладFrom не найден");
            }
            if (toWarehouse == null) {
                throw new IllegalStateException("СкладTo не найден");
            }
            if (quantity < 1) {
                throw new IllegalStateException("Совсем тупой");
            }

            Cell sourceCell = fromWarehouse.getCells().stream()
            .filter(cell -> cell.getProductsCell().stream()
                .anyMatch(product -> product.getId().equals(productId)))
            .findFirst().orElseThrow(() -> new IllegalStateException());

            if (sourceCell.getProductsCell().stream()
            .filter(p -> p.getId().equals(productId)).findFirst().get().getQuantity() < quantity) {
                throw new IllegalStateException("Недостаточно товара на складе-отправителе");
            }

            Cell targetCell = toWarehouse.getCells().stream()
            .filter(p -> p.getFreeSpace() >= quantity)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Нет свободного места"));
            
            Product product = sourceCell.getProductsCell().stream()
            .filter(p -> p.getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Товар не найден"));

            sourceCell.removeProduct(product, quantity);
            targetCell.addProduct(product, quantity);

            storage.saveWarehouse(fromWarehouse);
            storage.saveWarehouse(toWarehouse);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при деактивации сотрудника: " + e.getMessage());

        }
    }

    @Override
    public Warehouse updateWarehouse(Integer id, Warehouse warehouse) {
       if (id == null) {
        throw new IllegalArgumentException();
       }
       try {
        Warehouse existingWarehouse= storage.getWarehouseById(id);
        if (existingWarehouse == null) {
            throw new IllegalStateException("Пункт продаж с ID " + id + " не найден");
        }
        warehouse.setId(id);
        storage.updateWarehouse(warehouse);
        return warehouse;
    } catch (IOException e) {
        throw new RuntimeException("Ошибка при деактивации сотрудника: " + e.getMessage());
    }
    }
        
}
