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
{{ ... }}
- **代码生成** - MyBatis Plus代码生成
- **热部署** - Spring Boot DevTools
- **多环境配置** - dev/test/prod环境隔离
- **日志分级** - 不同环境不同日志级别

## 快速开始

### 环境要求
- **Java**: 21+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 6.0+

### 快速启动

#### 1. 使用启动脚本（推荐）
```bash
# 克隆项目
git clone <your-repo-url>
cd MiniServeHub-services-java

# 启动默认测试环境
./scripts/start.sh

# 或启动其他环境
./scripts/start.sh test     # 测试环境
./scripts/start.sh staging  # 预发环境
./scripts/start.sh prod     # 生产环境
```

#### 2. 手动启动步骤

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

## 📥 依赖下载

### 方式一：使用项目内阿里云镜像（推荐）
```bash
# 下载项目依赖（使用阿里云镜像，速度更快）
mvn -s .mvn/settings.xml clean install

# 或者只下载依赖不编译
mvn -s .mvn/settings.xml dependency:resolve
```

### 方式二：使用默认配置
```bash
# 使用pom.xml中配置的阿里云仓库
mvn clean install

# 首次下载可能需要一些时间，后续会使用本地缓存
mvn dependency:resolve
```

### 依赖下载说明
- 项目使用阿里云镜像，国内下载速度3-8MB/s
- 首次下载约需要2-5分钟（取决于网络状况）
- 依赖缓存在 `~/.m2/repository` 目录
- 如遇下载失败，可删除缓存重新下载：`rm -rf ~/.m2/repository`

## 🚀 项目启动

### 前置条件检查
```bash
# 检查Java版本（需要21+）
java -version

# 检查Maven版本（需要3.6+）
mvn -version

# 检查MySQL服务状态
mysql --version
mysqladmin ping

# 检查Redis服务状态
redis-cli ping
```

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

### 启动步骤

#### 1. 克隆项目
```bash
git clone https://github.com/17607131520Aaron/MiniServeHub-services-java.git
cd MiniServeHub-services-java
```

#### 2. 下载依赖
```bash
# 推荐：使用项目内阿里云镜像
mvn -s .mvn/settings.xml clean install

# 或者使用默认配置
mvn clean install
```

#### 3. 启动Redis服务
```bash
# macOS (使用Homebrew)
brew services start redis

# 或者直接启动
redis-server

# Linux
sudo systemctl start redis

# Windows
redis-server.exe
```

#### 4. 启动MySQL服务
```bash
# macOS (使用Homebrew)
brew services start mysql

# Linux
sudo systemctl start mysql

# Windows
net start mysql
```

#### 5. 启动应用

**方式一：使用Maven（推荐）**
```bash
# 使用项目内阿里云镜像配置
mvn -s .mvn/settings.xml spring-boot:run

# 或者使用默认配置
mvn spring-boot:run
```

**方式二：使用IDE**
- 在IDE中直接运行 `src/main/java/com/miniservehub/MiniServeHubApplication.java`

**方式三：使用JAR包**
```bash
# 先打包
mvn -s .mvn/settings.xml clean package -DskipTests

# 运行JAR包
java -jar target/miniservehub-services-1.0.0.jar
```

#### 6. 验证启动成功
```bash
# 检查应用是否启动成功
curl http://localhost:8080/api/actuator/health

# 预期返回：{"status":"UP"}
```

## 📚 API文档访问

### Knife4j API文档（推荐）
- **访问地址**: http://localhost:8080/api/doc.html
- **特性**: 
  - 美观的界面设计
  - 支持在线测试API
  - 支持导出API文档
  - 支持多种主题切换

### Swagger UI（备选）
- **访问地址**: http://localhost:8080/api/swagger-ui/index.html
- **特性**: 
  - 原生Swagger界面
  - 完整的API规范展示
  - 支持API调试

### API文档使用说明

#### 1. 接口测试
```bash
# 在API文档页面可以直接测试接口，也可以使用curl
# 创建用户示例
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "realName": "测试用户",
    "email": "test@example.com",
    "phone": "13800138000"
  }'
```

#### 2. 响应格式
所有API接口都遵循统一的响应格式：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": 1693939200000,
  "traceId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 监控面板访问

#### 数据库监控 - Druid
- **访问地址**: http://localhost:8080/api/druid/
- **默认账号**: admin / 123456
- **功能**: 
  - 数据源监控
  - SQL监控
  - 慢查询分析
  - 连接池状态

#### 应用监控 - Actuator
- **健康检查**: http://localhost:8080/api/actuator/health
- **应用信息**: http://localhost:8080/api/actuator/info  
- **指标数据**: http://localhost:8080/api/actuator/metrics
- **环境信息**: http://localhost:8080/api/actuator/env

### 常用访问地址汇总

| 服务 | 地址 | 说明 |
|------|------|------|
| API文档 | http://localhost:8080/api/doc.html | Knife4j文档界面 |
| Swagger UI | http://localhost:8080/api/swagger-ui/index.html | 原生Swagger界面 |
| 数据库监控 | http://localhost:8080/api/druid/ | Druid监控面板 |
| 健康检查 | http://localhost:8080/api/actuator/health | 应用健康状态 |
| 应用指标 | http://localhost:8080/api/actuator/metrics | 性能指标 |

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

## 🌍 多环境配置

项目支持3个环境，每个环境都有独立的配置文件：

| 环境 | 配置文件 | 用途 | 数据库 | 日志级别 |
|------|----------|------|--------|----------|
| **test** | `application-test.yml` | 本地开发测试（默认） | 本地MySQL | DEBUG |
| **staging** | `application-staging.yml` | 预发环境 | 预发服务器 | INFO |
| **prod** | `application-prod.yml` | 生产环境 | 生产服务器 | WARN |

### 环境启动方式

#### 方式一：使用启动脚本（推荐）
```bash
# Linux/macOS
./scripts/start.sh [环境名称]

# Windows
scripts\start.bat [环境名称]

# 示例
./scripts/start.sh               # 启动默认test环境
./scripts/start.sh test          # 启动测试环境
./scripts/start.sh staging       # 启动预发环境
./scripts/start.sh prod          # 启动生产环境
```

#### 方式二：使用Maven命令
```bash
# 测试环境（默认）
mvn -s .mvn/settings.xml spring-boot:run -Dspring-boot.run.profiles=test

# 预发环境
mvn -s .mvn/settings.xml spring-boot:run -Dspring-boot.run.profiles=staging

# 生产环境
mvn -s .mvn/settings.xml spring-boot:run -Dspring-boot.run.profiles=prod
```

#### 方式三：使用JAR包
```bash
# 先打包
mvn -s .mvn/settings.xml clean package -DskipTests

# 启动不同环境
java -jar target/miniservehub-services-1.0.0.jar --spring.profiles.active=test
java -jar target/miniservehub-services-1.0.0.jar --spring.profiles.active=staging
java -jar target/miniservehub-services-1.0.0.jar --spring.profiles.active=prod
```

### 环境变量配置

每个环境都提供了环境变量示例文件：

```bash
env-examples/
├── .env.test          # 测试环境变量（本地开发）
├── .env.staging       # 预发环境变量
└── .env.prod          # 生产环境变量
```

**使用步骤**：
1. 复制对应环境的示例文件：`cp env-examples/.env.prod .env`
2. 修改环境变量值
3. 启动应用时会自动加载

### 环境特性对比

#### 测试环境 (test) - 默认环境
- 数据库：本地MySQL (`miniservehub_test`)
- Redis：本地Redis (database: 0)
- 日志：DEBUG级别，显示SQL
- 监控：Druid完全开放
- JVM：`-Xms512m -Xmx1g`
- 用途：本地开发和调试

#### 预发环境 (staging)
- 数据库：预发服务器MySQL
- Redis：预发服务器Redis集群
- 日志：INFO级别，管理端口分离
- 监控：限制IP访问
- JVM：`-Xms2g -Xmx4g -XX:+UseG1GC`
- 用途：上线前验证

#### 生产环境 (prod)
- 数据库：生产服务器MySQL集群
- Redis：生产服务器Redis集群
- 日志：WARN级别，GC日志
- 监控：默认关闭Druid
- JVM：G1GC优化配置
- 用途：正式生产环境

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

## 🔧 故障排查

### 快速诊断命令
```bash
# 一键检查环境
echo "=== 环境检查 ==="
java -version
mvn -version
mysql --version 2>/dev/null || echo "MySQL未安装或未在PATH中"
redis-cli ping 2>/dev/null || echo "Redis未启动或未安装"

echo "=== 端口检查 ==="
lsof -i :8080 || echo "端口8080未被占用"
lsof -i :3306 || echo "MySQL端口3306未被占用"  
lsof -i :6379 || echo "Redis端口6379未被占用"
```

### 常见问题及解决方案

#### 1. 启动失败
**问题现象**: 应用启动时抛出异常
```bash
# 检查步骤
java -version                    # 确认Java 21+
mvn -s .mvn/settings.xml clean compile  # 检查编译是否成功
tail -f logs/miniservehub.log   # 查看详细错误日志
```

**常见原因**:
- JDK版本不是21+：升级到Java 21
- 端口被占用：`lsof -i :8080` 检查并杀死占用进程
- 配置文件错误：检查application.yml语法

#### 2. 数据库连接失败
**问题现象**: `java.sql.SQLException: Access denied`
```bash
# 诊断步骤
mysql -u root -p                # 测试数据库连接
show databases;                 # 确认数据库存在
```

**解决方案**:
```sql
-- 创建数据库和用户
CREATE DATABASE miniservehub_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'miniservehub'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON miniservehub_dev.* TO 'miniservehub'@'localhost';
FLUSH PRIVILEGES;
```

#### 3. Redis连接失败  
**问题现象**: `Unable to connect to Redis`
```bash
# 诊断步骤
redis-cli ping                  # 测试Redis连接
redis-server --version          # 检查Redis版本
```

**解决方案**:
```bash
# 启动Redis
brew services start redis      # macOS
sudo systemctl start redis     # Linux
redis-server                   # 直接启动
```

#### 4. 依赖下载失败
**问题现象**: `Could not resolve dependencies`
```bash
# 清理并重新下载
rm -rf ~/.m2/repository
mvn -s .mvn/settings.xml clean install -U
```

#### 5. API文档无法访问
**问题现象**: 404错误
- 确认应用已启动：`curl http://localhost:8080/api/actuator/health`
- 检查context-path配置：访问 `http://localhost:8080/api/doc.html`
- 查看Knife4j配置是否正确

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
