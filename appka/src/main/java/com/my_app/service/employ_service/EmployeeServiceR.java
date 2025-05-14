package com.my_app.service.employ_service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.warehouse.Warehouse;
import com.my_app.service.warehouse_service.WarehouseService;
import com.my_app.service.warehouse_service.WarehouseServiceR;
import com.my_app.storage_service.EmployeeJsonStorage;

public class EmployeeServiceR implements EmployeeService {
    private final EmployeeJsonStorage storage;
    private final WarehouseService warehouseService;
    
    public EmployeeServiceR() {
        this.storage = new EmployeeJsonStorage();
        this.warehouseService = new WarehouseServiceR();
    }


    @Override
    public void assignEmployeeToWarehouse(Integer employeeId, Integer warehouseId) {
        if (employeeId == null || warehouseId == null) {
            throw new IllegalArgumentException("тьфу");
        }

    try {
        Employee employee = storage.getEmployeeById(employeeId);
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);

        if (employee == null) {
            throw new IllegalStateException("Сотрудник не найден");
        }
        if (!employee.isActive()) {
            throw new IllegalStateException("это неактивный сотрудника");
        }
        if (warehouse == null) {
            throw new IllegalStateException("Склад не найден");
        }
        if (!warehouse.isActive()) {
            throw new IllegalStateException("Нельзя назначить сотрудника на неактивный склад");
        }

        List<Employee> warehouseEmployees = warehouse.getEmployees();
        if (warehouseEmployees == null) {
            warehouseEmployees = new ArrayList<>();
        }

        warehouseEmployees.add(employee);
        warehouse.setEmployees(warehouseEmployees);
        warehouseService.updateWarehouse(warehouseId, warehouse);

    } catch (IOException e) {
        throw new RuntimeException("Ошибка при назначении сотрудника на склад: " + e.getMessage());
    }
    }

    public void removeEmployeeFromWarehouse(Integer employeeId, Integer warehouseId) {
        if (employeeId == null || warehouseId == null) {
            throw new IllegalArgumentException("ID не могут быть null");
        }

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        if (warehouse == null) {
            throw new IllegalStateException("Склад не найден");
        }

        List<Employee> warehouseEmployees = warehouse.getEmployees();
        if (warehouseEmployees == null || warehouseEmployees.isEmpty()) {
            throw new IllegalStateException("нет сотрудников");
        }

        boolean removed = warehouseEmployees.removeIf(e -> e.getId().equals(employeeId));
        if (!removed) {
            throw new IllegalStateException("Такого чела нет");
        }

        warehouse.setEmployees(warehouseEmployees);
        warehouseService.updateWarehouse(warehouseId, warehouse);
    }

    @Override
    public void deactivateEmployee(Integer id) {
    if (id == null) {
        throw new IllegalArgumentException("ID не может быть null");
    }
    try {
        Employee employee = storage.getEmployeeById(id);
        if (employee == null) {
            throw new IllegalStateException("Сотрудник с ID " + id + " не найден");
        }
        employee.setActive(false);
        storage.updateEmployee(employee);
    } catch (IOException e) {
        throw new RuntimeException("Ошибка при деактивации сотрудника: " + e.getMessage());
    }
}
    @Override
    public Employee createEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Сотрудник не может быть null");
        }
        try {
            Integer nextId = storage.getNextEmployeeId();
            employee.setId(nextId);
            storage.saveEmployee(employee);
            return employee;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании сотрудника: " + e.getMessage());
        }
    }
    @Override
    public void deleteEmployee(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("тьфу");
        }
        try {
            if (storage.getEmployeeById(id) == null) {
                throw new IllegalStateException("Сотрудник не найден");
            }
            storage.deleteEmployee(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }
    @Override
    public List<Employee> getAllEmployees() {
        try {
            return storage.getAllEmployees();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }
    @Override
    public Employee getEmployeeById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("тьфу");
        }
        try {
            return storage.getEmployeeById(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        if (id == null || employee == null) {
            throw new IllegalArgumentException("тьфу");
        }
        try {
            if (storage.getEmployeeById(id) == null) {
                throw new IllegalStateException("Сотрудник не найден");
            }
            employee.setId(id);
            storage.updateEmployee(employee);
            return employee;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }
    @Override
    public List<Employee> getActiveEmployees() {
        try {
            return storage.getAllEmployees().stream()
            .filter(p -> p.isActive())
            .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }

}
