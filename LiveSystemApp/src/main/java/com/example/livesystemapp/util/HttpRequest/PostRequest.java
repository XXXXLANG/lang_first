package com.example.livesystemapp.util.HttpRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.util.Param.MyToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PostRequest {
    //         请求头对象
    private HttpHeaders headers = new HttpHeaders();
    //         请求客户端
    private RestTemplate client = new RestTemplate();

    private MyToken myToken = new MyToken();

    private String url = "http://localhost:8080/api/";

    private HttpEntity<MultiValueMap<String, Object>> httpEntity = null;

    public PostRequest() {
        //        设置请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("User-Agent", "Mozilla/5.0 WindSnowLi-Blog");
    }

    public void sendFile(MultiValueMap<String,Object> parts, String str) {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Token",new MyToken().getToken());
        httpEntity = new HttpEntity<>(parts, headers);
        client.postForEntity(url+str, httpEntity, JSONObject.class);
    }

    public JSONObject postReObject(MultiValueMap<String, Object> paramMap, String str) {
        //      整合请求头和请求参数
        headers.set("Token",new MyToken().getToken());
        httpEntity = new HttpEntity<>(paramMap, headers);
        //      发起请求
        JSONObject body = client.postForEntity(url+str, httpEntity, JSONObject.class).getBody();
        if(body != null) {
            System.out.println("******** POST请求 *********");
            System.out.println(body.toJSONString());
            if(body.getString("code").equals("0")) {
                try {
                    if(body.getJSONObject("data") == null) {
                        return body;
                    }
                    return body.getJSONObject("data");
                } catch (Exception e) {
                    return body;
                }
            }
        }
        return null;
    }

    public JSONArray postReArray(MultiValueMap<String, Object> paramMap, String str) {
        //      整合请求头和请求参数
        headers.set("Token",new MyToken().getToken());
        httpEntity = new HttpEntity<>(paramMap, headers);
        //      发起请求
        JSONObject body = client.postForEntity(url+str, httpEntity, JSONObject.class).getBody();
        if(body != null) {
            System.out.println("******** POST请求 *********");
            System.out.println(body.toJSONString());
            if(body.getString("code").equals("0")) {
                return body.getJSONArray("data");
            }
        }
        return null;
    }

    public JSONObject loginRequest(MultiValueMap<String, Object> paramMap, String str) {
        //        整合请求头和请求参数
        httpEntity = new HttpEntity<>(paramMap, headers);
        //      发起请求
        JSONObject body = client.postForEntity(url+str, httpEntity, JSONObject.class).getBody();
        System.out.println("******** POST请求 *********");
        assert body != null;
        System.out.println(body.toJSONString());
        String code = body.getString("code");
        if(code.equals("0")) {
            myToken.setToken(body.getJSONObject("data").getString("token"));
        }
        return body;
    }

    public JSONObject smsRequest(MultiValueMap<String, Object> paramMap, String str) {
        //        整合请求头和请求参数
        httpEntity = new HttpEntity<>(paramMap, headers);
        //      发起请求
        JSONObject body = client.postForEntity(url+str, httpEntity, JSONObject.class).getBody();
        System.out.println("******** POST请求 *********");
        assert body != null;
        System.out.println(body.toJSONString());
        return body;
    }
}
