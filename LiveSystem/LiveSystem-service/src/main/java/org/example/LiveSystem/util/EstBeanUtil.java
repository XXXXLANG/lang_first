package org.example.LiveSystem.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换工具类
 *
 * @author jun
 * @date 2021/11/9
 */
public class EstBeanUtil {

    public static <S, T> List<T> convertTo(List<S> sources, Class<T> targetClass) {
        List<T> dtos = new ArrayList<>();
        for (S m : sources) {
            dtos.add(convertTo(m,targetClass));
        }
        return dtos;
    }

    /**
     * 把 S 类型的对象转换 T 类型的对象
     * @param source 要转换的对象
     * @param targetClass 目标类型的字节码对象
     * @param <S> 转换的对象的类型
     * @param <T> 目标对象类型
     * @return
     */
    public static <S,T> T convertTo(S source,Class<T> targetClass){
        if(ObjectUtils.isEmpty(source)){
            return null;
        }
        try {
            T o = targetClass.newInstance();
            BeanUtils.copyProperties(source, o);
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
