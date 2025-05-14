package com.my_app.service.warehouse_service;

import java.util.List;

import com.my_app.entities.warehouse.Warehouse;

public interface WarehouseService {
    List<Warehouse> getAllWarehouses();
    Warehouse getWarehouseById(Integer id);
    Warehouse createWarehouse(Warehouse warehouse);
    void deleteWarehouse(Integer id);
    Warehouse updateWarehouse(Integer id, Warehouse warehouse);
    int getWarehouseStock(Integer id);
    void transferStock(Integer fromWarehouseId, Integer toWarehouseId, Integer productId, int quantity);
} 