package org.example.LiveSystem.common.util;

import org.example.LiveSystem.dto.Result;

public class ResultUtil {

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode("0");
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> success(String msg,T data){
        Result<T> result = new Result<>();
        result.setCode("0");
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String msg){
        return success(msg,null);
    }

    public static <T> Result<T> fail(String code,String msg){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(){
        Result<T> result = new Result<>();
        result.setCode("1");
        result.setMsg("系统异常");
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(String msg){
        Result<T> result = new Result<>();
        result.setCode("1");
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
