package com.my_app.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.sale_point.SalePoint;
import com.my_app.service.employ_service.EmployeeService;
import com.my_app.service.employ_service.EmployeeServiceR;
import com.my_app.service.product_service.ProductService;
import com.my_app.service.product_service.ProductServiceR;
import com.my_app.service.sale_pointer_service.SalesPointService;
import com.my_app.service.sale_pointer_service.SalesPointServiceR;

public class SalesPointServiceTest {
    private SalesPointService salesPointService;
    private EmployeeService employeeService;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        salesPointService = new SalesPointServiceR();
        employeeService = new EmployeeServiceR();
        productService = new ProductServiceR();
    }

    @Test
    void testCreateSalePoint() {
        SalePoint salePoint = new SalePoint();
        salePoint.setAdress("Test Address");
        salePoint.setActive(true);

        SalePoint createdSalePoint = salesPointService.createSalePoint(salePoint);
        assertNotNull(createdSalePoint);
        assertNotNull(createdSalePoint.getId());
        assertEquals("Test Address", createdSalePoint.getAdress());
        assertTrue(createdSalePoint.isActive());
    }

    @Test
    void testGetSalePointById() {
        SalePoint salePoint = new SalePoint();
        salePoint.setAdress("Test Address");
        salePoint.setActive(true);
        SalePoint createdSalePoint = salesPointService.createSalePoint(salePoint);

        SalePoint foundSalePoint = salesPointService.getSalePointById(createdSalePoint.getId());
        assertNotNull(foundSalePoint);
        assertEquals(createdSalePoint.getId(), foundSalePoint.getId());
        assertEquals("Test Address", foundSalePoint.getAdress());
    }

    @Test
    void testUpdateSalePoint() {
        SalePoint salePoint = new SalePoint();
        salePoint.setAdress("Test Address");
        salePoint.setActive(true);
        SalePoint createdSalePoint = salesPointService.createSalePoint(salePoint);

        createdSalePoint.setAdress("Updated Address");
        SalePoint updatedSalePoint = salesPointService.updateSalePoint(createdSalePoint.getId(), createdSalePoint);
        assertNotNull(updatedSalePoint);
        assertEquals("Updated Address", updatedSalePoint.getAdress());
    }

    @Test
    void testDeleteSalePoint() {
        SalePoint salePoint = new SalePoint();
        salePoint.setAdress("Test Address");
        salePoint.setActive(true);
        SalePoint createdSalePoint = salesPointService.createSalePoint(salePoint);

        salesPointService.deleteSalePoint(createdSalePoint.getId());
        assertThrows(IllegalStateException.class, () -> {
            salesPointService.getSalePointById(createdSalePoint.getId());
        });
    }

    @Test
    void testGetAllSalePoints() {
        SalePoint salePoint1 = new SalePoint();
        salePoint1.setAdress("Test Address 1");
        salePoint1.setActive(true);
        salesPointService.createSalePoint(salePoint1);

        SalePoint salePoint2 = new SalePoint();
        salePoint2.setAdress("Test Address 2");
        salePoint2.setActive(true);
        salesPointService.createSalePoint(salePoint2);

        List<SalePoint> salePoints = salesPointService.getAllSalePoints();
        assertNotNull(salePoints);
        assertTrue(salePoints.size() >= 2);
    }

    @Test
    void testRemoveEmployee() {
        SalePoint salePoint = new SalePoint();
        salePoint.setAdress("Test Address");
        salePoint.setActive(true);
        SalePoint createdSalePoint = salesPointService.createSalePoint(salePoint);

        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);
        Employee createdEmployee = employeeService.createEmployee(employee);

        salesPointService.addEmployee(createdSalePoint.getId(), createdEmployee);
        salesPointService.removeEmployee(createdSalePoint.getId(), createdEmployee);

        SalePoint updatedSalePoint = salesPointService.getSalePointById(createdSalePoint.getId());
        assertFalse(updatedSalePoint.getEmployees().contains(createdEmployee));
    }

   
} 