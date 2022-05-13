package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.UserGroupsmsgtouser;

public interface UserGroupsmsgtouserDao {
    int deleteByPrimaryKey(Integer gmId);

    int insert(UserGroupsmsgtouser record);

    int insertSelective(UserGroupsmsgtouser record);

    UserGroupsmsgtouser selectByPrimaryKey(Integer gmId);

    int updateByPrimaryKeySelective(UserGroupsmsgtouser record);

    int updateByPrimaryKey(UserGroupsmsgtouser record);
}