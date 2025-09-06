-- MiniServeHub Services 数据库初始化脚本

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `miniservehub_test` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE `miniservehub_test`;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `users` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(50) NOT NULL COMMENT '用户名',
    `email` varchar(100) NOT NULL COMMENT '邮箱',
    `password` varchar(255) NOT NULL COMMENT '密码',
    `status` varchar(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 创建角色表
CREATE TABLE IF NOT EXISTS `roles` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name` varchar(50) NOT NULL COMMENT '角色名称',
    `code` varchar(50) NOT NULL COMMENT '角色编码',
    `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 创建权限表
CREATE TABLE IF NOT EXISTS `permissions` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `name` varchar(50) NOT NULL COMMENT '权限名称',
    `code` varchar(50) NOT NULL COMMENT '权限编码',
    `resource` varchar(100) NOT NULL COMMENT '资源',
    `action` varchar(50) NOT NULL COMMENT '操作',
    `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_resource` (`resource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 创建用户角色关联表
CREATE TABLE IF NOT EXISTS `user_roles` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 创建角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permissions` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `permission_id` bigint NOT NULL COMMENT '权限ID',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 插入初始数据
-- 插入默认角色
INSERT IGNORE INTO `roles` (`name`, `code`, `description`) VALUES
('超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限'),
('管理员', 'ADMIN', '系统管理员，拥有大部分权限'),
('普通用户', 'USER', '普通用户，拥有基础权限');

-- 插入默认权限
INSERT IGNORE INTO `permissions` (`name`, `code`, `resource`, `action`, `description`) VALUES
('用户查看', 'USER_VIEW', 'user', 'view', '查看用户信息'),
('用户创建', 'USER_CREATE', 'user', 'create', '创建用户'),
('用户更新', 'USER_UPDATE', 'user', 'update', '更新用户信息'),
('用户删除', 'USER_DELETE', 'user', 'delete', '删除用户'),
('角色查看', 'ROLE_VIEW', 'role', 'view', '查看角色信息'),
('角色管理', 'ROLE_MANAGE', 'role', 'manage', '管理角色'),
('权限查看', 'PERMISSION_VIEW', 'permission', 'view', '查看权限信息'),
('权限管理', 'PERMISSION_MANAGE', 'permission', 'manage', '管理权限');

-- 插入默认用户（密码为 123456，已加密）
INSERT IGNORE INTO `users` (`username`, `email`, `password`, `status`) VALUES
('admin', 'admin@miniservehub.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVE'),
('testuser', 'test@miniservehub.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVE');

-- 分配角色权限
-- 超级管理员拥有所有权限
INSERT IGNORE INTO `role_permissions` (`role_id`, `permission_id`) 
SELECT r.id, p.id FROM `roles` r, `permissions` p WHERE r.code = 'SUPER_ADMIN';

-- 管理员拥有用户和角色管理权限
INSERT IGNORE INTO `role_permissions` (`role_id`, `permission_id`) 
SELECT r.id, p.id FROM `roles` r, `permissions` p 
WHERE r.code = 'ADMIN' AND p.code IN ('USER_VIEW', 'USER_CREATE', 'USER_UPDATE', 'USER_DELETE', 'ROLE_VIEW', 'ROLE_MANAGE');

-- 普通用户只有查看权限
INSERT IGNORE INTO `role_permissions` (`role_id`, `permission_id`) 
SELECT r.id, p.id FROM `roles` r, `permissions` p 
WHERE r.code = 'USER' AND p.code IN ('USER_VIEW');

-- 分配用户角色
-- admin用户为超级管理员
INSERT IGNORE INTO `user_roles` (`user_id`, `role_id`) 
SELECT u.id, r.id FROM `users` u, `roles` r 
WHERE u.username = 'admin' AND r.code = 'SUPER_ADMIN';

-- testuser用户为普通用户
INSERT IGNORE INTO `user_roles` (`user_id`, `role_id`) 
SELECT u.id, r.id FROM `users` u, `roles` r 
WHERE u.username = 'testuser' AND r.code = 'USER';

COMMIT;
