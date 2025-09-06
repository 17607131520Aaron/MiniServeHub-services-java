package com.miniservehub.repository;

import com.miniservehub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * 角色数据访问层
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 根据角色编码查找角色
     */
    Optional<Role> findByRoleCode(String roleCode);

    /**
     * 检查角色编码是否存在
     */
    boolean existsByRoleCode(String roleCode);

    /**
     * 根据状态查找角色
     */
    Set<Role> findByStatus(Integer status);

    /**
     * 根据角色编码集合查找角色
     */
    @Query("SELECT r FROM Role r WHERE r.roleCode IN :roleCodes AND r.status = 1")
    Set<Role> findByRoleCodesAndStatusActive(@Param("roleCodes") Set<String> roleCodes);
}
