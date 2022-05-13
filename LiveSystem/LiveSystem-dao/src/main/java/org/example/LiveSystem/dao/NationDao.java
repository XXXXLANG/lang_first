package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.Nation;

public interface NationDao {
    int deleteByPrimaryKey(Integer nId);

    int insert(Nation record);

    int insertSelective(Nation record);

    Nation selectByPrimaryKey(Integer nId);

    int updateByPrimaryKeySelective(Nation record);

    int updateByPrimaryKey(Nation record);
}