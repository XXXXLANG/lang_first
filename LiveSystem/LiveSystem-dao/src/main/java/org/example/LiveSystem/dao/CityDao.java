package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.City;

public interface CityDao {
    int deleteByPrimaryKey(Integer cId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}