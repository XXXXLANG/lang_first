package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.Interaction;

public interface InteractionDao {
    int deleteByPrimaryKey(Integer iId);

    int insert(Interaction record);

    int insertSelective(Interaction record);

    Interaction selectByPrimaryKey(Integer iId);

    int updateByPrimaryKeySelective(Interaction record);

    int updateByPrimaryKey(Interaction record);
}