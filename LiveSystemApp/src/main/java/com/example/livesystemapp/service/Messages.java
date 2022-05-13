package com.example.livesystemapp.service;

import com.alibaba.fastjson.JSONArray;
import com.example.livesystemapp.util.HttpRequest.GetRequest;

public class Messages {

    private GetRequest getRequest = new GetRequest();

    /**
     * 获取两人间的私聊信息，每次获取15条，需要指定已经获取的最晚信息ID
     * @param FriendID
     * @param lastMID
     * @return
     */
    public JSONArray getFriendMsg(Integer FriendID, Integer lastMID) {
        String str = "?FriendID="+FriendID;
        if(lastMID != null) {
            str += "&lastMID="+lastMID;
        }
        return getRequest.getRequest("Msg/getFriendMsg"+str);
    }

    /**
     * 获取群聊信息，每次获取15条，需要指定已经获取的最晚信息ID
     * @param GroupID
     * @param lastGMID
     * @return
     */
    public JSONArray getGroupMsg(Integer GroupID, Integer lastGMID) {
        String str = "?GroupID="+GroupID;
        if(lastGMID != null) {
            str += "&lastGMID="+lastGMID;
        }
        return getRequest.getRequest("Msg/getGroupMsg"+str);
    }

    /**
     * 获取聊天列表，保持联系的人或群
     * @return
     */
    public JSONArray getChartList() {
        return getRequest.getRequest("Msg/getChatList");
    }
}
