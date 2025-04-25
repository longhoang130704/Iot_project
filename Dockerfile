# Sử dụng image chính thức của Java
FROM eclipse-temurin:21-jdk-alpine

# Tạo thư mục làm việc trong container
WORKDIR /watering

# Copy file JAR vào container (nhớ đổi tên file JAR nếu cần)
COPY target/watering-0.0.1-SNAPSHOT.jar watering.jar

# Expose port (nếu app chạy ở 8080)
EXPOSE 8081

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "watering.jar"]
