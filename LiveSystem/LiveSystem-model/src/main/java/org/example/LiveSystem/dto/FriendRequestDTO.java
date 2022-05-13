package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * friend_request
 * @author 
 */
@Data
public class FriendRequestDTO implements Serializable {
    private Integer frId;

    /**
     * 对象用户
     */
    private Integer frUser;

    /**
     * 对象班群
     */
    private Integer frGroup;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 申请人
     */
    private Integer frApplicant;

    /**
     * 申请人昵称
     */
    private String friendName;

    /**
     * 申请简介
     */
    private String frIntro;

    /**
     * 好友昵称
     */
    private String frAliasfriend;

    /**
     * 发送时间
     */
    private Date frCreatetime;

    /**
     * 是否接受（1：接受，0：未读，2：拒绝）
     */
    private Integer frState;

}