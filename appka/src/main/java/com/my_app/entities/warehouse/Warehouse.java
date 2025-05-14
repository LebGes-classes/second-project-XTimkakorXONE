package com.my_app.entities.warehouse;

import java.util.ArrayList;
import java.util.List;

import com.my_app.entities.employee.Employee;
import com.my_app.entities.warehouse_cell.Cell;

public class Warehouse {
    private Integer id;
    private String adress;
    private List<Cell> cells;
    private boolean isActive;
    private List<Employee> employees;

    public Warehouse() {
        this.cells = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Warehouse(String adress, List<Cell> cells, boolean isActive) {
        this.adress = adress;
        this.cells = cells;
        this.isActive = isActive;
        this.employees = new ArrayList<>();
    }

    public int storeSize() {
        return cells.size();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void addCell(Cell cell) {
        cell.setWarehouseId(this.id);
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }

    public void setCells(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.setWarehouseId(this.id);
        }
        this.cells = cells;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
