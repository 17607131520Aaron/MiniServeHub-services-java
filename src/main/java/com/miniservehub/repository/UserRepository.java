package com.miniservehub.repository;

import com.miniservehub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问层
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    Optional<User> findByPhone(String phone);

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
     * 根据状态查找用户
     *
     * @param status 状态
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<User> findByStatus(Integer status, Pageable pageable);

    /**
     * 根据用户类型查找用户
     *
     * @param userType 用户类型
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<User> findByUserType(Integer userType, Pageable pageable);

    /**
     * 模糊查询用户（用户名、真实姓名、邮箱、手机号）
     *
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% " +
           "OR u.realName LIKE %:keyword% " +
           "OR u.email LIKE %:keyword% " +
           "OR u.phone LIKE %:keyword%")
    Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 根据创建时间范围查找用户
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<User> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    /**
     * 查找最近登录的用户
     *
     * @param days 天数
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginTime >= :sinceDate ORDER BY u.lastLoginTime DESC")
    List<User> findRecentlyActiveUsers(@Param("sinceDate") LocalDateTime sinceDate);

    /**
     * 统计用户总数
     *
     * @return 用户总数
     */
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();

    /**
     * 根据状态统计用户数
     *
     * @param status 状态
     * @return 用户数
     */
    long countByStatus(Integer status);

    /**
     * 更新用户最后登录信息
     *
     * @param userId 用户ID
     * @param loginTime 登录时间
     * @param loginIp 登录IP
     */
    @Modifying
    @Query("UPDATE User u SET u.lastLoginTime = :loginTime, u.lastLoginIp = :loginIp WHERE u.id = :userId")
    void updateLastLoginInfo(@Param("userId") Long userId, 
                           @Param("loginTime") LocalDateTime loginTime, 
                           @Param("loginIp") String loginIp);

    /**
     * 批量更新用户状态
     *
     * @param userIds 用户ID列表
     * @param status 状态
     */
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id IN :userIds")
    void batchUpdateStatus(@Param("userIds") List<Long> userIds, @Param("status") Integer status);

    /**
     * 根据用户类型和状态查找用户
     *
     * @param userType 用户类型
     * @param status 状态
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<User> findByUserTypeAndStatus(Integer userType, Integer status, Pageable pageable);
}
