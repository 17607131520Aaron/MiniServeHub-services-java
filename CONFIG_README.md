# 配置文件使用说明

## 统一配置架构

本项目采用**统一配置文件 + 环境变量**的配置模式，支持以下环境：

- **test** - 测试环境（本地开发默认）
- **staging** - 预发环境
- **prod** - 生产环境

## 配置文件说明

### 1. 统一配置文件
- `application.yml` - **唯一的配置文件**，包含所有环境配置，通过环境变量控制

### 2. 环境变量文件
- `env-examples/.env.test` - 测试环境变量示例
- `env-examples/.env.staging` - 预发环境变量示例
- `env-examples/.env.prod` - 生产环境变量示例
- `.env` - 当前激活的环境变量文件（已加入.gitignore）

## 使用方法

### 快速切换环境
使用提供的脚本快速切换环境：

```bash
# 切换到测试环境
./switch-env.sh test

# 切换到预发环境
./switch-env.sh staging

# 切换到生产环境
./switch-env.sh prod
```

### 手动配置
1. 复制对应的环境变量示例文件：
   ```bash
   cp env-examples/.env.test .env      # 测试环境
   cp env-examples/.env.staging .env   # 预发环境
   cp env-examples/.env.prod .env      # 生产环境
   ```

2. 编辑 `.env` 文件，修改敏感信息：
   ```bash
   vim .env
   ```

3. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

## 环境变量说明

### 服务器配置
- `SERVER_PORT` - 服务器端口（默认8080）
- `TOMCAT_MAX_THREADS` - Tomcat最大线程数
- `TOMCAT_MIN_THREADS` - Tomcat最小线程数
- `TOMCAT_ACCEPT_COUNT` - 接受队列长度
- `TOMCAT_MAX_CONNECTIONS` - 最大连接数

### 数据库配置
- `DB_HOST` - 数据库主机地址
- `DB_PORT` - 数据库端口（默认3306）
- `DB_NAME` - 数据库名称
- `DB_USERNAME` - 数据库用户名
- `DB_PASSWORD` - 数据库密码
- `DB_SSL` - 是否启用SSL（true/false）
- `DB_ALLOW_PUBLIC_KEY` - 是否允许公钥检索

### Druid连接池配置
- `DRUID_INITIAL_SIZE` - 初始连接数
- `DRUID_MIN_IDLE` - 最小空闲连接数
- `DRUID_MAX_ACTIVE` - 最大活跃连接数
- `DRUID_MAX_WAIT` - 最大等待时间（毫秒）
- `DRUID_SLOW_SQL_MILLIS` - 慢SQL阈值（毫秒）

### Druid监控配置
- `DRUID_USERNAME` - Druid监控用户名
- `DRUID_PASSWORD` - Druid监控密码
- `DRUID_ENABLED` - 是否启用Druid监控
- `DRUID_ALLOW_IPS` - 允许访问的IP地址

### JPA配置
- `JPA_DDL_AUTO` - DDL自动模式（update/validate/none）
- `JPA_SHOW_SQL` - 是否显示SQL
- `JPA_FORMAT_SQL` - 是否格式化SQL
- `JPA_USE_COMMENTS` - 是否使用注释
- `JPA_BATCH_SIZE` - 批处理大小

### Redis配置
- `REDIS_HOST` - Redis主机地址
- `REDIS_PORT` - Redis端口（默认6379）
- `REDIS_PASSWORD` - Redis密码
- `REDIS_DATABASE` - Redis数据库索引
- `REDIS_MAX_ACTIVE` - 最大活跃连接数
- `REDIS_MAX_IDLE` - 最大空闲连接数
- `REDIS_MIN_IDLE` - 最小空闲连接数

### 缓存配置
- `CACHE_TTL` - 缓存生存时间（毫秒）

### 日志配置
- `LOG_LEVEL_MINISERVEHUB` - 应用日志级别
- `LOG_LEVEL_WEB` - Web日志级别
- `LOG_LEVEL_SQL` - SQL日志级别
- `LOG_FILE_NAME` - 日志文件名
- `LOG_MAX_FILE_SIZE` - 最大日志文件大小
- `LOG_MAX_HISTORY` - 最大历史文件数

### JWT配置
- `JWT_SECRET` - JWT密钥（生产环境必须）
- `JWT_EXPIRATION` - JWT过期时间（秒）
- `JWT_REFRESH_EXPIRATION` - 刷新令牌过期时间（秒）

### 监控配置
- `MANAGEMENT_ENDPOINTS` - 暴露的监控端点
- `MANAGEMENT_HEALTH_DETAILS` - 健康检查详情显示
- `MANAGEMENT_PORT` - 监控端口

### 业务配置
- `MINISERVEHUB_ENV` - 环境标识
- `MINISERVEHUB_DEBUG` - 调试模式
- `MINISERVEHUB_CACHE_TTL` - 业务缓存TTL
- `MINISERVEHUB_CORS` - 是否启用CORS

## 安全注意事项

1. **生产环境**：
   - 必须修改所有默认密码
   - 使用强密码和长密钥
   - 限制Druid监控访问IP
   - 定期轮换密钥

2. **版本控制**：
   - `.env` 文件已加入 `.gitignore`
   - 不要将包含真实密码的 `.env` 文件提交到版本控制
   - 使用 `env-examples/` 目录中的示例文件作为模板

3. **环境隔离**：
   - 不同环境使用不同的数据库
   - 生产环境关闭SQL日志输出
   - 合理设置连接池大小

## 配置验证

启动应用后，可以通过以下方式验证配置：

1. 查看启动日志中的配置信息
2. 访问 `/actuator/env` 端点（如果启用）
3. 检查数据库连接是否正常
4. 验证Redis连接是否正常
