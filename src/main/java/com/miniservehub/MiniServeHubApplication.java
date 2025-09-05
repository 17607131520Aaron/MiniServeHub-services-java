package com.miniservehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MiniServeHub 企业级后端服务启动类
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 * @since 2024-09-05
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class MiniServeHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniServeHubApplication.class, args);
        System.out.println("""
            
            ███╗   ███╗██╗███╗   ██╗██╗███████╗███████╗██████╗ ██╗   ██╗███████╗██╗  ██╗██╗   ██╗██████╗ 
            ████╗ ████║██║████╗  ██║██║██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██║  ██║██║   ██║██╔══██╗
            ██╔████╔██║██║██╔██╗ ██║██║███████╗█████╗  ██████╔╝██║   ██║█████╗  ███████║██║   ██║██████╔╝
            ██║╚██╔╝██║██║██║╚██╗██║██║╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██║██║   ██║██╔══██╗
            ██║ ╚═╝ ██║██║██║ ╚████║██║███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║╚██████╔╝██████╔╝
            ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ 
            
            🚀 MiniServeHub Services Started Successfully!
            📖 API Documentation: http://localhost:8080/api/doc.html
            📊 Druid Monitor: http://localhost:8080/api/druid/
            📈 Health Check: http://localhost:8080/api/actuator/health
            """);
    }
}
