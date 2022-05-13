package org.example.LiveSystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class FriendsDTO {

    private Integer fId;

    /**
     * 朋友的ID
     */
    private Integer fFriendid;

    /**
     * 好友备注
     */
    private String fAliasuser;

    /**
     * 好友个人信息
     */
    private UsersInfoDTO friendInfo;
}
