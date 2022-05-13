package org.example.LiveSystem.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("好友申请处理数据")
@Data
public class RequestProcessionParm {

    @NotNull(message = "申请表id不能为空")
    private Integer FR_ID;

    @NotNull(message = "申请表处理结果不能为空")
    private Integer state;

    //点击接受可以添加备注
    private String Alias;
}
