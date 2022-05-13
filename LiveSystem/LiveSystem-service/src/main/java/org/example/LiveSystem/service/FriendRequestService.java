package org.example.LiveSystem.service;

import org.example.LiveSystem.dao.FriendRequestDao;
import org.example.LiveSystem.dto.FriendRequestDTO;
import org.example.LiveSystem.entity.FriendRequest;
import org.example.LiveSystem.param.FriendRequestParm;
import org.example.LiveSystem.param.RequestProcessionParm;

import java.util.List;

public interface FriendRequestService {

    /**
     * 添加好友申请
     * @param friendRequestParm
     */
    int addFriends (FriendRequestParm friendRequestParm, Integer uid);

    /**
     * 申请处理
     *
     * @param requestProcessionParm
     */
    void Processing (RequestProcessionParm requestProcessionParm);

    /**
     * 发送添加申请表（请求对方确认）
     * @param uId
     * @return
     */
    List<FriendRequestDTO> getProcess(Integer uId);
}
