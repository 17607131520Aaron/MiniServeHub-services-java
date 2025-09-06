package com.miniservehub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.miniservehub.common.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体类
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_role", indexes = {
    @Index(name = "idx_role_code", columnList = "roleCode", unique = true),
    @Index(name = "idx_role_name", columnList = "roleName")
})
@TableName("sys_role")
public class Role extends BaseEntity {

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Size(max = 50, message = "角色编码长度不能超过50个字符")
    @Column(name = "role_code", nullable = false, unique = true, length = 50)
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称长度不能超过100个字符")
    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    /**
     * 角色描述
     */
    @Size(max = 500, message = "角色描述长度不能超过500个字符")
    @Column(name = "description", length = 500)
    private String description;

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
     * 角色拥有的权限
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "sys_role_permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    /**
     * 拥有该角色的用户
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    // 构造函数
    public Role() {}

    public Role(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.status = 1;
        this.sortOrder = 0;
    }

    // Getters and Setters
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                "} " + super.toString();
    }
}
