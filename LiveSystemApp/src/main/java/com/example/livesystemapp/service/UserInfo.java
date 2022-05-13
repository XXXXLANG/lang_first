package com.example.livesystemapp.service;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.controller.InfoDispaly.UserInfoController;
import com.example.livesystemapp.util.HttpRequest.GetRequest;
import com.example.livesystemapp.util.HttpRequest.PostRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class UserInfo {

    private GetRequest getRequest = new GetRequest();
    private PostRequest postRequest = new PostRequest();

    /**
     * 通过token获取保存在Redis的用户信息
     * @return
     */
    public JSONObject getUserInfo() {
        return getRequest.getReObject("user/info");
    }

    public JSONObject getFriendInfo(String tel) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("tel", tel);
        return postRequest.postReObject(paramMap, "user/getFriendInfo");
    }

    public JSONObject getGroupInfo(String GroupID) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("GroupID", GroupID);
        return postRequest.postReObject(paramMap, "user/getGroupInfo");
    }

    public UserInfoController insetUserInfo(UserInfoController userInfoController) {
        JSONObject jsonObject = getUserInfo();
        userInfoController.setLocation(jsonObject.getString("unationid"), jsonObject.getString("uprovinceid"), jsonObject.getString("ucityid"));
        userInfoController.setBirthday(jsonObject.getString("ubirthday"));
        userInfoController.setTel(jsonObject.getString("utelephone"));
        userInfoController.setSex(jsonObject.getString("usex"));
        userInfoController.setSignaTure(jsonObject.getString("usignature"));
        userInfoController.setUserName(jsonObject.getString("unickname"));
        userInfoController.setUserFace(jsonObject.getString("uHeadportrait"));
        userInfoController.setPassword(jsonObject.getString("upassword"));
        return userInfoController;
    }
}
