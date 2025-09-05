package com.miniservehub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.miniservehub.common.result.ResultCode;
import com.miniservehub.dto.UserDTO;
import com.miniservehub.entity.User;
import com.miniservehub.exception.BusinessException;
import com.miniservehub.repository.UserRepository;
import com.miniservehub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("创建用户: {}", userDTO.getUsername());
        
        // 参数验证
        validateUserForCreate(userDTO);
        
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_ALREADY_EXISTS);
        }
        
        // 检查邮箱是否已存在
        if (StrUtil.isNotBlank(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS);
        }
        
        // 检查手机号是否已存在
        if (StrUtil.isNotBlank(userDTO.getPhone()) && userRepository.existsByPhone(userDTO.getPhone())) {
            throw new BusinessException(ResultCode.PHONE_ALREADY_EXISTS);
        }
        
        // 创建用户实体
        User user = new User();
        BeanUtil.copyProperties(userDTO, user, "id", "password");
        
        // 设置默认值
        user.setStatus(1); // 默认启用
        user.setUserType(2); // 默认普通用户
        user.setGender(user.getGender() != null ? user.getGender() : 0); // 默认未知性别
        
        // 保存用户
        User savedUser = userRepository.save(user);
        
        logger.info("用户创建成功: ID={}, Username={}", savedUser.getId(), savedUser.getUsername());
        return convertToDTO(savedUser);
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public UserDTO getUserById(Long id) {
        logger.debug("根据ID获取用户: {}", id);
        
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        return convertToDTO(userOpt.get());
    }

    @Override
    @Cacheable(value = "user", key = "'username:' + #username")
    public UserDTO getUserByUsername(String username) {
        logger.debug("根据用户名获取用户: {}", username);
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        return convertToDTO(userOpt.get());
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        logger.info("更新用户信息: ID={}", id);
        
        // 获取现有用户
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 验证更新数据
        validateUserForUpdate(userDTO, existingUser);
        
        // 更新用户信息
        BeanUtil.copyProperties(userDTO, existingUser, "id", "username", "password", "createTime");
        
        User updatedUser = userRepository.save(existingUser);
        
        logger.info("用户信息更新成功: ID={}, Username={}", updatedUser.getId(), updatedUser.getUsername());
        return convertToDTO(updatedUser);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void deleteUser(Long id) {
        logger.info("删除用户: ID={}", id);
        
        if (!userRepository.existsById(id)) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        userRepository.deleteById(id);
        logger.info("用户删除成功: ID={}", id);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void batchDeleteUsers(List<Long> ids) {
        logger.info("批量删除用户: IDs={}", ids);
        
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "用户ID列表不能为空");
        }
        
        userRepository.deleteAllById(ids);
        logger.info("批量删除用户成功: count={}", ids.size());
    }

    @Override
    public Page<UserDTO> getUsers(Pageable pageable) {
        logger.debug("分页查询用户: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(this::convertToDTO);
    }

    @Override
    public Page<UserDTO> searchUsers(String keyword, Pageable pageable) {
        logger.debug("搜索用户: keyword={}, page={}, size={}", keyword, pageable.getPageNumber(), pageable.getPageSize());
        
        if (StrUtil.isBlank(keyword)) {
            return getUsers(pageable);
        }
        
        Page<User> userPage = userRepository.findByKeyword(keyword, pageable);
        return userPage.map(this::convertToDTO);
    }

    @Override
    public Page<UserDTO> getUsersByStatus(Integer status, Pageable pageable) {
        logger.debug("根据状态查询用户: status={}, page={}, size={}", status, pageable.getPageNumber(), pageable.getPageSize());
        
        Page<User> userPage = userRepository.findByStatus(status, pageable);
        return userPage.map(this::convertToDTO);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void enableUser(Long id) {
        logger.info("启用用户: ID={}", id);
        updateUserStatus(id, 1);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void disableUser(Long id) {
        logger.info("禁用用户: ID={}", id);
        updateUserStatus(id, 0);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void batchUpdateStatus(List<Long> ids, Integer status) {
        logger.info("批量更新用户状态: IDs={}, status={}", ids, status);
        
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "用户ID列表不能为空");
        }
        
        userRepository.batchUpdateStatus(ids, status);
        logger.info("批量更新用户状态成功: count={}", ids.size());
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void resetPassword(Long id, String newPassword) {
        logger.info("重置用户密码: ID={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 加密新密码
        String encodedPassword = BCrypt.hashpw(newPassword);
        user.setPassword(encodedPassword);
        
        userRepository.save(user);
        logger.info("用户密码重置成功: ID={}", id);
    }

    @Override
    public void updateLastLoginInfo(Long id, String loginIp) {
        logger.debug("更新用户最后登录信息: ID={}, IP={}", id, loginIp);
        
        userRepository.updateLastLoginInfo(id, LocalDateTime.now(), loginIp);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return StrUtil.isNotBlank(email) && userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return StrUtil.isNotBlank(phone) && userRepository.existsByPhone(phone);
    }

    @Override
    public UserStatistics getUserStatistics() {
        logger.debug("获取用户统计信息");
        
        long totalUsers = userRepository.countAllUsers();
        long activeUsers = userRepository.countByStatus(1);
        long disabledUsers = userRepository.countByStatus(0);
        
        // 计算今日注册用户数
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = todayStart.plusDays(1);
        long todayRegistrations = userRepository.findByCreateTimeBetween(todayStart, todayEnd, Pageable.unpaged()).getTotalElements();
        
        return new UserStatistics(totalUsers, activeUsers, disabledUsers, todayRegistrations);
    }

    @Override
    public List<UserDTO> getRecentlyActiveUsers(int days) {
        logger.debug("获取最近{}天活跃用户", days);
        
        LocalDateTime sinceDate = LocalDateTime.now().minusDays(days);
        List<User> users = userRepository.findRecentlyActiveUsers(sinceDate);
        
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 更新用户状态
     */
    private void updateUserStatus(Long id, Integer status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        user.setStatus(status);
        userRepository.save(user);
    }

    /**
     * 验证创建用户参数
     */
    private void validateUserForCreate(UserDTO userDTO) {
        if (StrUtil.isBlank(userDTO.getUsername())) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名不能为空");
        }
        
        if (userDTO.getUsername().length() < 3 || userDTO.getUsername().length() > 20) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "用户名长度必须在3-20个字符之间");
        }
    }

    /**
     * 验证更新用户参数
     */
    private void validateUserForUpdate(UserDTO userDTO, User existingUser) {
        // 检查邮箱是否被其他用户使用
        if (StrUtil.isNotBlank(userDTO.getEmail()) && !userDTO.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS);
            }
        }
        
        // 检查手机号是否被其他用户使用
        if (StrUtil.isNotBlank(userDTO.getPhone()) && !userDTO.getPhone().equals(existingUser.getPhone())) {
            if (userRepository.existsByPhone(userDTO.getPhone())) {
                throw new BusinessException(ResultCode.PHONE_ALREADY_EXISTS);
            }
        }
    }

    /**
     * 实体转DTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO, "password");
        return userDTO;
    }
}
