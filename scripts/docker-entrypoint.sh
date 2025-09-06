#!/bin/bash

# Docker容器启动脚本
set -e

echo "🚀 启动 MiniServeHub Services"
echo "================================"

# 设置默认环境变量
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}
export SERVER_PORT=${SERVER_PORT:-8080}

echo "环境: $SPRING_PROFILES_ACTIVE"
echo "端口: $SERVER_PORT"
echo "JVM参数: $JAVA_OPTS"

# 等待数据库连接（如果设置了等待时间）
if [ -n "$WAIT_FOR_DB" ]; then
    echo "等待数据库连接..."
    sleep $WAIT_FOR_DB
fi

# 启动应用
echo "启动应用..."
exec java $JAVA_OPTS -jar app.jar
