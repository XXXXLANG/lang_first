package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user_msgusertouser
 * @author 
 */
@Data
public class UserMsgusertouserDTO implements Serializable {
    private Integer mId;

    /**
     * 发送者
     */
    private Integer mFromuserid;

    /**
     * 接收者
     */
    private Integer mTouserid;

    /**
     * 内容
     */
    private String mMsgcontent;

    /**
     * 接收状态
     */
    private Integer mState;

    /**
     * 发送时间
     */
    private Date mCreatetime;

}