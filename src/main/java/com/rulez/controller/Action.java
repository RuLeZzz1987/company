package com.rulez.controller;

import org.json.JSONObject;

/**
 * Created by dukaa on 17.11.2014.
 */
enum Action {
    CREATE, UPDATE, DELETE, UNKNOWN, VALIDATE;

    public static Action parseAction(JSONObject jsonObject) {
        try {
            return Action.valueOf(jsonObject.getString("action"));
        } catch (Exception e) {
            return Action.UNKNOWN;
        }
    }
}
