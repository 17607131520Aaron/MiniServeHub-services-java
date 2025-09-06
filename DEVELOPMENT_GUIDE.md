# MiniServeHub Services 开发规范指南

## 📋 项目概述

MiniServeHub Services 是一个基于 Spring Boot 3.2.0 和 Java 21 的企业级后端服务项目，采用现代化的微服务架构设计，提供用户管理、权限控制、JWT认证等核心功能。

### 🏗️ 技术栈

- **Java**: 21 (LTS)
- **Spring Boot**: 3.2.0
- **Spring Security**: 6.x
- **Spring Data JPA**: 3.x
- **MyBatis Plus**: 3.5.4.1
- **MySQL**: 8.0.33
- **Redis**: 6.x
- **Druid**: 1.2.20 (连接池)
- **JWT**: 0.12.3
- **Knife4j**: 4.3.0 (API文档)
- **Maven**: 3.8+

## 🚀 快速开始

### 环境要求

- **JDK**: 21 或更高版本
- **Maven**: 3.8 或更高版本
- **MySQL**: 8.0 或更高版本
- **Redis**: 6.0 或更高版本
- **IDE**: IntelliJ IDEA 2023.3+ (推荐)

### 1. 克隆项目

```bash
git clone <repository-url>
cd MiniServeHub-services-java
```

### 2. 环境配置

#### 2.1 数据库配置

创建MySQL数据库：

```sql
-- 创建数据库
CREATE DATABASE `MiniServeHub-services-test` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户（可选）
CREATE USER 'miniservehub'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON `MiniServeHub-services-test`.* TO 'miniservehub'@'localhost';
FLUSH PRIVILEGES;
```

#### 2.2 Redis配置

确保Redis服务正在运行：

```bash
# 启动Redis服务
redis-server

# 验证Redis连接
redis-cli ping
```

#### 2.3 环境变量配置

```bash
# 切换到测试环境（默认）
./switch-env.sh test

# 或手动复制环境变量文件
cp env-examples/.env.test .env
```

编辑 `.env` 文件，修改数据库和Redis连接信息：

```bash
# 数据库配置
DB_HOST=localhost
DB_PORT=3306
DB_NAME=MiniServeHub-services-test
DB_USERNAME=root
DB_PASSWORD=your_password

# Redis配置
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=
```

### 3. 安装依赖

```bash
# 清理并安装依赖
mvn clean install

# 跳过测试安装（可选）
mvn clean install -DskipTests
```

### 4. 启动服务

#### 4.1 开发模式启动

```bash
# 使用Maven启动
mvn spring-boot:run

# 或使用IDE直接运行
# 运行 com.miniservehub.MiniServeHubApplication
```

#### 4.2 生产模式启动

```bash
# 切换到生产环境
./switch-env.sh prod

# 编译打包
mvn clean package -DskipTests

# 启动JAR包
java -jar target/miniservehub-services-1.0.0.jar
```

### 5. 验证服务

服务启动后，访问以下地址验证：

- **API文档**: http://localhost:8080/api/doc.html
- **健康检查**: http://localhost:8080/api/actuator/health
- **Druid监控**: http://localhost:8080/api/druid (测试环境)

## 📁 项目结构

```
src/main/java/com/miniservehub/
├── annotation/           # 自定义注解
│   ├── IgnoreResponseAdvice.java
│   ├── RequirePermission.java
│   └── RequireRole.java
├── aspect/              # 切面编程
│   └── PermissionAspect.java
├── common/              # 公共组件
│   ├── base/           # 基础实体类
│   └── result/         # 统一返回结果
├── config/              # 配置类
│   ├── GlobalResponseHandler.java
│   ├── JpaConfig.java
│   ├── JwtProperties.java
│   ├── MyBatisPlusConfig.java
│   ├── RedisConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/          # 控制器层
│   ├── AuthController.java
│   └── UserController.java
├── dto/                 # 数据传输对象
│   ├── AuthResponse.java
│   ├── LoginRequest.java
│   ├── RefreshTokenRequest.java
│   ├── RegisterRequest.java
│   ├── UserCreateDTO.java
│   ├── UserDTO.java
│   └── UserUpdateDTO.java
├── entity/              # 实体类
│   ├── Permission.java
│   ├── Role.java
│   └── User.java
├── exception/           # 异常处理
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
├── repository/          # 数据访问层
│   ├── PermissionRepository.java
│   ├── RoleRepository.java
│   └── UserRepository.java
├── security/            # 安全相关
│   └── JwtAuthenticationFilter.java
├── service/             # 业务逻辑层
│   ├── impl/           # 服务实现
│   ├── AuthService.java
│   ├── CustomUserDetailsService.java
│   └── UserService.java
├── util/                # 工具类
│   └── JwtUtil.java
└── MiniServeHubApplication.java  # 启动类
```

## 🎯 开发规范

### 1. 代码规范

#### 1.1 命名规范

- **类名**: 使用大驼峰命名法 (PascalCase)
  ```java
  public class UserController {}
  public class UserServiceImpl {}
  ```

- **方法名**: 使用小驼峰命名法 (camelCase)
  ```java
  public UserDTO getUserById(Long id) {}
  public void createUser(UserCreateDTO dto) {}
  ```

- **变量名**: 使用小驼峰命名法，见名知意
  ```java
  private String userName;
  private List<UserDTO> userList;
  ```

- **常量名**: 使用全大写下划线分隔
  ```java
  public static final String DEFAULT_PASSWORD = "123456";
  public static final int MAX_RETRY_COUNT = 3;
  ```

#### 1.2 包命名规范

- **包名**: 全小写，使用点分隔
- **层次结构**: 按功能模块划分
  ```java
  com.miniservehub.controller    // 控制器
  com.miniservehub.service      // 服务层
  com.miniservehub.repository   // 数据访问层
  com.miniservehub.entity       // 实体类
  com.miniservehub.dto          // 数据传输对象
  com.miniservehub.config       // 配置类
  com.miniservehub.util         // 工具类
  ```

#### 1.3 注释规范

```java
/**
 * 用户服务实现类
 * 
 * @author Your Name
 * @since 1.0.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户信息，如果不存在返回null
     * @throws BusinessException 当用户不存在时抛出
     */
    @Override
    public UserDTO getUserById(Long id) {
        // 实现逻辑
    }
}
```

### 2. 架构规范

#### 2.1 分层架构

项目采用经典的三层架构：

```
Controller Layer (控制器层)
    ↓
Service Layer (业务逻辑层)
    ↓
Repository Layer (数据访问层)
    ↓
Database (数据库)
```

#### 2.2 依赖注入

- 使用 `@Service` 注解标记服务类
- 使用 `@Repository` 注解标记数据访问类
- 使用 `@Controller` 或 `@RestController` 标记控制器类
- 优先使用构造器注入

```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
}
```

#### 2.3 异常处理

- 使用全局异常处理器 `@ControllerAdvice`
- 自定义业务异常 `BusinessException`
- 统一返回结果格式

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
}
```

### 3. 数据库规范

#### 3.1 实体类规范

```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;
}
```

#### 3.2 数据库命名规范

- **表名**: 使用下划线分隔的小写单词
- **字段名**: 使用下划线分隔的小写单词
- **索引名**: `idx_表名_字段名`
- **外键名**: `fk_表名_字段名`

### 4. API设计规范

#### 4.1 RESTful API设计

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {
    
    @GetMapping
    @Operation(summary = "获取用户列表", description = "分页获取用户列表")
    public Result<Page<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 实现逻辑
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情", description = "根据ID获取用户详细信息")
    public Result<UserDTO> getUserById(@PathVariable Long id) {
        // 实现逻辑
    }
    
    @PostMapping
    @Operation(summary = "创建用户", description = "创建新用户")
    public Result<UserDTO> createUser(@Valid @RequestBody UserCreateDTO dto) {
        // 实现逻辑
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "更新用户信息")
    public Result<UserDTO> updateUser(@PathVariable Long id, 
                                    @Valid @RequestBody UserUpdateDTO dto) {
        // 实现逻辑
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    public Result<Void> deleteUser(@PathVariable Long id) {
        // 实现逻辑
    }
}
```

#### 4.2 统一返回格式

```java
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    
    // 成功返回
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), 
                           ResultCode.SUCCESS.getMessage(), data);
    }
    
    // 失败返回
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
```

### 5. 安全规范

#### 5.1 密码安全

```java
@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void createUser(UserCreateDTO dto) {
        // 密码加密
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
    }
}
```

#### 5.2 JWT令牌管理

```java
@Component
public class JwtUtil {
    
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
```

### 6. 测试规范

#### 6.1 单元测试

```java
@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    @DisplayName("根据ID获取用户 - 成功")
    void getUserById_Success() {
        // Given
        Long userId = 1L;
        
        // When
        UserDTO result = userService.getUserById(userId);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
    }
}
```

#### 6.2 集成测试

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        // Given
        UserCreateDTO dto = UserCreateDTO.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
        
        // When
        ResponseEntity<Result> response = restTemplate.postForEntity(
                "/api/users", dto, Result.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
```

## 🔧 开发工具配置

### 1. IDE配置

#### 1.1 IntelliJ IDEA配置

1. **代码格式化**:
   - File → Settings → Editor → Code Style → Java
   - 导入项目根目录的 `code-style.xml` (如果有)

2. **代码检查**:
   - File → Settings → Editor → Inspections
   - 启用所有Java相关的检查规则

3. **Maven配置**:
   - File → Settings → Build → Build Tools → Maven
   - 设置Maven home directory和User settings file

#### 1.2 插件推荐

- **Lombok**: 自动生成getter/setter等
- **MyBatis X**: MyBatis代码生成
- **Rainbow Brackets**: 括号高亮
- **Translation**: 翻译插件

### 2. Git配置

#### 2.1 提交信息规范

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Type类型**:
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具的变动

**示例**:
```
feat(user): 添加用户注册功能

- 实现用户注册接口
- 添加邮箱验证
- 完善密码强度校验

Closes #123
```

#### 2.2 分支管理

- `main`: 主分支，生产环境代码
- `develop`: 开发分支，集成开发功能
- `feature/*`: 功能分支
- `hotfix/*`: 热修复分支
- `release/*`: 发布分支

## 📊 监控和日志

### 1. 应用监控

- **健康检查**: `/api/actuator/health`
- **指标监控**: `/api/actuator/metrics`
- **Prometheus**: `/api/actuator/prometheus`

### 2. 日志管理

- **日志级别**: 通过环境变量控制
- **日志文件**: 按环境分离存储
- **日志格式**: 统一JSON格式，便于ELK收集

### 3. 性能监控

- **Druid监控**: 数据库连接池监控
- **Redis监控**: 缓存性能监控
- **JVM监控**: 内存和GC监控

## 🚀 部署指南

### 1. 环境切换

```bash
# 切换到测试环境
./switch-env.sh test

# 切换到预发环境
./switch-env.sh staging

# 切换到生产环境
./switch-env.sh prod
```

### 2. 配置验证

```bash
# 验证当前配置
./validate-config.sh
```

### 3. 构建部署

```bash
# 清理编译
mvn clean compile

# 运行测试
mvn test

# 打包
mvn package -DskipTests

# 运行
java -jar target/miniservehub-services-1.0.0.jar
```

## 📚 相关文档

- [配置文件说明](CONFIG_README.md)
- [API文档](http://localhost:8080/api/doc.html)
- [Spring Boot官方文档](https://spring.io/projects/spring-boot)
- [MyBatis Plus文档](https://baomidou.com/pages/24112f/)

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

---

**Happy Coding! 🎉**
