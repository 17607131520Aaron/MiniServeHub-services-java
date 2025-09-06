# MiniServeHub Services

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> 企业级Java后端服务，基于Spring Boot 3.2.0 + Java 21构建的现代化微服务架构

## ✨ 特性

- 🚀 **现代化技术栈**: Spring Boot 3.2.0 + Java 21 + Maven
- 🔐 **安全认证**: JWT + Spring Security 6.x
- 🗄️ **数据持久化**: Spring Data JPA + MyBatis Plus + MySQL 8.0
- ⚡ **缓存支持**: Redis + Redisson
- 📊 **监控管理**: Spring Actuator + Prometheus + Druid
- 📚 **API文档**: Knife4j (Swagger 3)
- 🏗️ **统一配置**: 环境变量驱动的多环境配置
- 🛡️ **权限控制**: 基于注解的细粒度权限管理

## 🚀 快速开始

### 环境要求

- **JDK**: 21 或更高版本
- **Maven**: 3.8 或更高版本  
- **MySQL**: 8.0 或更高版本
- **Redis**: 6.0 或更高版本

### 一键启动

```bash
# 克隆项目
git clone <repository-url>
cd MiniServeHub-services-java

# 一键启动（测试环境）
./start.sh test

# 或指定环境启动
./start.sh staging  # 预发环境
./start.sh prod     # 生产环境
```

### 手动启动

```bash
# 1. 配置环境变量
./switch-env.sh test

# 2. 安装依赖
mvn clean install

# 3. 启动服务
mvn spring-boot:run
```

### 验证服务

启动成功后，访问以下地址：

- **API文档**: http://localhost:8080/api/doc.html
- **健康检查**: http://localhost:8080/api/actuator/health
- **Druid监控**: http://localhost:8080/api/druid (测试环境)

## 📁 项目结构

```
MiniServeHub-services-java/
├── src/main/java/com/miniservehub/
│   ├── annotation/          # 自定义注解
│   ├── aspect/             # 切面编程
│   ├── common/             # 公共组件
│   ├── config/             # 配置类
│   ├── controller/         # 控制器层
│   ├── dto/                # 数据传输对象
│   ├── entity/             # 实体类
│   ├── exception/          # 异常处理
│   ├── repository/         # 数据访问层
│   ├── security/           # 安全相关
│   ├── service/            # 业务逻辑层
│   ├── util/               # 工具类
│   └── MiniServeHubApplication.java
├── src/main/resources/
│   └── application.yml     # 统一配置文件
├── env-examples/           # 环境变量示例
├── scripts/                # 脚本文件
├── docs/                   # 文档
└── README.md
```

## 🔧 配置管理

项目采用**统一配置文件 + 环境变量**的配置模式：

- **单一配置文件**: `application.yml` 包含所有环境配置
- **环境变量驱动**: 通过 `.env` 文件控制不同环境
- **一键切换**: 使用脚本快速切换环境

### 环境切换

```bash
# 切换到测试环境
./switch-env.sh test

# 切换到预发环境  
./switch-env.sh staging

# 切换到生产环境
./switch-env.sh prod

# 验证配置
./validate-config.sh
```

### 配置说明

详细配置说明请查看 [CONFIG_README.md](CONFIG_README.md)

## 🛠️ 开发指南

### 开发规范

- **代码规范**: 遵循阿里巴巴Java开发手册
- **架构设计**: 采用经典三层架构 (Controller-Service-Repository)
- **异常处理**: 全局异常处理器 + 自定义业务异常
- **API设计**: RESTful API + 统一返回格式
- **安全规范**: JWT认证 + 密码加密 + 权限控制

详细开发规范请查看 [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md)

### 开发工具

推荐使用以下工具：

- **IDE**: IntelliJ IDEA 2023.3+
- **数据库**: MySQL Workbench / DBeaver
- **API测试**: Postman / Insomnia
- **版本控制**: Git

## 📊 技术栈

### 核心框架

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | LTS版本，现代化语言特性 |
| Spring Boot | 3.2.0 | 企业级应用框架 |
| Spring Security | 6.x | 安全认证框架 |
| Spring Data JPA | 3.x | 数据持久化框架 |

### 数据存储

| 技术 | 版本 | 说明 |
|------|------|------|
| MySQL | 8.0+ | 关系型数据库 |
| Redis | 6.0+ | 缓存数据库 |
| Druid | 1.2.20 | 数据库连接池 |

### 开发工具

| 技术 | 版本 | 说明 |
|------|------|------|
| Maven | 3.8+ | 项目构建工具 |
| MyBatis Plus | 3.5.4.1 | ORM增强框架 |
| Knife4j | 4.3.0 | API文档工具 |
| Hutool | 5.8.22 | Java工具库 |

## 🔐 安全特性

- **JWT认证**: 无状态令牌认证
- **密码加密**: BCrypt密码加密
- **权限控制**: 基于注解的细粒度权限管理
- **CORS支持**: 跨域资源共享配置
- **SQL注入防护**: MyBatis Plus参数化查询
- **XSS防护**: 输入验证和输出编码

## 📈 监控和运维

### 应用监控

- **健康检查**: Spring Actuator健康检查端点
- **指标监控**: Micrometer + Prometheus指标收集
- **数据库监控**: Druid连接池监控
- **JVM监控**: 内存使用和GC监控

### 日志管理

- **分级日志**: 不同环境使用不同日志级别
- **日志文件**: 按环境分离存储
- **日志格式**: 统一JSON格式，便于ELK收集

## 🚀 部署指南

### Docker部署

#### 连接本地数据库和Redis

```bash
# 使用本地MySQL和Redis（推荐）
docker-compose -f docker-compose.local.yml up -d

# 或使用完整环境（包含MySQL和Redis容器）
docker-compose up -d
```

#### 单独构建和运行

```bash
# 构建镜像
docker build -t miniservehub-services .

# 运行容器（连接本地服务）
docker run -d \
  --name miniservehub-services \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e REDIS_HOST=host.docker.internal \
  miniservehub-services
```

### 传统部署

```bash
# 1. 切换生产环境
./switch-env.sh prod

# 2. 编译打包
mvn clean package -DskipTests

# 3. 启动服务
java -jar target/miniservehub-services-1.0.0.jar
```

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系我们

- **项目地址**: [GitHub Repository](https://github.com/your-org/MiniServeHub-services-java)
- **问题反馈**: [Issues](https://github.com/your-org/MiniServeHub-services-java/issues)
- **邮箱**: miniservehub@example.com

---

**⭐ 如果这个项目对你有帮助，请给我们一个Star！**
