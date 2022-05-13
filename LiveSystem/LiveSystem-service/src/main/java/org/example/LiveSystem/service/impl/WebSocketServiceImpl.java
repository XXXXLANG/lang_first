package org.example.LiveSystem.service.impl;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.LiveSystem.dao.*;
import org.example.LiveSystem.dto.UserGroupstouserDTO;
import org.example.LiveSystem.dto.UsersInfoDTO;
import org.example.LiveSystem.entity.*;
import org.example.LiveSystem.service.GroupsService;
import org.example.LiveSystem.service.TokenService;
import org.example.LiveSystem.service.WebSocketService;
import org.example.LiveSystem.util.SpringContext;
import org.springframework.stereotype.Service;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@ServerEndpoint("/websocket/{token}")
public class WebSocketServiceImpl implements WebSocketService {

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名UserID
     */
    private String name;

    /**
     * 记录下次发送时间的时间戳
     */
    private long timeStr;

    /**
     * 是否收到了心跳
     */
    private boolean isHeart = false;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, WebSocketServiceImpl> webSocketSet = new ConcurrentHashMap<>();

    public TokenService getTokenService() {
        return SpringContext.getBean(TokenService.class);
    }

    public UserMsgusertouserDao getUserMsgusertouserDao() {
        return SpringContext.getBean(UserMsgusertouserDao.class);
    }

    public UserGroupsmsgcontentDao getUserGroupsmsgcontentDao() {
        return SpringContext.getBean(UserGroupsmsgcontentDao.class);
    }

    public UserGroupsmsgtouserDao getUserGroupsmsgtouserDao() {
        return SpringContext.getBean(UserGroupsmsgtouserDao.class);
    }

    public InteractionDao getInteractionDao() {
        return SpringContext.getBean(InteractionDao.class);
    }

    public GroupsService getGroupsService() {
        return SpringContext.getBean(GroupsService.class);
    }

    public UserDao getUserDao() {
        return SpringContext.getBean(UserDao.class);
    }

    /**
     * 订阅服务器
     * @param session
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "token") String token) throws IOException {
        this.session = session;
        //判断token是否正确
        User user = getTokenService().getUserInfoByToken(token);
        if(user != null){
            // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
            name = user.getUId().toString();
            webSocketSet.put(name,this);
            log.info("[WebSocket] 连接成功，当前连接人数为：={}",webSocketSet.size());
            if(webSocketSet.size()==1 && !isHeart){
                isHeart=true;
                startHeart();
            }
        } else {
            log.info("[WebSocket] 连接失败，非法用户错误Token："+token);
        }
    }

    /**
     * 取消订阅服务器
     */
    @OnClose
    public void OnClose() throws IOException {
        try {
            webSocketSet.remove(this.name);
        } catch (Exception e) {
            log.info("[WebSocket] 用户："+this.name+"连接以提前断开连接");
        }
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    /**
     * 服务器接收信息,并转发到群组或用户
     * @param message
     */
    @OnMessage
    public void OnMessage(String message, @PathParam(value = "token") String token) throws IOException {
        if(message == null && message.length() == 0) {
            return;
        }
        User user = getTokenService().getUserInfoByToken(token);
        if(user == null){
            log.info("[WebSocket] 连接失败，非法用户错误Token："+token);
            return;
        }
        try{
            JSONObject jsonObject = new JSONObject(message);
            String ping = jsonObject.getStr("secret");
            //心跳判断
            if(ping!=null && ping.equals("ping")) {
//                log.info("收到" + user.getUId() + "的心跳" + message);
                //如果收到了心跳 这里设置isHeart为true
                WebSocketServiceImpl entity = webSocketSet.get(user.getUId());
                if (null != entity) {
                    entity.isHeart = true;
                }
            }
        } catch (Exception e) {
            //判断发送给谁，具体规则自定义
            //私聊信息规则 "FRIEND:"+FriendID+";"+"USER:"+UserID+";"+message
            //群消息规则   "GROUP:"+GroupID+";"+"USER:"+UserID+";"+message
            log.info("[WebSocket] 收到消息：{}",message);
            int second = message.substring(message.indexOf(";")+1, message.length()-1).indexOf(";") + message.indexOf(";") + 1;
            String FriendID = message.substring(message.indexOf("USER:")+5,second);
            String Msg = message.substring(second+1);
            if(message.indexOf("FRIEND:") == 0){
                String UserID = message.substring(message.indexOf("FRIEND:")+7,message.indexOf(";"));
                AppointSending(Integer.valueOf(UserID),Integer.valueOf(FriendID),Msg,message);
            }else if(message.indexOf("GROUP:") == 0) {
                String GroupID = message.substring(message.indexOf("GROUP:")+6,message.indexOf(";"));
                GroupSending(Integer.valueOf(GroupID),Integer.valueOf(FriendID),Msg,message);
            }else if(message.indexOf("Interaction:") == 0) {
                String forID = message.substring(message.indexOf("Interaction:")+12,message.indexOf(";"));
                message = "Interaction:"+getUserDao().getUserName(Integer.valueOf(forID))+message.substring(message.indexOf(";"));
                InteractionInfo(Integer.valueOf(FriendID), Integer.valueOf(forID),Msg,message);
            }
        }
    }

    /**
     * 发生错误时的回调函数
     * @param session
     * @param error
     */
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 群消息发送
     *
     * @param GroupID
     * @param UserID
     * @param message
     */
    public void GroupSending(Integer GroupID, Integer UserID, String message, String SMS) {
        //获取群成员
        List<UserGroupstouserDTO> userGroupstouserDTOS = getGroupsService().listGroupMember(GroupID);
        //循环发送消息
        //群信息录入数据库
        //群聊信息录入数据库
        UserGroupsmsgcontent Msgcontent = new UserGroupsmsgcontent();
        Msgcontent.setGmFromid(UserID);
        Msgcontent.setGmContent(message);
        Msgcontent.setGmCreatetime(new Date());
        getUserGroupsmsgcontentDao().insert(Msgcontent);
        UserGroupsmsgtouser Groupsmsgtouser = new UserGroupsmsgtouser();
        Groupsmsgtouser.setGmUserid(GroupID);
        //返回自增主键值，可能没有返回
        Groupsmsgtouser.setGmGroupmessageid(Msgcontent.getGmId());
        getUserGroupsmsgtouserDao().insert(Groupsmsgtouser);
        //循环发送给在线的群成员用户
        for(UserGroupstouserDTO userGroupstouserDTO : userGroupstouserDTOS) {
            try {
                //录入信息再发送，防止发送失败而丢失信息
                WebSocketServiceImpl webSocketService = webSocketSet.get(userGroupstouserDTO.getUgtUserid().toString());
                if(webSocketService != null) {
                    webSocketService.session.getBasicRemote().sendText(SMS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 私聊信息发送
     *
     * @param UserID
     * @param FriendID
     * @param message
     */
    public void AppointSending(Integer UserID, Integer FriendID, String message, String SMS) {
        try {
            //私聊信息录入数据库
            UserMsgusertouser Msgusertouser = new UserMsgusertouser();
            Msgusertouser.setMFromuserid(FriendID);
            Msgusertouser.setMTouserid(UserID);
            Msgusertouser.setMMsgcontent(message);
            Msgusertouser.setMState(Integer.getInteger("0"));
            Msgusertouser.setMCreatetime(new Date());
            getUserMsgusertouserDao().insert(Msgusertouser);
            //录入信息再发送，防止发送失败而丢失信息
            WebSocketServiceImpl webSocketService = webSocketSet.get(UserID.toString());
            if(webSocketService != null) {
                webSocketService.session.getBasicRemote().sendText(SMS);
            }
            webSocketService = webSocketSet.get(FriendID.toString());
            if(webSocketService != null) {
                webSocketService.session.getBasicRemote().sendText(SMS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 互动信息发送
     *
     * @param UserID
     * @param ForID
     * @param message
     */
    public void InteractionInfo(Integer UserID, Integer ForID, String message, String SMS) {
        try {
            //互动信息录入数据库
            Interaction interaction = new Interaction();
            interaction.setICreatetime(new Date());
            interaction.setIUserid(UserID);
            interaction.setIPattern(message);
            getInteractionDao().insert(interaction);
            //录入信息再发送，防止发送失败而丢失信息
            WebSocketServiceImpl webSocketService = webSocketSet.get(ForID.toString());
            if(webSocketService != null) {
                webSocketService.session.getBasicRemote().sendText(SMS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 判断用户是否在线
     *
     * @param Fid
     * @return
     */
    public Boolean isOnLine(Integer Fid) {
        if(webSocketSet.containsKey(Fid)) {
            return true;
        }
        return false;
    }

    /**
     * 获取连接
     *
     * @param Fid
     * @return
     */
    public Session getSession(Integer Fid) {
        return webSocketSet.get(Fid).session;
    }

    /**
     * 接收到第一个申请请求就开做心跳检测
     */
    private synchronized void startHeart(){
        ExamineHeartThread examineHeart =new ExamineHeartThread();
        Thread examineThread=new Thread(examineHeart);

        KeepHeartThread keepHeart=new KeepHeartThread();
        Thread keepThread=new Thread(keepHeart);


        keepThread.start();
        examineThread.start();

    }

    /**
     * server发送心跳包 10秒一次
     */
    private class KeepHeartThread implements Runnable {

        @Override
        public void run() {
            JSONObject heartJson=new JSONObject();
            heartJson.putOpt("type", "0");
            heartJson.putOpt("secret", "heart_keep");
            while (true) {
                try {
                    log.debug("发送心跳包当前人数为:"+webSocketSet.size());
                    sendPing(heartJson.toString());
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void sendPing(String message) throws IOException {
        if(webSocketSet.size()<=0)
            return;
        for (WebSocketServiceImpl WebSocketServiceImpl : webSocketSet.values()) {
            synchronized (WebSocketServiceImpl) {
                WebSocketServiceImpl.timeStr = getTimeInMillis();
                WebSocketServiceImpl.isHeart = false;
                try {
                    WebSocketServiceImpl.session.getBasicRemote().sendText(message);
                } catch (IllegalStateException e) {
                    log.error("连接断开："+e);
                }
            }
        }
    }

    /**
     * 检测是否收到client心跳 每秒一次
     */
    private class ExamineHeartThread implements Runnable{
        @Override
        public void run() {
            while (true) {
                try {
                    long timeMs=System.currentTimeMillis();
                    for (WebSocketServiceImpl entity : webSocketSet.values()) {
                        log.debug("timeMs="+timeMs);
//                        log.info("entity.timeStr="+entity.timeStr);
                        log.debug("timeMs>entity.timeStr="+(timeMs>entity.timeStr));
                        if(!entity.isHeart && entity.timeStr!=0 && timeMs>entity.timeStr){
                            removeUser(entity.name, new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "没有收到心跳"));
                        }
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除没有心跳的客户端
     * @param userId
     * @param reason
     */
    private void removeUser(String userId, CloseReason reason) {
        log.debug(userId+":退出了链接");
        WebSocketServiceImpl entity=webSocketSet.get(userId);
        if(null!=entity){
            try {
                if(entity.session.isOpen()){
                    entity.session.close(reason);
                }
                webSocketSet.remove(userId);
            } catch (IOException e) {
                log.info(e.toString());
                e.printStackTrace();
            }
        }
        log.debug("当前人数:"+webSocketSet.size());
    }

    /**
     * 获取时间戳
     * @return
     */
    private static long getTimeInMillis(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND,c.get(Calendar.SECOND)+8);
        return c.getTimeInMillis();
    }
}
