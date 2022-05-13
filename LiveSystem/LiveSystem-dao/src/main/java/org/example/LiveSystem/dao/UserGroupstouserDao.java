package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.entity.UserGroupstouser;

import java.util.List;

public interface UserGroupstouserDao {
    int deleteByPrimaryKey(Integer ugtId);

    int insert(UserGroupstouser record);

    int insertSelective(UserGroupstouser record);

    UserGroupstouser selectByPrimaryKey(Integer ugtId);

    int updateByPrimaryKeySelective(UserGroupstouser record);

    int updateByPrimaryKey(UserGroupstouser record);

    /**
     * 根据用户id获取所在群列表
     * @param uid
     * @return
     */
    List<UserGroupstouser> listGroupsBy(@Param("uid") Integer uid);

    /**
     * 根据群id获取群成员
     * @param gid
     * @return
     */
    List<UserGroupstouser> listUsersByGid(@Param("gid") Integer gid);

    /**
     * 获取群内昵称
     * @param FromID
     * @return
     */
    String getUserName(@Param("uid") Integer FromID, @Param("gmId") Integer gmId);

    /**
     * 删除群成员
     *
     * @param uId
     * @param gid
     * @return
     */
    int deleteGroup(@Param("uId") Integer uId, @Param("gid") Integer gid);

    /**
     * 根据用户进群顺序，寻找下一个群主
     *
     * @param gid
     * @return
     */
    Integer selectNextAdmin(@Param("gid") Integer gid, @Param("uid") Integer uid);

    /**
     * 通过群id，用户id查询信息
     *
     * @param uid
     * @param gid
     * @return
     */
    @Select("select * from user_groupstouser where UGT_UserID = #{uid} and UGT_GroupID = #{gid}")
    UserGroupstouser findByUidGid(Integer uid, Integer gid);
}