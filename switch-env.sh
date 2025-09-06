#!/bin/bash

# 环境切换脚本 - 统一配置版本
# 用法: ./switch-env.sh [test|staging|prod]

ENV=$1

if [ -z "$ENV" ]; then
    echo "用法: ./switch-env.sh [test|staging|prod]"
    if [ -f ".env" ]; then
        echo "当前环境: $(grep SPRING_PROFILES_ACTIVE .env | cut -d'=' -f2)"
    else
        echo "当前环境: 未设置"
    fi
    exit 1
fi

case $ENV in
    test)
        echo "切换到测试环境..."
        cp env-examples/.env.test .env
        echo "✅ 已切换到测试环境"
        ;;
    staging)
        echo "切换到预发环境..."
        cp env-examples/.env.staging .env
        echo "✅ 已切换到预发环境"
        ;;
    prod)
        echo "切换到生产环境..."
        cp env-examples/.env.prod .env
        echo "✅ 已切换到生产环境"
        echo "⚠️  请记得修改生产环境的敏感信息！"
        ;;
    *)
        echo "❌ 无效的环境: $ENV"
        echo "支持的环境: test, staging, prod"
        exit 1
        ;;
esac

echo ""
echo "当前环境配置:"
echo "环境: $(grep SPRING_PROFILES_ACTIVE .env | cut -d'=' -f2)"
echo "数据库: $(grep DB_HOST .env | cut -d'=' -f2):$(grep DB_PORT .env | cut -d'=' -f2)/$(grep DB_NAME .env | cut -d'=' -f2)"
echo "Redis: $(grep REDIS_HOST .env | cut -d'=' -f2):$(grep REDIS_PORT .env | cut -d'=' -f2)"
echo "Druid监控: $(grep DRUID_ENABLED .env | cut -d'=' -f2)"
echo "日志级别: $(grep LOG_LEVEL_MINISERVEHUB .env | cut -d'=' -f2)"
echo ""
echo "💡 提示: 现在所有配置都通过环境变量控制，只需修改 .env 文件即可"
