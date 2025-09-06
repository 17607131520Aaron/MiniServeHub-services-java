package com.miniservehub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT密钥
     */
    private String secret = "MiniServeHub2024SecretKeyForJWTTokenGenerationAndValidation";

    /**
     * JWT过期时间（秒）
     */
    private Long expiration = 86400L; // 24小时

    /**
     * 刷新令牌过期时间（秒）
     */
    private Long refreshExpiration = 604800L; // 7天

    /**
     * JWT请求头名称
     */
    private String header = "Authorization";

    /**
     * JWT令牌前缀
     */
    private String tokenPrefix = "Bearer ";

    // Getters and Setters
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getRefreshExpiration() {
        return refreshExpiration;
    }

    public void setRefreshExpiration(Long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
