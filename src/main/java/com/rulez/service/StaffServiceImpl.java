package com.rulez.service;

import com.rulez.db.ConnectionFactory;
import com.rulez.model.EmployeeModel;
import com.rulez.service.interfaces.StaffService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dukaa on 17.11.2014.
 */
public class StaffServiceImpl implements StaffService {

    @Override
    public List<EmployeeModel> get(int id) {
        List<EmployeeModel> employeeModels = new ArrayList<>();
        String sql = "SELECT id_employee, name_employee, email_employee, s.id_department, name_department, hire_date FROM staff s LEFT JOIN departments d ON s.id_department = d.id_department WHERE s.id_department = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        EmployeeModel model = new EmployeeModel();
                        model.setId(resultSet.getInt("id_employee"));
                        model.setName(resultSet.getString("name_employee"));
                        model.setEmail(resultSet.getString("email_employee"));
                        model.getDepartment().setId(resultSet.getInt("id_department"));
                        model.getDepartment().setName(resultSet.getString("name_department"));
                        model.setHireDate(resultSet.getDate("hire_date"));
                        employeeModels.add(model);
                    }
                } catch (SQLException e) { /*NOP*/ }
            }catch(SQLException e){ /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
        return employeeModels;
    }

    @Override
    public void create(String name, String email, int id_department, Date hireDate) {
        if (isEmployeeDataValid(name, email, id_department)) {
            Boolean isMailValidForCreate = false;
            try {
                isMailValidForCreate =  isEmailUnique(email).getBoolean("validation_result");
            } catch (JSONException e) { /*NOP*/ }
            if (isMailValidForCreate) {
                String sql = "INSERT INTO staff (name_employee, email_employee, id_department, hire_date) VALUES (?, ?, ?, ?)";
                try (Connection connection = ConnectionFactory.getConnection()) {
                    try (PreparedStatement statement = connection.prepareStatement(sql);) {
                        statement.setString(1, name);
                        statement.setString(2, email);
                        statement.setInt(3, id_department);
                        statement.setDate(4, new java.sql.Date(hireDate.getTime()));
                        statement.execute();
                    } catch (SQLException e) { /*NOP*/ }
                } catch (SQLException e) { /*NOP*/ }
            }
        }
    }

    @Override
    public void delete(int id_employee) {
        String sql = "DELETE FROM staff WHERE id_employee = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_employee);
                statement.execute();
            } catch (SQLException e) { /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
    }

    @Override
    public void update(int id, String name, String email, int id_department, Date hireDate) {
        if (isEmployeeDataValid(name, email, id_department)) {
            Boolean isMailValidForUpdate = false;
            try {
                isMailValidForUpdate = isEmailUnique(email).getBoolean("validation_result");
                if (!isMailValidForUpdate) {
                    isMailValidForUpdate = isEmailUnique(email).getInt("valid_for_update") == id;
                }
            } catch (JSONException e) { /*NOP*/ }
            if (isMailValidForUpdate) {
                String sql = "UPDATE staff SET name_employee = ?, email_employee = ?, id_department = ?, hire_date = ? WHERE id_employee = ?";
                try (Connection connection = ConnectionFactory.getConnection()) {
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, name);
                        statement.setString(2, email);
                        statement.setInt(3, id_department);
                        statement.setDate(4, new java.sql.Date(hireDate.getTime()));
                        statement.setInt(5, id);
                        statement.executeUpdate();
                    } catch (SQLException e) { /*NOP*/ }
                } catch (SQLException e) { /*NOP*/ }
            }
        }
    }

    @Override
    public JSONObject getPayloadData(BufferedReader reader) {
        return JSONparser.getPayloadData(reader);
    }

    @Override
    public JSONArray getJSONArray(int id) {
        JSONArray array = new JSONArray();
        List<EmployeeModel> employeeModels = get(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
        for (EmployeeModel model : employeeModels) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("model_id", model.getId());
                jsonObject.put("model_name", model.getName());
                jsonObject.put("model_email", model.getEmail());
                jsonObject.put("model_name_department", model.getDepartment().getName());
                jsonObject.put("model_hire_date", format.format(model.getHireDate()));
            } catch (JSONException e) { /*NOP*/ }
            array.put(jsonObject);
        }
        return array;
    }

    @Override
    public boolean isEmployeeDataValid(String name, String email, int id_department) {
        return isEmployeeNameValid(name) &&
                isEmployeeEmailValid(email) &&
                isId_departmentValid(id_department);
    }

    public boolean isId_departmentValid(int id_department) {
        String sql = "SELECT id_department FROM departments WHERE id_department = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_department);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException e) { /*NOP*/ }
            } catch (SQLException e) { /*NOP*/ }
        } catch (SQLException e) { /*NOP*/ }
        return false;
    }

    @Override
    public boolean isEmployeeNameValid(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z_0-9 ]{1,44}");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public boolean isEmployeeEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public JSONObject isEmailUnique(String email) {
        JSONObject result = new JSONObject();
        String sql = "SELECT id_employee FROM staff WHERE email_employee = ?";
        try(Connection connection = ConnectionFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email.toLowerCase());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result.put("validation_result", false);
                        result.put("valid_for_update", resultSet.getInt(1));
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
