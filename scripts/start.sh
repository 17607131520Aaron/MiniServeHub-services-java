#!/bin/bash

# MiniServeHub 多环境启动脚本
# 使用方法: ./scripts/start.sh [环境名称]
# 环境选项: test, staging, prod

set -e

# 默认环境
DEFAULT_ENV="test"
ENV=${1:-$DEFAULT_ENV}

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 日志函数
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查环境参数
check_environment() {
    case $ENV in
        test|staging|prod)
            log_info "启动环境: $ENV"
            ;;
        *)
            log_error "无效的环境参数: $ENV"
            log_info "支持的环境: test, staging, prod"
            exit 1
            ;;
    esac
}

# 检查Java版本
check_java() {
    if ! command -v java &> /dev/null; then
        log_error "Java未安装或未在PATH中"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n1 | awk -F '"' '{print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 21 ]; then
        log_error "需要Java 21或更高版本，当前版本: $JAVA_VERSION"
        exit 1
    fi
    
    log_success "Java版本检查通过: $(java -version 2>&1 | head -n1)"
}

# 检查Maven
check_maven() {
    if ! command -v mvn &> /dev/null; then
        log_error "Maven未安装或未在PATH中"
        exit 1
    fi
    
    log_success "Maven版本: $(mvn -version | head -n1)"
}

# 环境特定检查
check_env_specific() {
    case $ENV in
        test)
            # 检查本地MySQL和Redis
            if ! command -v mysql &> /dev/null; then
                log_warning "MySQL客户端未安装，请确保MySQL服务正在运行"
            fi
            
            if ! redis-cli ping &> /dev/null; then
                log_warning "Redis未启动，请先启动Redis服务"
                log_info "启动命令: redis-server 或 brew services start redis"
            fi
            ;;
        staging|prod)
            # 检查环境变量
            if [ -z "$DB_PASSWORD" ]; then
                log_error "环境变量 DB_PASSWORD 未设置"
                exit 1
            fi
            
            if [ "$ENV" = "prod" ] && [ -z "$JWT_SECRET" ]; then
                log_error "生产环境必须设置 JWT_SECRET 环境变量"
                exit 1
            fi
            ;;
    esac
}

# 编译项目
compile_project() {
    log_info "编译项目..."
    if mvn -s .mvn/settings.xml clean compile -q; then
        log_success "项目编译成功"
    else
        log_error "项目编译失败"
        exit 1
    fi
}

# 启动应用
start_application() {
    log_info "启动应用 (环境: $ENV)..."
    
    # 设置JVM参数
    case $ENV in
        test)
            JVM_OPTS="-Xms512m -Xmx1g"
            ;;
        staging)
            JVM_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC"
            ;;
        prod)
            JVM_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+PrintGCDetails -Xloggc:logs/gc.log"
            ;;
    esac
    
    # 启动命令
    export MAVEN_OPTS="$JVM_OPTS"
    mvn -s .mvn/settings.xml spring-boot:run -Dspring-boot.run.profiles=$ENV
}

# 主函数
main() {
    echo "=================================="
    echo "  MiniServeHub Services 启动脚本  "
    echo "=================================="
    
    check_environment
    check_java
    check_maven
    check_env_specific
    compile_project
    start_application
}

# 执行主函数
main
