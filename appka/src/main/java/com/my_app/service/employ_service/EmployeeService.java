package com.my_app.service.employ_service;

import java.util.List;

import com.my_app.entities.employee.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Integer id, Employee employee);
    void deleteEmployee(Integer id);
    void assignEmployeeToWarehouse(Integer employeeId, Integer warehouseId);
    List<Employee> getActiveEmployees();
    void deactivateEmployee(Integer id);
} 