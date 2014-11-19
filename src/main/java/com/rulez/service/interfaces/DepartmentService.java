package com.rulez.service.interfaces;

import com.rulez.model.DepartmentModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.List;

/**
 * Created by dukaa on 13.11.2014.
 */
public interface DepartmentService {

    void create(String department_name);

    List<DepartmentModel> get();

    void delete(int id_department);

    void update(int old_id_department, String new_department_name);

    JSONObject getPayloadData(BufferedReader reader);

    JSONArray getJSONArray();

    JSONObject isNameUnique(String model_name);

    boolean isDepartmentNameValid(String model_name);
}
