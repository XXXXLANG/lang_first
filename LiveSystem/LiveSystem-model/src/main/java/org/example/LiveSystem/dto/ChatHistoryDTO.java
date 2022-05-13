package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_history
 * @author 
 */
@Data
public class ChatHistoryDTO implements Serializable {
    private Integer chId;

    /**
     * 群ID
     */
    private Integer chGroupid;

    /**
     * 好友ID
     */
    private Integer chFriendid;

    /**
     * 用户ID
     */
    private Integer chUserid;

    /**
     * 聊天时间
     */
    private Date chCreatetime;

    /**
     * 置顶（1：是，0：否）
     */
    private Integer chTop;

    /**
     * 好友或群名称
     */
    private String FriendName;

    /**
     * 最后的聊天内容
     */
    private String Msg;
}