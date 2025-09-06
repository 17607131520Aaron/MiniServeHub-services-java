package com.miniservehub.annotation;

import java.lang.annotation.*;

/**
 * 角色控制注解
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {
    
    /**
     * 需要的角色码
     */
    String[] value() default {};
    
    /**
     * 角色关系：AND(需要所有角色) 或 OR(需要任一角色)
     */
    Logical logical() default Logical.OR;
    
    /**
     * 角色逻辑枚举
     */
    enum Logical {
        AND, OR
    }
}
