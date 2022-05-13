package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.Province;

public interface ProvinceDao {
    int deleteByPrimaryKey(Integer pId);

    int insert(Province record);

    int insertSelective(Province record);

    Province selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);
}