package org.example.LiveSystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GroupsDTO {
    private Integer ugId;

    /**
     * 群名称
     */
    private String ugName;

    /**
     * 班级账号
     */
    private String ugGroupNum;

    /**
     * 创建时间
     */
    private Date ugCreatetime;

    /**
     * 群主
     */
    private Integer ugAdminid;
    private String adminName;

    /**
     * 群图标
     */
    private String ugIcon;

    /**
     * 公告
     */
    private String ugNotice;

    /**
     * 简介
     */
    private String ugIntro;

    /**
     * 进群验证（1：是，0：否）
     */
    private Integer ugVerification;
}
