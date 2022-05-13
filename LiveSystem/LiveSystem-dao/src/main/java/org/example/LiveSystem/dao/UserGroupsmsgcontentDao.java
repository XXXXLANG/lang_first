package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.LiveSystem.entity.UserGroupsmsgcontent;

import java.util.List;

public interface UserGroupsmsgcontentDao {
    int deleteByPrimaryKey(Integer gmId);

    int insert(UserGroupsmsgcontent record);

    int insertSelective(UserGroupsmsgcontent record);

    UserGroupsmsgcontent selectByPrimaryKey(Integer gmId);

    int updateByPrimaryKeySelective(UserGroupsmsgcontent record);

    int updateByPrimaryKey(UserGroupsmsgcontent record);

    /**
     * 获取群内聊天信息,每次获取15条
     * @param gid
     * @param lastGMID
     * @return
     */
    List<UserGroupsmsgcontent> findMsgByGid(@Param("gid") Integer gid,
                                            @Param("lastGMID") Integer lastGMID);

    /**
     * 获取群最后的信息内容
     * @param friendID
     * @return
     */
    String getLastMsg(Integer friendID);
}