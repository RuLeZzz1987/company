package com.rulez.controller;

import com.rulez.service.DepartmentServiceImpl;
import com.rulez.service.interfaces.DepartmentService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dukaa on 13.11.2014.
 */
public class DepartmentController extends HttpServlet {

    private DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/departments.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        JSONObject payloadData = departmentService.getPayloadData(req.getReader());
        JSONArray array = new JSONArray();

        try {
            Action action = Action.parseAction(payloadData);
            switch (action) {
                case CREATE: {
                    departmentService.create(payloadData.getString("model_name"));
                    array = departmentService.getJSONArray();
                    break;
                }
                case DELETE: {
                    departmentService.delete(payloadData.getInt("model_id"));
                    array = departmentService.getJSONArray();
                    break;
                }
                case UPDATE: {
                    departmentService.update(payloadData.getInt("model_id"), payloadData.getString("model_new_name"));
                    array = departmentService.getJSONArray();
                    break;
                }
                case VALIDATE: {
                    JSONObject validation_result = departmentService.isNameUnique(payloadData.getString("model_name"));
                    array.put(validation_result);
                    break;
                }

                default: {
                    array = departmentService.getJSONArray();
                    break;
                }
            }
        } catch (Exception e) {
            /*NOP*/
        }
        writer.println(array);
        writer.flush();
    }



}
