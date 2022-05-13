package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.School;

public interface SchoolDao {
    int deleteByPrimaryKey(Integer sId);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
}