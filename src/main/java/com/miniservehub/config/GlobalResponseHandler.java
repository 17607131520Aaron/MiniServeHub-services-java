package com.miniservehub.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniservehub.annotation.IgnoreResponseAdvice;
import com.miniservehub.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应处理器
 * 自动将Controller返回的数据包装成统一的Result格式
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@RestControllerAdvice(basePackages = "com.miniservehub.controller")
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseHandler.class);
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查方法或类上是否有@IgnoreResponseAdvice注解
        if (returnType.hasMethodAnnotation(IgnoreResponseAdvice.class) || 
            returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        
        // 如果返回类型已经是Result，则不需要包装
        return !returnType.getParameterType().equals(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        
        // 排除Swagger相关的接口
        String path = request.getURI().getPath();
        if (path.contains("/swagger") || path.contains("/api-docs") || path.contains("/webjars")) {
            return body;
        }
        
        // 排除错误页面
        if (path.contains("/error")) {
            return body;
        }
        
        // 如果已经是Result类型，直接返回
        if (body instanceof Result) {
            return body;
        }
        
        // 处理String类型的特殊情况
        if (returnType.getParameterType().equals(String.class)) {
            try {
                // 将Result转换为JSON字符串
                Result<Object> result = Result.success(body);
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                logger.error("JSON序列化失败", e);
                return Result.success(body);
            }
        }
        
        // 包装成功响应
        return Result.success(body);
    }
}
