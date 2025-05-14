package com.my_app.service.sale_pointer_service;

import java.io.IOException;
import java.util.List;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.product.Product;
import com.my_app.entities.sale_point.SalePoint;
import com.my_app.storage_service.SalePointJsonStorage;

public class SalesPointServiceR implements SalesPointService {
    private final SalePointJsonStorage storage;

    public SalesPointServiceR() {
        this.storage = new SalePointJsonStorage();
    }

    @Override
    public SalePoint createSalePoint(SalePoint salePoint) {
        if (salePoint == null) {
            throw new IllegalArgumentException("Пункт продаж не может быть null");
        }
        try {
            Integer nextId = storage.getNextSalePointId();
            salePoint.setId(nextId);
            storage.saveSalePoint(salePoint);
            return salePoint;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании пункта продаж: " + e.getMessage());
        }
    }

    @Override
    public void deleteSalePoint(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(id);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            if (!salePoint.getEmployees().isEmpty()) {
                throw new IllegalStateException("Невозможно удалить пункт продаж, содержащий сотрудников");
            }
            storage.deleteSalePoint(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении пункта продаж: " + e.getMessage());
        }
    }

    @Override
    public List<SalePoint> getAllSalePoints() {
        try {
            return storage.getAllSalePoints();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении списка пунктов продаж: " + e.getMessage());
        }
    }

    @Override
    public SalePoint getSalePointById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(id);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            return salePoint;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении пункта продаж: " + e.getMessage());
        }
    }

    @Override
    public SalePoint updateSalePoint(Integer id, SalePoint salePoint) {
        if (id == null || salePoint == null) {
            throw new IllegalArgumentException("ID и пункт продаж не могут быть null");
        }
        try {
            SalePoint existingSalePoint = storage.getSalePointById(id);
            if (existingSalePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            salePoint.setId(id);
            storage.updateSalePoint(salePoint);
            return salePoint;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении пункта продаж: " + e.getMessage());
        }
    }

    @Override
    public void addEmployee(Integer salePointId, Employee employee) {
        if (salePointId == null || employee == null) {
            throw new IllegalArgumentException("ID пункта продаж и сотрудник не могут быть null");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            salePoint.getEmployees().add(employee);
            storage.updateSalePoint(salePoint);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }

    @Override
    public void removeEmployee(Integer salePointId, Employee employee) {
        if (salePointId == null || employee == null) {
            throw new IllegalArgumentException("ID пункта продаж и сотрудник не могут быть null");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            salePoint.getEmployees().remove(employee);
            storage.updateSalePoint(salePoint);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении сотрудника: " + e.getMessage());
        }
    }

    @Override
    public void addProduct(Integer salePointId, Product product, int quantity) {
        if (salePointId == null || product == null) {
            throw new IllegalArgumentException("ID пункта продаж и товар не могут быть null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            salePoint.getProducts().add(product);
            storage.updateSalePoint(salePoint);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    @Override
    public void removeProduct(Integer salePointId, Product product, int quantity) {
        if (salePointId == null || product == null) {
            throw new IllegalArgumentException("ID пункта продаж и товар не могут быть null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            salePoint.getProducts().remove(product);
            storage.updateSalePoint(salePoint);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    @Override
    public boolean hasEnoughProduct(Integer salePointId, Integer productId, int quantity) {
        if (salePointId == null || productId == null) {
            throw new IllegalArgumentException("ID пункта продаж и товара не могут быть null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            return salePoint.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .isPresent();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при проверке наличия товара: " + e.getMessage());
        }
    }

    @Override
    public int getProductQuantity(Integer salePointId, Product product) {
        if (salePointId == null || product == null) {
            throw new IllegalArgumentException("ID пункта продаж и товар не могут быть null");
        }
        try {
            SalePoint salePoint = storage.getSalePointById(salePointId);
            if (salePoint == null) {
                throw new IllegalStateException("Пункт продаж не найден");
            }
            return (int) salePoint.getProducts().stream()
                .filter(p -> p.getId().equals(product.getId()))
                .count();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении количества товара: " + e.getMessage());
        }
    }
}
