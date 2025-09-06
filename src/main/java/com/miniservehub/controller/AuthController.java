package com.miniservehub.controller;

import com.miniservehub.dto.*;
import com.miniservehub.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Tag(name = "用户认证", description = "用户登录、注册、令牌刷新等认证相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Operation(summary = "用户登录", description = "用户通过用户名和密码登录系统")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("用户登录请求: {}", loginRequest.getUsername());
        return authService.login(loginRequest);
    }

    @Operation(summary = "用户注册", description = "新用户注册账号")
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        logger.info("用户注册请求: {}", registerRequest.getUsername());
        return authService.register(registerRequest);
    }

    @Operation(summary = "刷新访问令牌", description = "使用刷新令牌获取新的访问令牌")
    @PostMapping("/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        logger.info("刷新令牌请求");
        return authService.refreshToken(refreshTokenRequest);
    }

    @Operation(summary = "用户登出", description = "用户退出登录")
    @PostMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            authService.logout(username);
            logger.info("用户 {} 登出成功", username);
            return "登出成功";
        }
        return "用户未登录";
    }

    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/me")
    public AuthResponse.UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 这里可以通过用户服务获取完整的用户信息
            // 为了简化，这里返回基本信息
            String username = authentication.getName();
            logger.info("获取当前用户信息: {}", username);
            
            // 实际项目中应该通过服务层获取完整用户信息
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
            userInfo.setUsername(username);
            return userInfo;
        }
        return null;
    }
}
