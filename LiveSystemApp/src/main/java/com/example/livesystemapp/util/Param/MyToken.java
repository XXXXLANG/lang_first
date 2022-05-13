package com.example.livesystemapp.util.Param;

import com.alibaba.fastjson.JSONObject;

public class MyToken {

    private static JSONObject myToken = new JSONObject();

    public void setToken(String Token) {
        myToken.put("Token",Token);
    }

    public String getToken() {
        return myToken.getString("Token");
    }
}
