package com.example.livesystemapp.service.WebSocket;

import com.alibaba.fastjson.JSONObject;
import com.example.livesystemapp.util.FireCurtain.FireCurtainUtil;
import com.example.livesystemapp.util.NotificationsUtil;
import com.example.livesystemapp.util.Param.GlobalParam;
import com.example.livesystemapp.util.SceneUtil;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWebSocketClient extends WebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocketClient.class);

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOGGER.info("------ MyWebSocket onOpen ------");
    }

    @Override
    public void onMessage(String message) {
        if(message == null && message.length() == 0) {
            return;
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(message);
            String secret = jsonObject.getString("secret");

            if(secret!=null && secret.equals("heart_keep")) {
                //如果收到了心跳 这里设置isHeart为true
                jsonObject =new JSONObject();
                jsonObject.put("secret", "ping");
                send(jsonObject.toString());
            }
        } catch (Exception e) {
            //判断发送给谁，具体规则自定义
            //私聊信息规则 "FRIEND:"+FriendID+";"+"USER:"+UserID+";"+message
            //群消息规则   "GROUP:"+GroupID+";"+"USER:"+UserID+";"+message
            LOGGER.info("-------- 接收到服务端数据： " + message + "--------");
            int second = message.substring(message.indexOf(";")+1, message.length()-1).indexOf(";") + message.indexOf(";") + 1;
            String FriendID = message.substring(message.indexOf("USER:")+5,second);
            String Msg = message.substring(second+1);
            if(message.indexOf("FRIEND:") == 0) {
                String UserID = message.substring(message.indexOf("FRIEND:")+7,message.indexOf(";"));
                onFriendSMS(Integer.valueOf(UserID),Integer.valueOf(FriendID),Msg);
            }else if(message.indexOf("GROUP:") == 0) {
                String GroupID = message.substring(message.indexOf("GROUP:")+6,message.indexOf(";"));
                //如果正在直播
                if(GlobalParam.LiveGroup != null && GlobalParam.LiveGroup.equals(GroupID)) {
                    FireCurtainUtil.sendFireCurtain(Msg);
                }
                onGroupSMS(Integer.valueOf(GroupID),Integer.valueOf(FriendID),Msg);
            }else if(message.indexOf("Interaction:") == 0) {
                String userName = message.substring(message.indexOf("Interaction:")+12,message.indexOf(";"));
                Platform.runLater(() -> {
                    //更新JavaFX的主线程的代码放在此处
                    NotificationsUtil.getInteraction(userName, Msg);
                });
            }
        }
    }

    /**
     * 显示私聊消息
     * @param UserID
     * @param FriendID
     * @param message
     */
    public void onFriendSMS(Integer UserID, Integer FriendID, String message) {

        Platform.runLater(() -> {
            //更新JavaFX的主线程的代码放在此处
//            直接添加聊天气泡，需要手动创建短信json
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("mmsgcontent", message);
//            jsonObject.put("mfromuserid", FriendID);
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString = formatter.format(date);
//            jsonObject.put("gmCreatetime", dateString);
//                VBox vBox;
//                if(GlobalParam.SelectFriendID == UserID) {
//                    vBox = GlobalParam.paneForFriend.get(UserID);
//                    if(vBox != null) {
//                        new SceneUtil().addBubble(vBox, jsonObject, UserID.toString());
//                    }
//                } else if(GlobalParam.SelectFriendID == FriendID) {
//                    vBox = GlobalParam.paneForFriend.get(FriendID);
//                    if(vBox != null) {
//                        new SceneUtil().addBubble(vBox, jsonObject, UserID.toString());
//                    }
//                } else {
//                    //没有看到的消息，即不是目前打开的聊天室，就弹出消息提醒，不用添加气泡。
//                    NotificationsUtil.getMsg("收到好友发来的消息："+message);
//                }
            //改用点击刷新
            if(GlobalParam.SelectFriendID == FriendID || GlobalParam.SelectFriendID == UserID) {
                Integer sel = GlobalParam.SelectFriendID;
                Event.fireEvent(GlobalParam.homeController.live,new MouseEvent(MouseEvent.MOUSE_CLICKED,
                        1,1,1,1, MouseButton.PRIMARY, 1,
                        true, true, true, true,
                        true, true, true,
                        true, true, true, null));
                Event.fireEvent(GlobalParam.controllerForFriend.get(sel).bgColor,new MouseEvent(MouseEvent.MOUSE_CLICKED,
                        1,1,1,1, MouseButton.PRIMARY, 1,
                        true, true, true, true,
                        true, true, true,
                        true, true, true, null));
            } else {
                //没有看到的消息，即不是目前打开的聊天室，就弹出消息提醒，不用添加气泡。
                NotificationsUtil.getMsg("收到好友发来的消息："+message);
            }
        });
    }

    /**
     * 显示群消息
     * @param GroupID
     * @param UserID
     * @param message
     */
    public void onGroupSMS(Integer GroupID, Integer UserID, String message) {
        Platform.runLater(() -> {
            //更新JavaFX的主线程的代码放在此处
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("gmContent", message);
//            jsonObject.put("gmFromid", UserID);
//            //获取群用户的群内昵称，获取还是记录？
//            jsonObject.put("groupNick", UserID);
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//            String dateString = formatter.format(date);
//            jsonObject.put("gmCreatetime", dateString);
//            if(GroupID == GlobalParam.SelectGroupID) {
//                VBox vBox = GlobalParam.paneForGroup.get(GroupID);
//                if(vBox != null) {
//                    new SceneUtil().addBubble(vBox, jsonObject, UserID.toString());
//                }
//            } else {
//                //没有看到的消息，即不是目前打开的聊天室，就弹出消息提醒，不用添加气泡。
//                NotificationsUtil.getMsg("收到群聊发来的消息："+message);
//            }
            //改用点击刷新
            if(GlobalParam.SelectGroupID == GroupID) {
                Integer sel = GlobalParam.SelectGroupID;
                Event.fireEvent(GlobalParam.homeController.live,new MouseEvent(MouseEvent.MOUSE_CLICKED,
                        1,1,1,1, MouseButton.PRIMARY, 1,
                        true, true, true, true,
                        true, true, true,
                        true, true, true, null));
                Event.fireEvent(GlobalParam.controllerForGroup.get(sel).bgColor,new MouseEvent(MouseEvent.MOUSE_CLICKED,
                        1,1,1,1, MouseButton.PRIMARY, 1,
                        true, true, true, true,
                        true, true, true,
                        true, true, true, null));
            } else {
                //没有看到的消息，即不是目前打开的聊天室，就弹出消息提醒，不用添加气泡。
                NotificationsUtil.getMsg("收到群聊发来的消息："+message);
            }
        });
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        LOGGER.info("------ MyWebSocket onClose ------{}",s);
        WSClient.startWS();
    }

    @Override
    public void onError(Exception e) {
        LOGGER.info("------ MyWebSocket onError ------{}",e);
    }
}
