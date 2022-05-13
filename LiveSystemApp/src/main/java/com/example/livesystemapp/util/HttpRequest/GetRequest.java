package com.example.livesystemapp.util.HttpRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.util.Param.MyToken;
import org.apache.catalina.util.ParameterMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GetRequest {
    //         请求头对象
    private HttpHeaders headers = new HttpHeaders();

    private MyToken myToken = new MyToken();

    public GetRequest() {
        //        设置请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("User-Agent", "Mozilla/5.0 WindSnowLi-Blog");
        headers.set("Token", myToken.getToken());
    }

    public JSONArray getRequest(String str) {
        String url = "http://localhost:8080/api/"+str ;
        //         请求客户端
        RestTemplate client = new RestTemplate();
        //      发起请求
        JSONObject body = client.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                JSONObject.class,
                "param").getBody();
        if(body != null) {
            System.out.println("******** Get请求 *********");
            System.out.println(body);
            if(body.getString("code").equals("0")) {
                return body.getJSONArray("data");
            }
        }
        return null;
    }

    public JSONObject getReObject(String str) {
        String url = "http://localhost:8080/api/"+str ;
        //         请求客户端
        RestTemplate client = new RestTemplate();
        //      发起请求
        JSONObject body = client.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                JSONObject.class,
                "param").getBody();
        if(body != null) {
            System.out.println("******** Get请求 *********");
            System.out.println(body);
            if(body.getString("code").equals("0")) {
                return body.getJSONObject("data");
            }
        }
        return null;
    }
}
