package com.miniservehub.controller;

import com.miniservehub.common.result.Result;
import com.miniservehub.dto.UserDTO;
import com.miniservehub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Tag(name = "用户管理", description = "用户相关的API接口")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "创建用户", description = "创建新的用户")
    @PostMapping
    public Result<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("创建用户请求: {}", userDTO.getUsername());
        UserDTO createdUser = userService.createUser(userDTO);
        return Result.success("用户导出成功", createdUser);
    }

    @Operation(summary = "根据ID获取用户", description = "根据用户ID获取用户详细信息")
    @GetMapping("/{id}")
    public Result<UserDTO> getUserById(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        logger.debug("获取用户请求: ID={}", id);
        UserDTO user = userService.getUserById(id);
        return Result.success(user);
    }

    @Operation(summary = "根据用户名获取用户", description = "根据用户名获取用户详细信息")
    @GetMapping("/username/{username}")
    public Result<UserDTO> getUserByUsername(
            @Parameter(description = "用户名", required = true) @PathVariable String username) {
        logger.debug("根据用户名获取用户请求: {}", username);
        UserDTO user = userService.getUserByUsername(username);
        return Result.success(user);
    }

    @Operation(summary = "更新用户信息", description = "更新指定用户的信息")
    @PutMapping("/{id}")
    public Result<UserDTO> updateUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        logger.info("更新用户请求: ID={}", id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return Result.success("用户状态更新成功", updatedUser);
    }

    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        logger.info("删除用户请求: ID={}", id);
        userService.deleteUser(id);
        Result<Void> result = Result.success();
        result.setMessage("用户删除成功");
        return result;
    }

    @Operation(summary = "批量删除用户", description = "根据ID列表批量删除用户")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteUsers(@RequestBody List<Long> ids) {
        logger.info("批量删除用户请求: IDs={}", ids);
        userService.batchDeleteUsers(ids);
        Result<Void> result = Result.success();
        result.setMessage("批量删除成功");
        return result;
    }

    @Operation(summary = "分页查询用户", description = "分页获取用户列表")
    @GetMapping
    public Result<Page<UserDTO>> getUsers(
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "createTime") String sortBy,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "desc") String sortDir) {
        
        logger.debug("分页查询用户请求: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
        
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<UserDTO> users = userService.getUsers(pageable);
        return Result.success(users);
    }

    @Operation(summary = "搜索用户", description = "根据关键词搜索用户")
    @GetMapping("/search")
    public Result<Page<UserDTO>> searchUsers(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "createTime") String sortBy,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "desc") String sortDir) {
        
        logger.debug("搜索用户请求: keyword={}, page={}, size={}", keyword, page, size);
        
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<UserDTO> users = userService.searchUsers(keyword, pageable);
        return Result.success(users);
    }

    @Operation(summary = "根据状态查询用户", description = "根据用户状态分页查询用户")
    @GetMapping("/status/{status}")
    public Result<Page<UserDTO>> getUsersByStatus(
            @Parameter(description = "用户状态 0-禁用 1-启用") @PathVariable Integer status,
            @Parameter(description = "页码，从0开始") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段") @RequestParam(defaultValue = "createTime") String sortBy,
            @Parameter(description = "排序方向") @RequestParam(defaultValue = "desc") String sortDir) {
        
        logger.debug("根据状态查询用户请求: status={}, page={}, size={}", status, page, size);
        
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<UserDTO> users = userService.getUsersByStatus(status, pageable);
        return Result.success(users);
    }

    @Operation(summary = "启用用户", description = "启用指定的用户")
    @PutMapping("/{id}/enable")
    public Result<Void> enableUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        logger.info("启用用户请求: ID={}", id);
        userService.enableUser(id);
        Result<Void> result = Result.success();
        result.setMessage("用户启用成功");
        return result;
    }

    @Operation(summary = "禁用用户", description = "禁用指定的用户")
    @PutMapping("/{id}/disable")
    public Result<Void> disableUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id) {
        logger.info("禁用用户请求: ID={}", id);
        userService.disableUser(id);
        Result<Void> result = Result.success();
        result.setMessage("用户禁用成功");
        return result;
    }

    @Operation(summary = "批量更新用户状态", description = "批量更新用户状态")
    @PutMapping("/batch/status")
    public Result<Void> batchUpdateStatus(
            @RequestBody BatchUpdateStatusRequest request) {
        logger.info("批量更新用户状态请求: IDs={}, status={}", request.getIds(), request.getStatus());
        userService.batchUpdateStatus(request.getIds(), request.getStatus());
        Result<Void> result = Result.success();
        result.setMessage("批量更新用户状态成功");
        return result;
    }

    @Operation(summary = "重置用户密码", description = "重置指定用户的密码")
    @PutMapping("/{id}/reset-password")
    public Result<Void> resetPassword(
            @Parameter(description = "用户ID", required = true) @PathVariable Long id,
            @RequestBody ResetPasswordRequest request) {
        logger.info("重置用户密码请求: ID={}", id);
        userService.resetPassword(id, request.getNewPassword());
        Result<Void> result = Result.success();
        result.setMessage("密码重置成功");
        return result;
    }

    @Operation(summary = "检查用户名是否存在", description = "检查指定用户名是否已被使用")
    @GetMapping("/check/username")
    public Result<Boolean> checkUsername(
            @Parameter(description = "用户名") @RequestParam String username) {
        logger.debug("检查用户名请求: {}", username);
        boolean exists = userService.existsByUsername(username);
        return Result.success(exists);
    }

    @Operation(summary = "检查邮箱是否存在", description = "检查指定邮箱是否已被使用")
    @GetMapping("/check/email")
    public Result<Boolean> checkEmail(
            @Parameter(description = "邮箱") @RequestParam String email) {
        logger.debug("检查邮箱请求: {}", email);
        boolean exists = userService.existsByEmail(email);
        return Result.success(exists);
    }

    @Operation(summary = "检查手机号是否存在", description = "检查指定手机号是否已被使用")
    @GetMapping("/check/phone")
    public Result<Boolean> checkPhone(
            @Parameter(description = "手机号") @RequestParam String phone) {
        logger.debug("检查手机号请求: {}", phone);
        boolean exists = userService.existsByPhone(phone);
        return Result.success(exists);
    }

    @Operation(summary = "获取用户统计信息", description = "获取用户相关的统计数据")
    @GetMapping("/statistics")
    public Result<UserService.UserStatistics> getUserStatistics() {
        logger.debug("获取用户统计信息请求");
        UserService.UserStatistics statistics = userService.getUserStatistics();
        return Result.success(statistics);
    }

    @Operation(summary = "获取最近活跃用户", description = "获取最近指定天数内活跃的用户列表")
    @GetMapping("/recent-active")
    public Result<List<UserDTO>> getRecentlyActiveUsers(
            @Parameter(description = "天数") @RequestParam(defaultValue = "7") int days) {
        logger.debug("获取最近活跃用户请求: days={}", days);
        List<UserDTO> users = userService.getRecentlyActiveUsers(days);
        return Result.success(users);
    }

    /**
     * 批量更新状态请求对象
     */
    public static class BatchUpdateStatusRequest {
        private List<Long> ids;
        private Integer status;

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    /**
     * 重置密码请求对象
     */
    public static class ResetPasswordRequest {
        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
