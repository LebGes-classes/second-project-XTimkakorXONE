package com.my_app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.my_app.service.employ_service.EmployeeService;
import com.my_app.service.employ_service.EmployeeServiceR;
import com.my_app.entities.employee.Employee;
import java.util.List;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceR();
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);

        Employee createdEmployee = employeeService.createEmployee(employee);
        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.getId());
        assertEquals("Test Employee", createdEmployee.getName());
        assertEquals("Manager", createdEmployee.getPosition());
        assertTrue(createdEmployee.isActive());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);
        Employee createdEmployee = employeeService.createEmployee(employee);

        Employee foundEmployee = employeeService.getEmployeeById(createdEmployee.getId());
        assertNotNull(foundEmployee);
        assertEquals(createdEmployee.getId(), foundEmployee.getId());
        assertEquals("Test Employee", foundEmployee.getName());
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);
        Employee createdEmployee = employeeService.createEmployee(employee);

        createdEmployee.setName("Updated Employee");
        createdEmployee.setPosition("Senior Manager");
        Employee updatedEmployee = employeeService.updateEmployee(createdEmployee.getId(), createdEmployee);
        assertNotNull(updatedEmployee);
        assertEquals("Updated Employee", updatedEmployee.getName());
        assertEquals("Senior Manager", updatedEmployee.getPosition());
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);
        Employee createdEmployee = employeeService.createEmployee(employee);

        employeeService.deleteEmployee(createdEmployee.getId());
        assertThrows(IllegalStateException.class, () -> {
            employeeService.getEmployeeById(createdEmployee.getId());
        });
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setName("Test Employee 1");
        employee1.setPosition("Manager");
        employee1.setActive(true);
        employeeService.createEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Test Employee 2");
        employee2.setPosition("Developer");
        employee2.setActive(true);
        employeeService.createEmployee(employee2);

        List<Employee> employees = employeeService.getAllEmployees();
        assertNotNull(employees);
        assertTrue(employees.size() >= 2);
    }

    @Test
    void testGetActiveEmployees() {
        Employee employee1 = new Employee();
        employee1.setName("Test Employee 1");
        employee1.setPosition("Manager");
        employee1.setActive(true);
        employeeService.createEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Test Employee 2");
        employee2.setPosition("Developer");
        employee2.setActive(false);
        employeeService.createEmployee(employee2);

        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        assertNotNull(activeEmployees);
        assertTrue(activeEmployees.stream().allMatch(Employee::isActive));
    }

    @Test
    void testDeactivateEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setPosition("Manager");
        employee.setActive(true);
        Employee createdEmployee = employeeService.createEmployee(employee);

        employeeService.deactivateEmployee(createdEmployee.getId());
        Employee deactivatedEmployee = employeeService.getEmployeeById(createdEmployee.getId());
        assertFalse(deactivatedEmployee.isActive());
    }
} 