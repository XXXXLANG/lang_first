package org.example.LiveSystem.dto;

import lombok.Data;

/**
 * 接口返回结果类型
 * @param <T>
 */
@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;
}
