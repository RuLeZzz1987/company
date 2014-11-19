package com.rulez.model;

/**
 * Created by dukaa on 13.11.2014.
 */
public class DepartmentModel {

    private int id;
    private String name;

    public DepartmentModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}