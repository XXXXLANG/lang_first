package org.example.LiveSystem.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 短信验证码登录的参数对象
 *
 */
@ApiModel("短信验证码登录数据")
@Data
public class SmsLoginParam {
    @ApiModelProperty(value = "手机号",required = true, notes="手机号必须是以1开头的11位号码")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "1\\d{10}",message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "验证码",required = true,notes="6位数字验证码")
    @NotNull(message = "验证码不能为空")
    @Size(min = 6,max = 6,message = "非法验证码")
    private String sms;
}
