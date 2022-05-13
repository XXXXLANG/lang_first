package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.LiveSystem.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryDao {
    int deleteByPrimaryKey(Integer chId);

    int insert(ChatHistory record);

    int insertSelective(ChatHistory record);

    ChatHistory selectByPrimaryKey(Integer chId);

    int updateByPrimaryKeySelective(ChatHistory record);

    int updateByPrimaryKey(ChatHistory record);

    /**
     * 查询用户的聊天记录列表
     *
     * @param uid
     * @return
     */
    List<ChatHistory> findChatListBy(Integer uid);

    /**
     * 通过双方ID，删除聊天
     *
     * @param uid
     * @param fid
     * @return
     */
    int delChatByUFID(@Param("uid") Integer uid, @Param("fid") Integer fid);

    /**
     * 通过双方ID，删除聊天
     *
     * @param uid
     * @param gid
     * @return
     */
    int delChatByUGID(@Param("uid") Integer uid, @Param("gid") Integer gid);
}