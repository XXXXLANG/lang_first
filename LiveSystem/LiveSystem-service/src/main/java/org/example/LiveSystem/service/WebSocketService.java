package org.example.LiveSystem.service;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;

public interface WebSocketService {

    /**
     * 订阅服务器
     * @param session
     */
    void OnOpen(Session session, @PathParam(value = "token") String token) throws IOException;

    /**
     * 取消订阅服务器
     */
    void OnClose() throws IOException;

    /**
     * 服务器接收信息
     * @param message
     */
    void OnMessage(String message, @PathParam(value = "token") String token) throws IOException;

    /**
     * 发生错误时的回调函数
     * @param session
     * @param error
     */
    void onError(Session session, Throwable error);

    /**
     * 群消息发送
     * @param GroupID
     * @param UserID
     * @param message
     */
    void GroupSending(Integer GroupID, Integer UserID, String message, String SMS);

    /**
     * 指定发送
     * @param UserID
     * @param FriendID
     * @param message
     */
    void AppointSending(Integer UserID, Integer FriendID, String message, String SMS);

    /**
     * 判断用户是否在线
     *
     * @param Fid
     * @return
     */
    Boolean isOnLine(Integer Fid);

    /**
     * 获取连接
     *
     * @param Fid
     * @return
     */
    Session getSession(Integer Fid);
}