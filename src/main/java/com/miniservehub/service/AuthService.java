package com.miniservehub.service;

import com.miniservehub.dto.*;
import com.miniservehub.entity.Role;
import com.miniservehub.entity.User;
import com.miniservehub.exception.BusinessException;
import com.miniservehub.repository.UserRepository;
import com.miniservehub.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证服务
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Service
@Transactional
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 用户登录
     */
    public AuthResponse login(LoginRequest loginRequest) {
        logger.info("用户登录请求: {}", loginRequest.getUsername());

        try {
            // 验证用户凭据
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = (User) userDetails;

            // 更新最后登录时间
            user.setLastLoginTime(LocalDateTime.now());
            userRepository.save(user);

            // 生成JWT令牌
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // 构建用户信息
            AuthResponse.UserInfo userInfo = buildUserInfo(user);

            logger.info("用户 {} 登录成功", loginRequest.getUsername());
            return new AuthResponse(accessToken, refreshToken, jwtUtil.getTokenRemainingTime(accessToken), userInfo);

        } catch (Exception e) {
            logger.error("用户 {} 登录失败: {}", loginRequest.getUsername(), e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    /**
     * 用户注册
     */
    public AuthResponse register(RegisterRequest registerRequest) {
        logger.info("用户注册请求: {}", registerRequest.getUsername());

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (registerRequest.getEmail() != null && userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException("邮箱已被使用");
        }

        // 检查手机号是否已存在
        if (registerRequest.getPhone() != null && userRepository.existsByPhone(registerRequest.getPhone())) {
            throw new BusinessException("手机号已被使用");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRealName(registerRequest.getRealName());
        user.setPhone(registerRequest.getPhone());
        user.setStatus(1); // 启用状态
        user.setUserType(2); // 普通用户
        user.setGender(0); // 未知性别

        // 设置默认角色（这里可以根据业务需求设置）
        user.setRoles(new HashSet<>());

        user = userRepository.save(user);

        // 生成JWT令牌
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // 构建用户信息
        AuthResponse.UserInfo userInfo = buildUserInfo(user);

        logger.info("用户 {} 注册成功", registerRequest.getUsername());
        return new AuthResponse(accessToken, refreshToken, jwtUtil.getTokenRemainingTime(accessToken), userInfo);
    }

    /**
     * 刷新令牌
     */
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        try {
            // 验证刷新令牌
            if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
                throw new BusinessException("无效的刷新令牌");
            }

            // 获取用户名
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 生成新的访问令牌
            String newAccessToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            // 构建用户信息
            User user = (User) userDetails;
            AuthResponse.UserInfo userInfo = buildUserInfo(user);

            logger.info("用户 {} 刷新令牌成功", username);
            return new AuthResponse(newAccessToken, newRefreshToken, jwtUtil.getTokenRemainingTime(newAccessToken), userInfo);

        } catch (Exception e) {
            logger.error("刷新令牌失败: {}", e.getMessage());
            throw new BusinessException("刷新令牌失败");
        }
    }

    /**
     * 用户登出
     */
    public void logout(String username) {
        logger.info("用户 {} 登出", username);
        // 这里可以实现令牌黑名单机制
        // 目前JWT是无状态的，登出主要由前端处理（删除本地存储的令牌）
    }

    /**
     * 构建用户信息
     */
    private AuthResponse.UserInfo buildUserInfo(User user) {
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setRealName(user.getRealName());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setStatus(user.getStatus());
        userInfo.setUserType(user.getUserType());
        userInfo.setLastLoginTime(user.getLastLoginTime());

        // 设置角色信息
        Set<String> roles = user.getRoles().stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toSet());
        userInfo.setRoles(roles);

        // 设置权限信息
        Set<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> permission.getPermissionCode())
                .collect(Collectors.toSet());
        userInfo.setPermissions(permissions);

        return userInfo;
    }
}
