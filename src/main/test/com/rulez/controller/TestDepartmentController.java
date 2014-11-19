package com.rulez.controller;

import com.rulez.service.interfaces.DepartmentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by DukaA on 19.11.2014.
 */
public class TestDepartmentController {


    @Test
    public void testDoPostCreateRow() throws IOException, JSONException, ServletException {
        DepartmentController controller = new DepartmentController();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        DepartmentService service = mock(DepartmentService.class);
        BufferedReader reader = mock(BufferedReader.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "CREATE");
        jsonObject.put("model_name", "testDepartment");
        JSONArray array = new JSONArray();
        JSONObject department = new JSONObject();
        department.put("model_id", 1);
        department.put("model_name", "testDepartment");
        array.put(department);

        PrintWriter writer = new PrintWriter(System.out);

        when(resp.getWriter()).thenReturn(writer);
        when(service.getPayloadData(reader)).thenReturn(jsonObject);
        when(service.getJSONArray()).thenReturn(array);

        controller.doPost(req, resp);
    }
}
