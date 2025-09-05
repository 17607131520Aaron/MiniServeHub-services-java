# MiniServeHub Services

## 项目简介

MiniServeHub Services 是一个基于 Java 21 和 Spring Boot 3.x 构建的企业级单体后端服务，采用了阿里巴巴和腾讯等大厂的技术体系和最佳实践。

## 技术栈

### 核心框架
- **Java 21** - 最新LTS版本，支持虚拟线程等新特性
- **Spring Boot 3.2.0** - 企业级应用开发框架
- **Spring Data JPA** - 数据访问层框架
- **Spring Cache** - 缓存抽象层

### 数据库相关
- **MySQL 8.0+** - 主数据库
- **Redis** - 缓存数据库
- **Druid** - 阿里巴巴数据库连接池
- **MyBatis Plus** - 增强版ORM框架

### 工具库
- **Hutool** - Java工具类库
- **FastJSON2** - 阿里巴巴JSON处理库
- **Knife4j** - API文档生成工具
- **Redisson** - Redis客户端

### 监控与日志
- **Spring Boot Actuator** - 应用监控
- **Micrometer** - 指标监控
- **Logback** - 日志框架

## 项目结构

```
src/main/java/com/miniservehub/
├── MiniServeHubApplication.java    # 启动类
├── common/                         # 公共组件
│   ├── base/                      # 基础类
│   └── result/                    # 响应结果封装
├── config/                        # 配置类
│   ├── JpaConfig.java            # JPA配置
│   ├── MyBatisPlusConfig.java    # MyBatis Plus配置
│   ├── RedisConfig.java          # Redis配置
│   └── WebConfig.java            # Web配置
├── controller/                    # 控制器层
├── dto/                          # 数据传输对象
├── entity/                       # 实体类
├── exception/                    # 异常处理
├── repository/                   # 数据访问层
└── service/                      # 业务逻辑层
    └── impl/                     # 业务逻辑实现
```

## 功能特性

### 🚀 企业级特性
- **统一响应格式** - 标准化API响应结构
- **全局异常处理** - 统一异常处理机制
- **参数验证** - 完整的参数校验体系
- **缓存支持** - Redis缓存集成
- **分页查询** - 标准化分页实现
- **审计功能** - 自动记录创建和更新时间

### 📊 监控与运维
- **健康检查** - Spring Boot Actuator
- **指标监控** - Prometheus集成
- **数据库监控** - Druid监控面板
- **API文档** - Knife4j自动生成

### 开发特性
- **代码生成** - MyBatis Plus代码生成
- **热部署** - Spring Boot DevTools
- **多环境配置** - dev/test/prod环境隔离
- **日志分级** - 不同环境不同日志级别

## 快速开始

### 环境要求
- Java 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### Maven阿里云镜像配置

本项目已配置项目级别的阿里云镜像，包含：

1. **项目内配置文件**: `.mvn/settings.xml`
   - 阿里云中央仓库镜像
   - 阿里云公共仓库镜像
   - 完整的profiles配置

2. **pom.xml中的仓库配置**:
   - 阿里云中央仓库: `https://maven.aliyun.com/repository/central`
   - 阿里云公共仓库: `https://maven.aliyun.com/repository/public`
   - 阿里云Spring仓库: `https://maven.aliyun.com/repository/spring`

3. **使用方法**:
   ```bash
   # 使用项目内的settings.xml
   mvn -s .mvn/settings.xml clean compile
   
   # 或者直接使用，pom.xml中的仓库配置会自动生效
   mvn clean compile
   ```

### 运行步骤

### 数据库准备

1. 创建数据库：
```sql
CREATE DATABASE miniservehub_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE miniservehub_test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE miniservehub_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 配置数据库连接信息（`src/main/resources/application.yml`）：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/miniservehub_dev
    username: root
    password: your_password
```

### 启动应用

1. **克隆项目**
```bash
git clone <repository-url>
cd MiniServeHub-services-java
```

2. **安装依赖**
```bash
mvn clean install
```

3. **启动Redis**
```bash
redis-server
```

4. **启动应用**
```bash
mvn spring-boot:run
```

或者使用IDE直接运行 `MiniServeHubApplication.java`

### 访问应用

启动成功后，可以访问以下地址：

- **API文档**: http://localhost:8080/api/doc.html
- **数据库监控**: http://localhost:8080/api/druid/ (admin/123456)
- **健康检查**: http://localhost:8080/api/actuator/health
- **应用指标**: http://localhost:8080/api/actuator/metrics

## API接口

### 用户管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/users` | 创建用户 |
| GET | `/api/users/{id}` | 获取用户详情 |
| PUT | `/api/users/{id}` | 更新用户信息 |
| DELETE | `/api/users/{id}` | 删除用户 |
| GET | `/api/users` | 分页查询用户 |
| GET | `/api/users/search` | 搜索用户 |
| PUT | `/api/users/{id}/enable` | 启用用户 |
| PUT | `/api/users/{id}/disable` | 禁用用户 |
| GET | `/api/users/statistics` | 用户统计信息 |

### 请求示例

**创建用户**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "realName": "测试用户",
    "email": "test@example.com",
    "phone": "13800138000"
  }'
```

**分页查询用户**
```bash
curl "http://localhost:8080/api/users?page=0&size=10&sortBy=createTime&sortDir=desc"
```

## 配置说明

### 应用配置

主要配置文件：`src/main/resources/application.yml`

```yaml
server:
  port: 8080                    # 服务端口
  servlet:
    context-path: /api          # 应用上下文路径

spring:
  profiles:
    active: dev                 # 激活的环境配置
  
  datasource:                   # 数据源配置
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    
  jpa:                         # JPA配置
    hibernate:
      ddl-auto: update         # 自动创建/更新表结构
    show-sql: true             # 显示SQL语句
    
  data:
    redis:                     # Redis配置
      host: localhost
      port: 6379
      database: 0
```

### 环境配置

- **开发环境** (`dev`): 详细日志，自动建表
- **测试环境** (`test`): 适中日志，手动建表
- **生产环境** (`prod`): 精简日志，手动建表

## 部署指南

### Docker部署

1. **构建镜像**
```bash
mvn clean package -DskipTests
docker build -t miniservehub-services .
```

2. **运行容器**
```bash
docker run -d \
  --name miniservehub-services \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/miniservehub_prod \
  miniservehub-services
```

### 传统部署

1. **打包应用**
```bash
mvn clean package -DskipTests
```

2. **运行JAR包**
```bash
java -jar target/miniservehub-services-1.0.0.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

## 开发指南

### 代码规范

- 遵循阿里巴巴Java开发手册
- 使用统一的代码格式化配置
- 必须编写单元测试
- API接口必须添加Swagger注解

### 数据库设计规范

- 表名使用下划线命名法
- 主键统一使用雪花算法生成的Long类型
- 必须包含create_time、update_time、deleted字段
- 外键关系通过应用层维护

### 缓存策略

- 查询接口使用`@Cacheable`
- 更新操作使用`@CacheEvict`
- 缓存key统一使用业务前缀
- 设置合理的过期时间

## 性能优化

### 数据库优化
- 合理使用索引
- 避免N+1查询问题
- 使用连接池管理连接
- 定期分析慢查询

### 缓存优化
- 热点数据缓存
- 缓存预热策略
- 缓存穿透防护
- 缓存雪崩防护

### JVM优化
- 合理设置堆内存大小
- 选择合适的垃圾收集器
- 监控GC性能
- 使用Java 21虚拟线程

## 监控告警

### 应用监控
- **健康检查**: `/actuator/health`
- **应用信息**: `/actuator/info`
- **指标数据**: `/actuator/metrics`
- **Prometheus**: `/actuator/prometheus`

### 数据库监控
- **Druid监控**: `/druid/index.html`
- 连接池状态监控
- SQL执行统计
- 慢查询分析

## 故障排查

### 常见问题

1. **启动失败**
   - 检查JDK版本是否为21+
   - 确认数据库连接配置
   - 查看启动日志错误信息

2. **数据库连接失败**
   - 检查MySQL服务状态
   - 验证用户名密码
   - 确认数据库是否存在

3. **Redis连接失败**
   - 检查Redis服务状态
   - 验证连接配置
   - 确认网络连通性

### 日志查看

```bash
# 查看应用日志
tail -f logs/miniservehub.log

# 查看错误日志
grep ERROR logs/miniservehub.log

# 查看特定时间段日志
grep "2024-09-05 23:" logs/miniservehub.log
```

## 贡献指南

1. Fork项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目维护者: MiniServeHub Team
- 邮箱: miniservehub@example.com
- 项目地址: https://github.com/miniservehub/services-java

---

**MiniServeHub Services** - 企业级Java后端服务解决方案
