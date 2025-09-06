package com.miniservehub.aspect;

import com.miniservehub.annotation.RequirePermission;
import com.miniservehub.annotation.RequireRole;
import com.miniservehub.entity.User;
import com.miniservehub.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限控制切面
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Aspect
@Component
public class PermissionAspect {

    private static final Logger logger = LoggerFactory.getLogger(PermissionAspect.class);

    /**
     * 权限检查切面
     */
    @Before("@annotation(requirePermission)")
    public void checkPermission(JoinPoint joinPoint, RequirePermission requirePermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("用户未登录");
        }

        String[] requiredPermissions = requirePermission.value();
        if (requiredPermissions.length == 0) {
            return;
        }

        // 获取用户权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> userPermissions = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // 检查权限
        boolean hasPermission = false;
        if (requirePermission.logical() == RequirePermission.Logical.AND) {
            // 需要所有权限
            hasPermission = userPermissions.containsAll(Set.of(requiredPermissions));
        } else {
            // 需要任一权限
            hasPermission = Set.of(requiredPermissions).stream()
                    .anyMatch(userPermissions::contains);
        }

        if (!hasPermission) {
            String username = authentication.getName();
            logger.warn("用户 {} 访问 {} 权限不足，需要权限: {}, 用户权限: {}", 
                    username, joinPoint.getSignature().getName(), 
                    String.join(",", requiredPermissions), 
                    String.join(",", userPermissions));
            throw new BusinessException("权限不足");
        }

        logger.debug("用户 {} 权限检查通过: {}", authentication.getName(), String.join(",", requiredPermissions));
    }

    /**
     * 角色检查切面
     */
    @Before("@annotation(requireRole)")
    public void checkRole(JoinPoint joinPoint, RequireRole requireRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("用户未登录");
        }

        String[] requiredRoles = requireRole.value();
        if (requiredRoles.length == 0) {
            return;
        }

        // 获取用户角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> userRoles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.substring(5)) // 移除 "ROLE_" 前缀
                .collect(Collectors.toSet());

        // 检查角色
        boolean hasRole = false;
        if (requireRole.logical() == RequireRole.Logical.AND) {
            // 需要所有角色
            hasRole = userRoles.containsAll(Set.of(requiredRoles));
        } else {
            // 需要任一角色
            hasRole = Set.of(requiredRoles).stream()
                    .anyMatch(userRoles::contains);
        }

        if (!hasRole) {
            String username = authentication.getName();
            logger.warn("用户 {} 访问 {} 角色不足，需要角色: {}, 用户角色: {}", 
                    username, joinPoint.getSignature().getName(), 
                    String.join(",", requiredRoles), 
                    String.join(",", userRoles));
            throw new BusinessException("角色权限不足");
        }

        logger.debug("用户 {} 角色检查通过: {}", authentication.getName(), String.join(",", requiredRoles));
    }
}
