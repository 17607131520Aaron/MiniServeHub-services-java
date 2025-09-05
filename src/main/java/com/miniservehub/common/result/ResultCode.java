package com.miniservehub.common.result;

/**
 * 响应状态码枚举
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误 4xx
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    VALIDATE_FAILED(422, "参数验证失败"),

    // 服务器错误 5xx
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    // 业务错误码 1xxx
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    USERNAME_ALREADY_EXISTS(1003, "用户名已存在"),
    EMAIL_ALREADY_EXISTS(1004, "邮箱已存在"),
    PHONE_ALREADY_EXISTS(1005, "手机号已存在"),
    PASSWORD_ERROR(1006, "密码错误"),
    USER_DISABLED(1007, "用户已被禁用"),
    USER_LOCKED(1008, "用户已被锁定"),

    // 权限错误码 2xxx
    PERMISSION_DENIED(2001, "权限不足"),
    TOKEN_INVALID(2002, "Token无效"),
    TOKEN_EXPIRED(2003, "Token已过期"),
    LOGIN_REQUIRED(2004, "请先登录"),

    // 数据错误码 3xxx
    DATA_NOT_FOUND(3001, "数据不存在"),
    DATA_ALREADY_EXISTS(3002, "数据已存在"),
    DATA_INVALID(3003, "数据格式错误"),
    DATABASE_ERROR(3004, "数据库操作失败"),

    // 文件错误码 4xxx
    FILE_NOT_FOUND(4001, "文件不存在"),
    FILE_UPLOAD_FAILED(4002, "文件上传失败"),
    FILE_SIZE_EXCEEDED(4003, "文件大小超出限制"),
    FILE_TYPE_NOT_SUPPORTED(4004, "文件类型不支持"),

    // 缓存错误码 5xxx
    CACHE_ERROR(5001, "缓存操作失败"),
    REDIS_CONNECTION_ERROR(5002, "Redis连接失败"),

    // 第三方服务错误码 6xxx
    THIRD_PARTY_SERVICE_ERROR(6001, "第三方服务调用失败"),
    SMS_SEND_FAILED(6002, "短信发送失败"),
    EMAIL_SEND_FAILED(6003, "邮件发送失败");

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
