package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.LiveSystem.entity.UserMsgusertouser;

import java.util.List;

public interface UserMsgusertouserDao {
    int deleteByPrimaryKey(Integer mId);

    int insert(UserMsgusertouser record);

    int insertSelective(UserMsgusertouser record);

    UserMsgusertouser selectByPrimaryKey(Integer mId);

    int updateByPrimaryKeySelective(UserMsgusertouser record);

    int updateByPrimaryKey(UserMsgusertouser record);

    /**
     * 获取两人之间的聊天信息,每次获取15条
     * @param fid
     * @param uid
     * @param lastMID
     * @return
     */
    List<UserMsgusertouser> findMsgByFidUid(@Param("fid") Integer fid,
                                            @Param("uid") Integer uid,
                                            @Param("lastMID") Integer lastMID);

    /**
     * 获取最后的聊天信息
     * @param friendID
     * @param userID
     * @return
     */
    String getLastMsg(@Param("friendID") Integer friendID, @Param("userID") Integer userID);

}