package com.miniservehub.repository;

import com.miniservehub.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * 权限数据访问层
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据权限编码查找权限
     */
    Optional<Permission> findByPermissionCode(String permissionCode);

    /**
     * 检查权限编码是否存在
     */
    boolean existsByPermissionCode(String permissionCode);

    /**
     * 根据状态查找权限
     */
    Set<Permission> findByStatus(Integer status);

    /**
     * 根据权限类型查找权限
     */
    Set<Permission> findByPermissionTypeAndStatus(Integer permissionType, Integer status);

    /**
     * 根据父权限ID查找子权限
     */
    Set<Permission> findByParentIdAndStatus(Long parentId, Integer status);

    /**
     * 根据权限编码集合查找权限
     */
    @Query("SELECT p FROM Permission p WHERE p.permissionCode IN :permissionCodes AND p.status = 1")
    Set<Permission> findByPermissionCodesAndStatusActive(@Param("permissionCodes") Set<String> permissionCodes);
}
