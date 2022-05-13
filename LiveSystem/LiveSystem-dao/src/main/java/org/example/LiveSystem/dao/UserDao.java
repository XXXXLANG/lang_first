package org.example.LiveSystem.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.LiveSystem.entity.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select U_ID, U_LoginID, U_NickName, U_SignaTure, U_Sex, U_Birthday, U_Sno, U_Telephone," +
            " U_Email, U_HeadPortrait, U_SchoolTag, U_position, U_NationID, U_ProvinceID, U_CityID from user where U_Telephone=#{phone}")
    User findByTel(String phone);

    @Select("select * from user where U_LoginID=#{loginID} and U_PassWord=#{password}")
    User findByUidPwd(String loginID, String password);

    @Select("select U_NickName from user where U_ID = #{uid}")
    String getUserName(Integer uid);

    @Update("update user set U_HeadPortrait = #{fileName} where U_ID = #{uid}")
    void setUserIcon(String fileName, Integer uid);
}