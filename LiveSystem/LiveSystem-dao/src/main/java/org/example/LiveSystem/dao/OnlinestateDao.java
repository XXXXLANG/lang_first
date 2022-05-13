package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Select;
import org.example.LiveSystem.entity.Onlinestate;

public interface OnlinestateDao {
    int deleteByPrimaryKey(Integer oId);

    int insert(Onlinestate record);

    int insertSelective(Onlinestate record);

    Onlinestate selectByPrimaryKey(Integer oId);

    int updateByPrimaryKeySelective(Onlinestate record);

    int updateByPrimaryKey(Onlinestate record);

    @Select("select * from onlinestate where O_UserID = #{gid}")
    Onlinestate findByGid(Integer gid);

    /**
     * 通过群ID指定数据修改状态
     *
     * @param record
     * @return
     */
    int updateState(Onlinestate record);
}