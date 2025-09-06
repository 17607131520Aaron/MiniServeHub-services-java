package com.miniservehub.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略全局响应包装注解
 * 标注此注解的方法将跳过全局响应包装，直接返回原始数据
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
