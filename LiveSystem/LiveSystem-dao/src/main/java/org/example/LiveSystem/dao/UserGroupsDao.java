package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.LiveSystem.dto.GroupsDTO;
import org.example.LiveSystem.entity.UserGroups;

public interface UserGroupsDao {
    int deleteByPrimaryKey(Integer ugId);

    int insert(UserGroups record);

    int insertSelective(UserGroups record);

    UserGroups selectByPrimaryKey(Integer ugId);

    int updateByPrimaryKeySelective(UserGroups record);

    int updateByPrimaryKey(UserGroups record);

    /**
     * 获取群名称
     *
     * @param friendID
     * @return
     */
    @Select("select UG_Name from user_groups where UG_ID = #{friendID}")
    String getGroupName(Integer friendID);

    /**
     * 更改群头像
     *
     * @param filename
     * @param GroupID
     */
    @Update("update user_groups set UG_ICon = #{fileName} where UG_ID = #{GroupID}")
    void setGroupIcon(String filename, Integer GroupID);

    /**
     * 根据群账号查找群
     *
     * @param groupNum
     * @return
     */
    @Select("select * from user_groups where UG_GroupNum = #{groupNum}")
    UserGroups findByNum(String groupNum);

    /**
     * 修改群主
     *
     * @param gid
     * @param nextAdmin
     */
    void updateAdmin(@Param("gid") Integer gid, @Param("nextAdmin") Integer nextAdmin);
}