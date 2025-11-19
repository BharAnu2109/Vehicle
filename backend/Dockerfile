# Multi-stage build for Spring Boot microservices
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy all service directories
COPY service-registry ./service-registry
COPY config-server ./config-server
COPY api-gateway ./api-gateway
COPY vehicle-service ./vehicle-service
COPY manufacturing-service ./manufacturing-service
COPY inventory-service ./inventory-service
COPY order-service ./order-service

# Build all services
RUN mvn clean package -DskipTests

# Runtime stage - Service Registry
FROM eclipse-temurin:17-jre AS service-registry
WORKDIR /app
COPY --from=build /app/service-registry/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - Config Server
FROM eclipse-temurin:17-jre AS config-server
WORKDIR /app
COPY --from=build /app/config-server/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - API Gateway
FROM eclipse-temurin:17-jre AS api-gateway
WORKDIR /app
COPY --from=build /app/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - Vehicle Service
FROM eclipse-temurin:17-jre AS vehicle-service
WORKDIR /app
COPY --from=build /app/vehicle-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - Manufacturing Service
FROM eclipse-temurin:17-jre AS manufacturing-service
WORKDIR /app
COPY --from=build /app/manufacturing-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - Inventory Service
FROM eclipse-temurin:17-jre AS inventory-service
WORKDIR /app
COPY --from=build /app/inventory-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]

# Runtime stage - Order Service
FROM eclipse-temurin:17-jre AS order-service
WORKDIR /app
COPY --from=build /app/order-service/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
