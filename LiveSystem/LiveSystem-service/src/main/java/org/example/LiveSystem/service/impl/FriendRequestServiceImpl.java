package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.dao.*;
import org.example.LiveSystem.dto.FriendRequestDTO;
import org.example.LiveSystem.entity.FriendRequest;
import org.example.LiveSystem.entity.Friends;
import org.example.LiveSystem.entity.UserGroups;
import org.example.LiveSystem.entity.UserGroupstouser;
import org.example.LiveSystem.param.FriendRequestParm;
import org.example.LiveSystem.param.RequestProcessionParm;
import org.example.LiveSystem.service.FriendRequestService;
import org.example.LiveSystem.service.WebSocketService;
import org.example.LiveSystem.util.EstBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private FriendsDao friendsDao;

    @Autowired
    private UserGroupsDao userGroupsDao;

    @Autowired
    private UserGroupstouserDao userGroupstouserDao;

    @Autowired
    private UserDao userDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加好友申请
     * @param friendRequestParm
     */
    @Override
    public int addFriends(FriendRequestParm friendRequestParm, Integer uid) {
        Integer isAddFriend = friendRequestParm.getIsAddFriend();
        Integer Fid;
        if(isAddFriend == 1) {
            Fid = friendRequestParm.getFid();
        } else {
            Fid = friendRequestParm.getGid();
        }
        if(friendRequestDao.findByUidFid(isAddFriend, uid, Fid) != null) {
            return 0;
        }
        String Alias = friendRequestParm.getAlias();
        String Intro = friendRequestParm.getIntro();
        FriendRequest friendRequest = new FriendRequest();
        //处理状态,未处理
        friendRequest.setFrState(0);
        //申请对象
        if(isAddFriend == 1) {
            friendRequest.setFrUser(Fid);
        } else {
            friendRequest.setFrGroup(Fid);
            UserGroups userGroups = userGroupsDao.selectByPrimaryKey(Fid);
            if(userGroups != null && userGroups.getUgVerification() == 0) {
                friendRequest.setFrState(1);
                //进群不需要验证,直接进入添加成员信息
                UserGroupstouser userGroupstouser = new UserGroupstouser();
                userGroupstouser.setUgtReminding(0);
                userGroupstouser.setUgtGroupid(Fid);
                userGroupstouser.setUgtUserid(uid);
                userGroupstouserDao.insert(userGroupstouser);
            } else if(userGroups != null && userGroups.getUgVerification() == 1) {
                if(userGroups != null && webSocketService.isOnLine(userGroups.getUgAdminid())) {
                    UserGroupstouser userGroupstouser = userGroupstouserDao.findByUidGid(userGroups.getUgAdminid(), Fid);
                    //用户是否开启免打扰
                    if(userGroupstouser != null && userGroupstouser.getUgtReminding() == 0) {
                        String message = "用户 " + userDao.getUserName(userGroups.getUgAdminid()) + " 向您发出进群申请";
                        try {
                            webSocketService.getSession(userGroups.getUgAdminid()).getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            logger.error("消息推送错误：" + e);
                        }
                    }
                }
            }
        }
        //申请人
        friendRequest.setFrApplicant(uid);
        //申请简介
        friendRequest.setFrIntro(Intro);
        //对象备注
        friendRequest.setFrAliasfriend(Alias);
        //发送时间
        friendRequest.setFrCreatetime(new Date());

        //判断用户是否在线,在线就发送请求
        if(friendRequestParm.getIsAddFriend() == 1 && webSocketService.isOnLine(Fid)) {
            String message = "用户 "+userDao.getUserName(Fid)+" 向您发出好友申请";
            try {
                webSocketService.getSession(Fid).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("消息推送错误："+e);
            }
        }

        return friendRequestDao.insert(friendRequest);
    }

    /**
     * 申请处理
     *
     * @param requestProcessionParm
     */
    @Override
    public void Processing(RequestProcessionParm requestProcessionParm) {
        //修改申请表状态
        Integer FR_ID = requestProcessionParm.getFR_ID();
        Integer state = requestProcessionParm.getState();
        friendRequestDao.updateStateByFRID(FR_ID, state);
        //如果是同意就在好友表插入数据(1:接受)
        if(state.equals(1)) {
            FriendRequest friendRequest = friendRequestDao.selectByPrimaryKey(FR_ID);
            if(friendRequest != null) {
                //判断是进群申请还是好友申请
                if(friendRequest.getFrGroup() != null && friendRequest.getFrGroup() > 0) {
                    UserGroupstouser userGroupstouser = new UserGroupstouser();
                    userGroupstouser.setUgtUserid(friendRequest.getFrApplicant());
                    userGroupstouser.setUgtGroupid(friendRequest.getFrGroup());
                    userGroupstouser.setUgtReminding(0);
                    userGroupstouserDao.insert(userGroupstouser);
                } else if(friendRequest.getFrUser() != null && friendRequest.getFrUser() > 0) {
                    Friends friends = new Friends();
                    String Alias = requestProcessionParm.getAlias();
                    friends.setFFriendid(friendRequest.getFrApplicant());
                    friends.setFUserid(friendRequest.getFrUser());
                    friends.setFAliasuser(Alias);
                    friends.setFAliasfriend(friendRequest.getFrAliasfriend());
                    friendsDao.insert(friends);
                }
            }
        }
    }

    /**
     * 发送添加申请表（请求对方确认）
     * @param uId
     * @return
     */
    @Override
    public List<FriendRequestDTO> getProcess(Integer uId) {
        List<FriendRequest> friendRequests = friendRequestDao.findRequestByuid(uId);
        return EstBeanUtil.convertTo(friendRequests, FriendRequestDTO.class);
    }

}
