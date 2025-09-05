@echo off
REM MiniServeHub Windows启动脚本
REM 使用方法: scripts\start.bat [环境名称]
REM 环境选项: test, staging, prod

setlocal enabledelayedexpansion

REM 默认环境
set DEFAULT_ENV=test
if "%1"=="" (
    set ENV=%DEFAULT_ENV%
) else (
    set ENV=%1
)

echo ==================================
echo   MiniServeHub Services 启动脚本
echo ==================================

REM 检查环境参数
if "%ENV%"=="test" goto :valid_env
if "%ENV%"=="staging" goto :valid_env
if "%ENV%"=="prod" goto :valid_env

echo [ERROR] 无效的环境参数: %ENV%
echo [INFO] 支持的环境: test, staging, prod
exit /b 1

:valid_env
echo [INFO] 启动环境: %ENV%

REM 检查Java版本
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java未安装或未在PATH中
    exit /b 1
)

echo [SUCCESS] Java版本检查通过

REM 检查Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven未安装或未在PATH中
    exit /b 1
)

echo [SUCCESS] Maven版本检查通过

REM 设置JVM参数
if "%ENV%"=="test" set JVM_OPTS=-Xms512m -Xmx1g
if "%ENV%"=="staging" set JVM_OPTS=-Xms2g -Xmx4g -XX:+UseG1GC
if "%ENV%"=="prod" set JVM_OPTS=-Xms2g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200

REM 编译项目
echo [INFO] 编译项目...
mvn -s .mvn/settings.xml clean compile -q
if errorlevel 1 (
    echo [ERROR] 项目编译失败
    exit /b 1
)

echo [SUCCESS] 项目编译成功

REM 启动应用
echo [INFO] 启动应用 (环境: %ENV%)...
set MAVEN_OPTS=%JVM_OPTS%
mvn -s .mvn/settings.xml spring-boot:run -Dspring-boot.run.profiles=%ENV%
