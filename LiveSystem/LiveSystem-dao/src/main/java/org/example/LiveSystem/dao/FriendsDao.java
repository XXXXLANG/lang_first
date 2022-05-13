package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.example.LiveSystem.entity.Friends;
import org.example.LiveSystem.entity.User;

import java.util.List;

public interface FriendsDao {
    int deleteByPrimaryKey(Integer fId);

    int insert(Friends record);

    int insertSelective(Friends record);

    Friends selectByPrimaryKey(Integer fId);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);

    /**
     * 根据用户id获取好友列表
     * @param id
     * @return
     */
    List<Friends> listFriendsBy(@Param("id") Integer id);

    /**
     * 获取好友昵称
     * @param friendID
     * @param userID
     * @return
     */
    String getFriendName(@Param("friendID") Integer friendID, @Param("userID") Integer userID);

    int deleteFriend(@Param("uid") Integer uid, @Param("fid") Integer fid);
}