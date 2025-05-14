package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.warehouse.Warehouse;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class WarehouseJsonStorage extends JsonStorageService<Warehouse> {
    public WarehouseJsonStorage() {
        super("storage/Warehouse.json", Warehouse.class);
    }

    public void saveWarehouse(Warehouse warehouse) throws IOException {
        if (warehouse == null) {
            throw new IllegalArgumentException("Склад не может быть null");
        }
        List<Warehouse> list = readFile();
        list.add(warehouse);
        writeFile(list);
    }

    public List<Warehouse> getAllWarehouses() throws IOException {
        return readFile();
    }

    public Warehouse getWarehouseById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        return readFile().stream()
            .filter(o -> Objects.equals(o.getId(), id))
            .findFirst()
            .orElse(null);
    }

    public void deleteWarehouse(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        List<Warehouse> list = readFile();
        list.removeIf(warehouse -> Objects.equals(warehouse.getId(), id));
        writeFile(list);
    }

    public Integer getNextWarehouseId() throws IOException {
        List<Warehouse> warehouses = readFile();
        return IdGenerator.getNextId(warehouses, Warehouse::getId);
    }

    public Warehouse updateWarehouse(Warehouse warehouse) throws IOException {
        if (warehouse == null) {
            throw new IllegalArgumentException("Склад не может быть null");
        }
        List<Warehouse> list = readFile();
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i).getId(), warehouse.getId())) {
                list.set(i, warehouse);
                writeFile(list);
                return warehouse;
            }
        } 
        return null;
    }
}
