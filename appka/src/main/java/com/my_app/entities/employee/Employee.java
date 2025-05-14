package com.my_app.entities.employee;

public class Employee {
    private Integer id;
    private String name;
    private String position;
    private boolean isActive;
    private WorkPlace workPlace;

    public Employee() {
    }

    public Employee(String name, String position, boolean isActive, WorkPlace workPlace) {
        this.name = name;
        this.position = position;
        this.isActive = isActive;
        this.workPlace = workPlace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }
}
