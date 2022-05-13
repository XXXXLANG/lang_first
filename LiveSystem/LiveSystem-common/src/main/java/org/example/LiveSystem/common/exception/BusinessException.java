package org.example.LiveSystem.common.exception;

import org.example.LiveSystem.constants.ResultEnum;

/**
 * 自定义的业务异常，要指定错误消息和错误码
 */
public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException(String code,String msg){
        super(msg);
        this.code = code;
    }

    public BusinessException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public String getCode() {
        return code;
    }
}
