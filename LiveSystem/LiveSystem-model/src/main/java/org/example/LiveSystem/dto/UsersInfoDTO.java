package org.example.LiveSystem.dto;

import lombok.Data;

import java.util.Date;


@Data
public class UsersInfoDTO {
    private Integer uId;

    /**
     * 账号
     */
    private String uLoginid;

    /**
     * 密码
     */
    private String uPassword;

    /**
     * 昵称
     */
    private String uNickname;

    /**
     * 性别（1：男，0：女）
     */
    private Integer uSex;

    /**
     * 生日
     */
    private Date uBirthday;

    /**
     * 头像
     */
    private String uHeadportrait;

    /**
     * 个性签名
     */
    private String uSignature;

    /**
     * 学号
     */
    private String uSno;

    /**
     * 电话
     */
    private String uTelephone;

    /**
     * 邮箱
     */
    private String uEmail;

    /**
     * 学校
     */
    private Integer uSchooltag;
    private String School;

    /**
     * 学校职位（也包括“学生”）
     */
    private Integer uPosition;
    private String Position;

    /**
     * 国家
     */
    private Integer uNationid;
    private String Nation;

    /**
     * 省份
     */
    private Integer uProvinceid;
    private String Province;

    /**
     * 城市
     */
    private Integer uCityid;
    private String City;

    private String token;
}
