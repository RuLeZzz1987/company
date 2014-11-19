package com.rulez.service;

import org.json.JSONObject;

import java.io.BufferedReader;

/**
 * Created by dukaa on 17.11.2014.
 */
public class JSONparser {

    public static JSONObject getPayloadData(BufferedReader reader) {
        JSONObject obj = null;
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            obj = new JSONObject(buffer.toString());
        } catch (Exception e) {
            /*NOP*/
        }
        return obj;
    }
}
