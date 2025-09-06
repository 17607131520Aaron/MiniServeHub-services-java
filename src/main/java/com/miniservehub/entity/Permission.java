package com.miniservehub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.miniservehub.common.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限实体类
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_permission", indexes = {
    @Index(name = "idx_permission_code", columnList = "permissionCode", unique = true),
    @Index(name = "idx_permission_name", columnList = "permissionName"),
    @Index(name = "idx_parent_id", columnList = "parentId")
})
@TableName("sys_permission")
public class Permission extends BaseEntity {

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(max = 100, message = "权限编码长度不能超过100个字符")
    @Column(name = "permission_code", nullable = false, unique = true, length = 100)
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 100, message = "权限名称长度不能超过100个字符")
    @Column(name = "permission_name", nullable = false, length = 100)
    private String permissionName;

    /**
     * 权限描述
     */
    @Size(max = 500, message = "权限描述长度不能超过500个字符")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 权限类型 1-菜单 2-按钮 3-接口
     */
    @Column(name = "permission_type", nullable = false, columnDefinition = "TINYINT DEFAULT 3")
    private Integer permissionType;

    /**
     * 父权限ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 权限路径
     */
    @Size(max = 200, message = "权限路径长度不能超过200个字符")
    @Column(name = "permission_path", length = 200)
    private String permissionPath;

    /**
     * HTTP方法
     */
    @Size(max = 10, message = "HTTP方法长度不能超过10个字符")
    @Column(name = "http_method", length = 10)
    private String httpMethod;

    /**
     * 状态 0-禁用 1-启用
     */
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Integer status;

    /**
     * 排序
     */
    @Column(name = "sort_order", columnDefinition = "INT DEFAULT 0")
    private Integer sortOrder;

    /**
     * 拥有该权限的角色
     */
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    // 构造函数
    public Permission() {}

    public Permission(String permissionCode, String permissionName, Integer permissionType) {
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
        this.permissionType = permissionType;
        this.status = 1;
        this.sortOrder = 0;
    }

    // Getters and Setters
    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPermissionPath() {
        return permissionPath;
    }

    public void setPermissionPath(String permissionPath) {
        this.permissionPath = permissionPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionCode='" + permissionCode + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                ", permissionType=" + permissionType +
                ", parentId=" + parentId +
                ", permissionPath='" + permissionPath + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                "} " + super.toString();
    }
}
