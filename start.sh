#!/bin/bash

# MiniServeHub Services 快速启动脚本
# 用法: ./start.sh [test|staging|prod]

set -e

ENV=${1:-test}
PROFILE=""

echo "🚀 MiniServeHub Services 启动脚本"
echo "=================================="

# 检查Java版本
echo "📋 检查环境..."
if ! command -v java &> /dev/null; then
    echo "❌ Java 未安装，请先安装 Java 21"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "❌ Java 版本过低，需要 Java 21，当前版本: $JAVA_VERSION"
    exit 1
fi
echo "✅ Java 版本: $JAVA_VERSION"

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven 未安装，请先安装 Maven 3.8+"
    exit 1
fi
echo "✅ Maven 已安装"

# 检查MySQL连接
echo "🔍 检查数据库连接..."
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL 客户端未安装，跳过数据库连接检查"
else
    # 这里可以添加数据库连接检查逻辑
    echo "✅ MySQL 客户端已安装"
fi

# 检查Redis连接
echo "🔍 检查Redis连接..."
if ! command -v redis-cli &> /dev/null; then
    echo "⚠️  Redis 客户端未安装，跳过Redis连接检查"
else
    if redis-cli ping &> /dev/null; then
        echo "✅ Redis 连接正常"
    else
        echo "⚠️  Redis 连接失败，请确保Redis服务正在运行"
    fi
fi

# 切换环境
echo ""
echo "🌍 切换环境到: $ENV"
if [ -f "switch-env.sh" ]; then
    ./switch-env.sh $ENV
else
    echo "❌ switch-env.sh 脚本不存在"
    exit 1
fi

# 验证配置
echo ""
echo "🔧 验证配置..."
if [ -f "validate-config.sh" ]; then
    ./validate-config.sh
else
    echo "⚠️  validate-config.sh 脚本不存在，跳过配置验证"
fi

# 清理和编译
echo ""
echo "🔨 编译项目..."
mvn clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ 编译成功"
else
    echo "❌ 编译失败"
    exit 1
fi

# 启动服务
echo ""
echo "🚀 启动服务..."
echo "环境: $ENV"
echo "端口: 8080"
echo "API文档: http://localhost:8080/api/doc.html"
echo "健康检查: http://localhost:8080/api/actuator/health"
echo ""

# 根据环境设置JVM参数
case $ENV in
    test)
        JVM_OPTS="-Xms512m -Xmx1g -XX:+UseG1GC"
        ;;
    staging)
        JVM_OPTS="-Xms1g -Xmx2g -XX:+UseG1GC"
        ;;
    prod)
        JVM_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
        ;;
    *)
        JVM_OPTS="-Xms512m -Xmx1g -XX:+UseG1GC"
        ;;
esac

echo "JVM参数: $JVM_OPTS"
echo ""

# 启动应用
export MAVEN_OPTS="$JVM_OPTS"
mvn spring-boot:run
