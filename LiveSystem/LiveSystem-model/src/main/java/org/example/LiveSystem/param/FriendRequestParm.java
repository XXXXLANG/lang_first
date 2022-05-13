package org.example.LiveSystem.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("好友申请数据")
@Data
public class FriendRequestParm {

    @NotNull(message = "判断字段不能为空")
    // 1: 是好友，0：非好友
    private Integer isAddFriend;

    @Min(value = 1,message = "好友ID出错")
    private Integer Fid;

    @Min(value = 1,message = "群聊ID出错")
    private Integer Gid;

    private String Alias;

    private String Intro;
}
