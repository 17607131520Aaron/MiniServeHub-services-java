package com.miniservehub.service;

import com.miniservehub.dto.UserDTO;
import com.miniservehub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务接口
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userDTO 用户信息
     * @return 用户信息
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserDTO getUserById(Long id);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserDTO getUserByUsername(String username);

    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param userDTO 用户信息
     * @return 更新后的用户信息
     */
    UserDTO updateUser(Long id, UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     */
    void batchDeleteUsers(List<Long> ids);

    /**
     * 分页查询用户
     *
     * @param pageable 分页参数
     * @return 用户分页数据
     */
    Page<UserDTO> getUsers(Pageable pageable);

    /**
     * 根据关键词搜索用户
     *
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 用户分页数据
     */
    Page<UserDTO> searchUsers(String keyword, Pageable pageable);

    /**
     * 根据状态查询用户
     *
     * @param status 状态
     * @param pageable 分页参数
     * @return 用户分页数据
     */
    Page<UserDTO> getUsersByStatus(Integer status, Pageable pageable);

    /**
     * 启用用户
     *
     * @param id 用户ID
     */
    void enableUser(Long id);

    /**
     * 禁用用户
     *
     * @param id 用户ID
     */
    void disableUser(Long id);

    /**
     * 批量更新用户状态
     *
     * @param ids 用户ID列表
     * @param status 状态
     */
    void batchUpdateStatus(List<Long> ids, Integer status);

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     */
    void resetPassword(Long id, String newPassword);

    /**
     * 更新用户最后登录信息
     *
     * @param id 用户ID
     * @param loginIp 登录IP
     */
    void updateLastLoginInfo(Long id, String loginIp);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 获取用户统计信息
     *
     * @return 统计信息
     */
    UserStatistics getUserStatistics();

    /**
     * 获取最近活跃用户
     *
     * @param days 天数
     * @return 用户列表
     */
    List<UserDTO> getRecentlyActiveUsers(int days);

    /**
     * 用户统计信息内部类
     */
    class UserStatistics {
        private long totalUsers;
        private long activeUsers;
        private long disabledUsers;
        private long todayRegistrations;

        // 构造函数
        public UserStatistics() {}

        public UserStatistics(long totalUsers, long activeUsers, long disabledUsers, long todayRegistrations) {
            this.totalUsers = totalUsers;
            this.activeUsers = activeUsers;
            this.disabledUsers = disabledUsers;
            this.todayRegistrations = todayRegistrations;
        }

        // Getters and Setters
        public long getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
        }

        public long getActiveUsers() {
            return activeUsers;
        }

        public void setActiveUsers(long activeUsers) {
            this.activeUsers = activeUsers;
        }

        public long getDisabledUsers() {
            return disabledUsers;
        }

        public void setDisabledUsers(long disabledUsers) {
            this.disabledUsers = disabledUsers;
        }

        public long getTodayRegistrations() {
            return todayRegistrations;
        }

        public void setTodayRegistrations(long todayRegistrations) {
            this.todayRegistrations = todayRegistrations;
        }
    }
}
