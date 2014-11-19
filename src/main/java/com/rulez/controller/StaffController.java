package com.rulez.controller;

import com.rulez.service.StaffServiceImpl;
import com.rulez.service.interfaces.StaffService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by dukaa on 13.11.2014.
 */
public class StaffController extends HttpServlet {

    private StaffService staffService = new StaffServiceImpl();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id_department", req.getParameter("id_department"));
        req.setAttribute("name_department", req.getParameter("name_department"));
        req.getRequestDispatcher("WEB-INF/jsp/staff.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json; charset=UTF-8");
        Action action;
        try {
            PrintWriter writer = resp.getWriter();
            JSONObject payloadData = staffService.getPayloadData(req.getReader());
            JSONArray array = new JSONArray();
            int current_id = Integer.parseInt(req.getParameter("id_department"));

            try {
                try {
                    action = Action.valueOf(payloadData.getString("action"));
                } catch (Exception e) {
                    action = Action.UNKNOWN;
                }
                switch (action) {
                    case CREATE: {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                        staffService.create(payloadData.getString("model_name"),
                                payloadData.getString("model_email"),
                                payloadData.getInt("model_id_department"),
                                format.parse(payloadData.getString("model_hire_date")));
                        array = staffService.getJSONArray(current_id);
                        break;
                    }
                    case DELETE: {
                        staffService.delete(payloadData.getInt("model_id"));
                        array = staffService.getJSONArray(current_id);
                        break;
                    }
                    case UPDATE: {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                        staffService.update(payloadData.getInt("model_id"),
                                payloadData.getString("model_name"),
                                payloadData.getString("model_email"),
                                payloadData.getInt("model_id_department"),
                                format.parse(payloadData.getString("model_hire_date")));
                        array = staffService.getJSONArray(current_id);
                        break;
                    }
                    case VALIDATE: {
                        JSONObject validation_result = staffService.isEmailUnique(payloadData.getString("model_email"));
                        array.put(validation_result);
                        break;
                    }
                    default: {
                        array = staffService.getJSONArray(current_id);
                    }
                }

            } catch (Exception e) { /*NOP*/ }
            writer.println(array);
            writer.flush();
        } catch (IOException e) { /*NOP*/ }

    }


}
