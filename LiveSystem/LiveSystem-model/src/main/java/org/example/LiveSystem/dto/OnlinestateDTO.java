package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * onlinestate
 * @author 
 */
@Data
public class OnlinestateDTO implements Serializable {
    private Integer oId;

    /**
     * 用户ID
     */
    private Integer oUserid;

    /**
     * 客户端地址（每次登录保存）
     */
    private String oClient;

    /**
     * 在线状态（1：在线，0：离线）
     */
    private Integer oState;

    /**
     * 最近登录时间
     */
    private Date oDatatime;
}