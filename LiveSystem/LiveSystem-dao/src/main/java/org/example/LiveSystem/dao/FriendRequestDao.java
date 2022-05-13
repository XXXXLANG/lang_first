package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.LiveSystem.entity.FriendRequest;

import java.util.List;

public interface FriendRequestDao {
    int deleteByPrimaryKey(Integer frId);

    int insert(FriendRequest record);

    int insertSelective(FriendRequest record);

    FriendRequest selectByPrimaryKey(Integer frId);

    int updateByPrimaryKeySelective(FriendRequest record);

    int updateByPrimaryKey(FriendRequest record);

    /**
     *修改申请结果
     *
     * @param FR_ID
     * @param state
     */
    void updateStateByFRID(@Param("FrId") Integer FR_ID, @Param("state") Integer state);

    /**
     *  获取没处理的好友申请表
     *
     * @param uid
     * @return
     */
    List<FriendRequest> findRequestByuid(Integer uid);

    /**
     * 避免重复创建
     * @param uid
     * @param fid
     */
    FriendRequest findByUidFid(@Param("isFriend") Integer isFriend, @Param("uid") Integer uid, @Param("fid") Integer fid);
}