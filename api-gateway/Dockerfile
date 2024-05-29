FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["/app/wait-for-it.sh", "database:3306", "--", "/app/wait-for-it.sh", "rabbitmq:5672", "--", "/app/wait-for-it.sh", "config:8888", "--", "/app/wait-for-it.sh", "eureka-service:8761", "--", "/app/wait-for-it.sh", "system-events:9090", "--", "/app/wait-for-it.sh", "auth-service:9000", "--", "/app/wait-for-it.sh", "chat-service:8002", "--", "/app/wait-for-it.sh", "mealplan-service:8004", "--", "/app/wait-for-it.sh", "membership-service:8003", "--", "/app/wait-for-it.sh", "training-service:8000", "--", "java", "-jar", "app.jar"]