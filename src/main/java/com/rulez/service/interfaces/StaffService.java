package com.rulez.service.interfaces;

import com.rulez.model.EmployeeModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.Date;
import java.util.List;

/**
 * Created by dukaa on 17.11.2014.
 */
public interface StaffService {

    List<EmployeeModel> get(int id);

    void create(String name, String email, int id_department, Date hireDate);

    void delete(int id_employee);

    void update(int id, String name, String email, int id_department, Date hireDate);

    JSONObject getPayloadData(BufferedReader reader);

    JSONArray getJSONArray(int id);

    boolean isEmployeeDataValid(String name, String email, int id_department);

    boolean isEmployeeNameValid(String name);

    boolean isEmployeeEmailValid(String email);

    JSONObject isEmailUnique(String email);
}
