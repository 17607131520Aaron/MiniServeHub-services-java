package com.miniservehub.annotation;

import java.lang.annotation.*;

/**
 * 权限控制注解
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    
    /**
     * 需要的权限码
     */
    String[] value() default {};
    
    /**
     * 权限关系：AND(需要所有权限) 或 OR(需要任一权限)
     */
    Logical logical() default Logical.AND;
    
    /**
     * 权限逻辑枚举
     */
    enum Logical {
        AND, OR
    }
}
