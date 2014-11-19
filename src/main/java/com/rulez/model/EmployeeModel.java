package com.rulez.model;

import java.util.Date;

/**
 * Created by dukaa on 17.11.2014.
 */
public class EmployeeModel {

    private int id;
    private String name;
    private DepartmentModel department;
    private String email;
    private Date hireDate;

    public EmployeeModel() {
        this.department = new DepartmentModel();
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
}
