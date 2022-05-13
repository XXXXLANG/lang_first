package com.example.livesystemapp.service;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Login {

    private PostRequest postRequest = new PostRequest();

    public JSONObject pwLogin(String LoginID, String password) {
        String url = "user/login/password";
        //        请求表
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("LoginID", LoginID);
        paramMap.add("password", password);

        return postRequest.loginRequest(paramMap, url);
    }

    public void smsRequest(String phone) {
        String url = "sms/verifyCode";
        //        请求表
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("phone", phone);
        paramMap.add("codeType", 0);
        postRequest.smsRequest(paramMap, url);
    }

    public JSONObject smsLogin(String phone, String sms) {
        String url = "user/login/sms";
        //        请求表
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("phone", phone);
        paramMap.add("sms", sms);

        return postRequest.loginRequest(paramMap, url);
    }

    public void logout() {
        String url = "user/logout";
        postRequest.postReObject(new LinkedMultiValueMap<>(), url);
    }
}
