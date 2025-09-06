#!/bin/bash

# 配置验证脚本
# 用于验证环境变量和配置文件是否正确

echo "🔍 配置验证脚本"
echo "=================="

# 检查必要文件是否存在
echo "📁 检查文件结构..."
if [ -f "src/main/resources/application.yml" ]; then
    echo "✅ application.yml 存在"
else
    echo "❌ application.yml 不存在"
    exit 1
fi

if [ -f ".env" ]; then
    echo "✅ .env 文件存在"
else
    echo "❌ .env 文件不存在，请先运行 ./switch-env.sh test"
    exit 1
fi

# 检查环境变量文件
echo ""
echo "📋 检查环境变量文件..."
for env in test staging prod; do
    if [ -f "env-examples/.env.$env" ]; then
        echo "✅ .env.$env 存在"
    else
        echo "❌ .env.$env 不存在"
    fi
done

# 显示当前环境配置
echo ""
echo "🌍 当前环境配置:"
echo "环境: $(grep SPRING_PROFILES_ACTIVE .env | cut -d'=' -f2)"
echo "数据库: $(grep DB_HOST .env | cut -d'=' -f2):$(grep DB_PORT .env | cut -d'=' -f2)/$(grep DB_NAME .env | cut -d'=' -f2)"
echo "Redis: $(grep REDIS_HOST .env | cut -d'=' -f2):$(grep REDIS_PORT .env | cut -d'=' -f2)"
echo "Druid监控: $(grep DRUID_ENABLED .env | cut -d'=' -f2)"
echo "日志级别: $(grep LOG_LEVEL_MINISERVEHUB .env | cut -d'=' -f2)"

# 检查关键环境变量
echo ""
echo "🔑 检查关键环境变量..."
required_vars=("DB_HOST" "DB_USERNAME" "DB_PASSWORD" "REDIS_HOST" "JWT_SECRET")
for var in "${required_vars[@]}"; do
    if grep -q "^$var=" .env; then
        echo "✅ $var 已设置"
    else
        echo "❌ $var 未设置"
    fi
done

echo ""
echo "🎯 配置验证完成！"
echo "💡 如需切换环境，请使用: ./switch-env.sh [test|staging|prod]"
