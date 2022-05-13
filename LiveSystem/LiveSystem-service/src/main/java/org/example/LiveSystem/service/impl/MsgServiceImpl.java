package org.example.LiveSystem.service.impl;

import org.example.LiveSystem.dao.ChatHistoryDao;
import org.example.LiveSystem.dao.UserGroupsmsgcontentDao;
import org.example.LiveSystem.dao.UserMsgusertouserDao;
import org.example.LiveSystem.dto.ChatHistoryDTO;
import org.example.LiveSystem.dto.FriendsDTO;
import org.example.LiveSystem.dto.UserGroupsmsgcontentDTO;
import org.example.LiveSystem.dto.UserMsgusertouserDTO;
import org.example.LiveSystem.entity.ChatHistory;
import org.example.LiveSystem.entity.UserGroupsmsgcontent;
import org.example.LiveSystem.entity.UserMsgusertouser;
import org.example.LiveSystem.service.MsgService;
import org.example.LiveSystem.util.EstBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private UserMsgusertouserDao userMsgusertouserDao;

    @Autowired
    private UserGroupsmsgcontentDao userGroupsmsgcontentDao;

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    @Override
    public List<UserMsgusertouserDTO> getFriendMsg(Integer FID, Integer UID, Integer lastMID) {
        List<UserMsgusertouser> userMsgusertousers = userMsgusertouserDao.findMsgByFidUid(FID, UID, lastMID);
        return EstBeanUtil.convertTo(userMsgusertousers, UserMsgusertouserDTO.class);
    }

    @Override
    public List<UserGroupsmsgcontentDTO> getGroupMsg(Integer GID, Integer lastGMID) {
        List<UserGroupsmsgcontent> userGroupsmsgcontents = userGroupsmsgcontentDao.findMsgByGid(GID, lastGMID);
        return EstBeanUtil.convertTo(userGroupsmsgcontents, UserGroupsmsgcontentDTO.class);
    }

    @Override
    public List<ChatHistoryDTO> getChatList(Integer UID) {
        List<ChatHistory> chatHistories = chatHistoryDao.findChatListBy(UID);
        return EstBeanUtil.convertTo(chatHistories, ChatHistoryDTO.class);
    }

    @Override
    public int deleteChat(Integer chId) {
        return chatHistoryDao.deleteByPrimaryKey(chId);
    }

    @Override
    public int addChat(Integer uId, Integer fid, Boolean isGroup) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setChTop(0);
        chatHistory.setChCreatetime(new Date());
        chatHistory.setChUserid(uId);
        if(isGroup) {
            chatHistory.setChGroupid(fid);
        } else {
            chatHistory.setChFriendid(fid);
        }
        return chatHistoryDao.insert(chatHistory);
    }

    @Override
    public int delChatByUFID(Integer uid, Integer fid) {
        return chatHistoryDao.delChatByUFID(uid, fid);
    }

    @Override
    public int delChatByUGID(Integer uid, Integer gid) {
        return chatHistoryDao.delChatByUGID(uid, gid);
    }
}
