package org.example.LiveSystem.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * user_groupstouser
 * @author 
 */
@Data
public class UserGroupstouserDTO implements Serializable {
    private Integer ugtId;

    /**
     * 用户ID
     */
    private Integer ugtUserid;

    /**
     * 群ID
     */
    private Integer ugtGroupid;

    /**
     * 群内用户昵称
     */
    private String ugtGroupnick;

    /**
     * 用户是否消息免打扰（1：是；0：否）
     */
    private Integer ugtReminding;
}