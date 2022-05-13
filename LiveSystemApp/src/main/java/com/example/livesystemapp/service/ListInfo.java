package com.example.livesystemapp.service;

import com.alibaba.fastjson.JSONArray;
import com.example.livesystemapp.util.HttpRequest.GetRequest;

public class ListInfo {

    private GetRequest getRequest = new GetRequest();

    public JSONArray getFriendsList() { return getRequest.getRequest("home/friendsList"); }

    public JSONArray getGroupsList() {
        return getRequest.getRequest("home/groupsList");
    }

}
