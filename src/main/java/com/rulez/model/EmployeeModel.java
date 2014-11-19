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

    public EmployeeModel(int id, String name, int id_department, String name_department, String email, Date hireDate) {
        this.id = id;
        this.name = name;
        this.department = new DepartmentModel(id_department, name_department);
        this.email = email;
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return new Date(hireDate.getTime());
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DepartmentModel getDepartment() {
        return department;
    }


}
