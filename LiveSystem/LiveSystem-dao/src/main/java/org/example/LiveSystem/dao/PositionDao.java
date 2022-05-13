package org.example.LiveSystem.dao;

import org.example.LiveSystem.entity.Position;

public interface PositionDao {
    int deleteByPrimaryKey(Integer spId);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer spId);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}