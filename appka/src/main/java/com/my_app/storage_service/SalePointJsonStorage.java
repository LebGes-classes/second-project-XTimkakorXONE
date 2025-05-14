package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.sale_point.SalePoint;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class SalePointJsonStorage extends JsonStorageService<SalePoint> {

    public SalePointJsonStorage() {
        super("storage/SalePoint.json", SalePoint.class);
    }
    
    public void saveSalePoint(SalePoint salePoint) throws IOException {
        if (salePoint == null) {
            throw new IllegalArgumentException("Пункт продаж не может быть null");
        }
        List<SalePoint> list = readFile();
        list.add(salePoint);
        writeFile(list);
    }

    public List<SalePoint> getAllSalePoints() throws IOException {
        return readFile();
    }

    public SalePoint getSalePointById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        return readFile().stream()
            .filter(o -> Objects.equals(o.getId(), id))
            .findFirst()
            .orElse(null);
    }

    public void deleteSalePoint(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        List<SalePoint> list = readFile();
        list.removeIf(salePoint -> Objects.equals(salePoint.getId(), id));
        writeFile(list);
    }

    public SalePoint updateSalePoint(SalePoint salePoint) throws IOException {
        if (salePoint == null) {
            throw new IllegalArgumentException("Пункт продаж не может быть null");
        }
        List<SalePoint> list = readFile();
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i).getId(), salePoint.getId())) {
                list.set(i, salePoint);
                writeFile(list);
                return salePoint;
            }
        }
        return null;
    }

    public Integer getNextSalePointId() throws IOException {
        List<SalePoint> salePoints = readFile();
        return IdGenerator.getNextId(salePoints, SalePoint::getId);
    }

}
