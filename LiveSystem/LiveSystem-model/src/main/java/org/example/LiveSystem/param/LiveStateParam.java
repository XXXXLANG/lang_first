package org.example.LiveSystem.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("直播数据")
@Data
public class LiveStateParam {

    @ApiModelProperty(value = "直播地址", notes="记录地址后缀")
    private String client;

    @ApiModelProperty(value = "直播状态",required = true, notes="状态的值只能是1：在线，0：离线")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @ApiModelProperty(value = "群ID",required = true, notes="群ID指定对应数据")
    @NotNull(message = "群ID不能为空")
    private Integer gid;
}
