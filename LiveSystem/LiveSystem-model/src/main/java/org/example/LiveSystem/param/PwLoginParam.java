package org.example.LiveSystem.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 账号密码验证码登录的参数对象
 *
 */
@ApiModel("账号密码验证码登录数据")
@Data
public class PwLoginParam {
    @ApiModelProperty(value = "账号",required = true, notes="手机号必须是以1开头的11位号码")
    @NotNull(message = "账号不能为空")
    //11到18位数字
    @Pattern(regexp = "^\\d{11,18}$",message = "账号格式不正确")
    private String LoginID;

    @ApiModelProperty(value = "密码",required = true,notes="6位数字验证码")
    @NotNull(message = "密码不能为空")
    //以字母开头，长度在11~18之间，只能包含字符、数字和下划线。
    @Pattern(regexp = "^[a-zA-Z]\\w{10,17}$",message = "密码格式不正确")
    private String password;
}
