package com.rulez.service;

import com.rulez.db.ConnectionFactory;
import com.rulez.model.DepartmentModel;
import com.rulez.service.interfaces.DepartmentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dukaa on 13.11.2014.
 */
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public void create(String department_name) {
        if (isDepartmentNameValid(department_name)) {
            String sql = "INSERT INTO departments (name_department) VALUES (?)";
            try (Connection connection = ConnectionFactory.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                    preparedStatement.setString(1, department_name.toLowerCase());
                    preparedStatement.execute();
                } catch (SQLException e) { /*NOP*/ }
            } catch (SQLException e) { /*NOP*/ }
        }
    }

    @Override
    public List<DepartmentModel> get() {
        List<DepartmentModel> departmentModels = new ArrayList<>();
        String sql = "SELECT id_department, name_department FROM departments";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DepartmentModel model = new DepartmentModel();
                        model.setId(resultSet.getInt("id_department"));
                        model.setName(resultSet.getString("name_department"));
                        departmentModels.add(model);
                    }
                }
            } catch (SQLException e) { /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
        return departmentModels;
    }

    @Override
    public void delete(int department_id) {
        String sql = "DELETE FROM departments WHERE id_department = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                preparedStatement.setInt(1, department_id);
                preparedStatement.execute();
            } catch (SQLException e) { /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
    }

    @Override
    public void update(int old_id_department, String department_new_name) {
        if (isDepartmentNameValid(department_new_name)) {
            String sql = "UPDATE departments SET name_department = ? WHERE id_department = ?";
            try (Connection connection = ConnectionFactory.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                    preparedStatement.setString(1, department_new_name);
                    preparedStatement.setInt(2, old_id_department);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) { /*NOP*/ }
            } catch (SQLException e) { /*NOP*/ }
        }
    }

    @Override
    public JSONObject getPayloadData(BufferedReader reader) {
        return JSONparser.getPayloadData(reader);
    }

    @Override
    public JSONArray getJSONArray() {
        JSONArray array = new JSONArray();
        List<DepartmentModel> departmentModels = get();
        for(DepartmentModel model : departmentModels) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("model_id", model.getId());
                jsonObject.put("model_name", model.getName());
            } catch (JSONException e) { /*NOP*/ }
            array.put(jsonObject);
        }
        return array;
    }

    @Override
    public boolean isDepartmentNameValid(String model_name) {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z_0-9 ]{1,44}");
        Matcher matcher = pattern.matcher(model_name);
        if (matcher.matches()) {
            JSONObject bdValidation = isNameUnique(model_name);
            try {
                return Boolean.parseBoolean(bdValidation.getString("validation_result"));
            } catch (JSONException e) { /*NOP*/ }
        }
        return false;
    }

    @Override
    public JSONObject isNameUnique(String model_name) {
        JSONObject result = new JSONObject();
        String sql = "SELECT id_department FROM departments WHERE name_department = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, model_name.toLowerCase());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result.put("validation_result", false);
                    } else {
                        result.put("validation_result", true);
                    }
                    return result;
                } catch (SQLException e) { /*NOP*/ }
            } catch (SQLException e) { /*NOP*/ }
            catch (JSONException e) { /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
        return result;
    }
}
