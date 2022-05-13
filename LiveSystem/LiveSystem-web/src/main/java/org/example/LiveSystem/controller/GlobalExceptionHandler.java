package org.example.LiveSystem.controller;

import org.example.LiveSystem.common.exception.BusinessException;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.constants.ResultEnum;
import org.example.LiveSystem.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 日志组件
     */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    //@ResponseBody
    public Result<Void> handleException(Exception e){
        log.error("系统异常",e);
        return ResultUtil.fail();
    }

    // 使用对象中的注解做参数校验时的异常处理
    @ExceptionHandler(BindException.class)
    public Result<Void> handleException(BindException e){
        log.warn("数据绑定异常",e);
        List<ObjectError> allErrors = e.getAllErrors();
        StringBuilder builder = new StringBuilder();
        for (ObjectError error : allErrors) {
            builder.append(error.getDefaultMessage()).append(";");
        }
        return ResultUtil.fail(ResultEnum.FAIL_PARAM.getCode(),builder.toString());
    }

    // 对于单个参数做参数校验时的异常处理
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleException(ConstraintViolationException e){
        log.warn("数据绑定异常",e);

        Set<ConstraintViolation<?>> constraintViolations =
                e.getConstraintViolations();
        StringJoiner joiner = new StringJoiner(";");
        for (ConstraintViolation<?> violation : constraintViolations) {
            joiner.add(violation.getMessage());
        }
        return ResultUtil.fail(ResultEnum.FAIL_PARAM.getCode(),joiner.toString());
    }

    // 使用springmvc的注解（@RequestParam）时出现的异常的处理
    @ExceptionHandler(ServletRequestBindingException.class)
    public Result<Void> handleException(ServletRequestBindingException e){
        log.warn("数据绑定异常",e);
        return ResultUtil.fail(ResultEnum.FAIL_PARAM.getCode(),e.getMessage());
    }

    /**
     * 统一处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleException(BusinessException e){
        log.error("系统异常",e);
        return ResultUtil.fail(e.getCode(),e.getMessage());
    }
}
