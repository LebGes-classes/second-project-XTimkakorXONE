package com.my_app.storage_service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.my_app.entities.employee.Employee;
import com.my_app.utils.IdGenerator;
import com.my_app.utils.JsonStorageService;

public class EmployeeJsonStorage extends JsonStorageService<Employee> {

    public EmployeeJsonStorage() {
        super("storage/Employee.json", Employee.class);
    }

    public void saveEmployee(Employee employee) throws IOException {
        if (employee == null) {
            throw new IllegalArgumentException("Сотрудник не может быть null");
        }
        List<Employee> employees = readFile();
        employees.add(employee);
        writeFile(employees);
    }

    public List<Employee> getAllEmployees() throws IOException {
        return readFile();
    }

    public Employee getEmployeeById(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        return readFile().stream()
            .filter(e -> Objects.equals(e.getId(), id))
            .findFirst()
            .orElse(null);
    }

    public void updateEmployee(Employee employee) throws IOException {
        if (employee == null) {
            throw new IllegalArgumentException("Сотрудник не может быть null");
        }
        List<Employee> employees = readFile();
        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getId(), employee.getId())) {
                employees.set(i, employee);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalStateException("Сотрудник с ID " + employee.getId() + " не найден");
        }
        writeFile(employees);
    }

    public void deleteEmployee(Integer id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        List<Employee> employees = readFile();
        boolean removed = employees.removeIf(e -> Objects.equals(e.getId(), id));
        if (removed) {
            writeFile(employees);
        }
    }

    public Integer getNextEmployeeId() throws IOException {
        List<Employee> employees = readFile();
        return IdGenerator.getNextId(employees, Employee::getId);
    }
}
    