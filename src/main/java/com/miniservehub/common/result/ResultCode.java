package com.miniservehub.common.result;

/**
 * 响应状态码枚举
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
public enum ResultCode {

    // 成功
    SUCCESS(0, "操作成功"),

    // 通用错误码 9xxx
    INTERNAL_SERVER_ERROR(9000, "服务器内部错误"),
    BAD_REQUEST(9001, "请求参数错误"),
    UNAUTHORIZED(9002, "未授权"),
    FORBIDDEN(9003, "禁止访问"),
    NOT_FOUND(9004, "资源不存在"),
    METHOD_NOT_ALLOWED(9005, "请求方法不允许"),
    VALIDATE_FAILED(9006, "参数验证失败"),
    SERVICE_UNAVAILABLE(9007, "服务暂不可用"),

    // 用户相关错误码 9100-9199
    USER_NOT_FOUND(9101, "用户不存在"),
    USER_ALREADY_EXISTS(9102, "用户已存在"),
    USERNAME_ALREADY_EXISTS(9103, "用户名已存在"),
    EMAIL_ALREADY_EXISTS(9104, "邮箱已存在"),
    PHONE_ALREADY_EXISTS(9105, "手机号已存在"),
    PASSWORD_ERROR(9106, "密码错误"),
    USER_DISABLED(9107, "用户已被禁用"),
    USER_LOCKED(9108, "用户已被锁定"),

    // 权限相关错误码 9200-9299
    PERMISSION_DENIED(9201, "权限不足"),
    TOKEN_INVALID(9202, "Token无效"),
    TOKEN_EXPIRED(9203, "Token已过期"),
    LOGIN_REQUIRED(9204, "请先登录"),

    // 数据相关错误码 9300-9399
    DATA_NOT_FOUND(9301, "数据不存在"),
    DATA_ALREADY_EXISTS(9302, "数据已存在"),
    DATA_INVALID(9303, "数据格式错误"),
    DATABASE_ERROR(9304, "数据库操作失败"),

    // 文件相关错误码 9400-9499
    FILE_NOT_FOUND(9401, "文件不存在"),
    FILE_UPLOAD_FAILED(9402, "文件上传失败"),
    FILE_SIZE_EXCEEDED(9403, "文件大小超出限制"),
    FILE_TYPE_NOT_SUPPORTED(9404, "文件类型不支持"),

    // 缓存相关错误码 9500-9599
    CACHE_ERROR(9501, "缓存操作失败"),
    REDIS_CONNECTION_ERROR(9502, "Redis连接失败"),

    // 第三方服务错误码 9600-9699
    THIRD_PARTY_SERVICE_ERROR(9601, "第三方服务调用失败"),
    SMS_SEND_FAILED(9602, "短信发送失败"),
    EMAIL_SEND_FAILED(9603, "邮件发送失败");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据状态码获取枚举
     *
     * @param code 状态码
     * @return 结果码枚举
     */
    public static ResultCode getByCode(Integer code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
