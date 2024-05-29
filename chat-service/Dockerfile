FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY chat-service/pom.xml ./chat-service/
COPY chat-service-rest/pom.xml ./chat-service-rest/
COPY chat-service-core/pom.xml ./chat-service-core/
COPY chat-service-dao/pom.xml ./chat-service-dao/
COPY chat-service-api/pom.xml ./chat-service-api/
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/chat-service/target/chat-service-*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8002
ENTRYPOINT ["/app/wait-for-it.sh", "database:3306", "--", "/app/wait-for-it.sh", "rabbitmq:5672", "--", "/app/wait-for-it.sh", "config:8888", "--", "/app/wait-for-it.sh", "eureka-service:8761", "--", "java", "-jar", "app.jar"]