# 使用官方的 OpenJDK 镜像作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将本地的 JAR 文件复制到容器中
COPY target/LPSystem-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用运行的端口（根据你的 Spring Boot 配置）
EXPOSE 6096

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]