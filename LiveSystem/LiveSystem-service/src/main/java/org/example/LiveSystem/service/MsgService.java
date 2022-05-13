package org.example.LiveSystem.service;

import org.example.LiveSystem.dto.ChatHistoryDTO;
import org.example.LiveSystem.dto.UserGroupsmsgcontentDTO;
import org.example.LiveSystem.dto.UserMsgusertouserDTO;

import java.util.List;

public interface MsgService {

    /**
     * 获取私聊信息
     *
     * @param FID
     * @param UID
     * @return
     */
    List<UserMsgusertouserDTO> getFriendMsg(Integer FID, Integer UID, Integer lastMID);

    /**
     * 获取群聊信息
     *
     * @param GID
     * @return
     */
    List<UserGroupsmsgcontentDTO> getGroupMsg(Integer GID, Integer lastGMID);

    /**
     * 获取聊天记录列表
     *
     * @param UID
     * @return
     */
    List<ChatHistoryDTO> getChatList(Integer UID);

    /**
     * 删除聊天，保留聊天内容
     *
     * @param chId
     * @return
     */
    int deleteChat(Integer chId);

    /**
     * 发起聊天
     * @param uId
     * @param fid
     * @param isGroup
     * @return
     */
    int addChat(Integer uId, Integer fid, Boolean isGroup);

    /**
     * 删除好友聊天
     *
     * @param uid
     * @param fid
     * @return
     */
    int delChatByUFID(Integer uid, Integer fid);

    /**
     * 删除群聊天
     *
     * @param uid
     * @param gid
     * @return
     */
    int delChatByUGID(Integer uid, Integer gid);
}
