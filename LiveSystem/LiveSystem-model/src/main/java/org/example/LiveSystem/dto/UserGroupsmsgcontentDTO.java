package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user_groupsmsgcontent
 * @author 
 */
@Data
public class UserGroupsmsgcontentDTO implements Serializable {
    private Integer gmId;

    /**
     * 发送者ID
     */
    private Integer gmFromid;

    /**
     * 发送者群内昵称
     */
    private String groupNick;

    /**
     * 消息内容
     */
    private String gmContent;

    /**
     * 发送时间
     */
    private Date gmCreatetime;
}