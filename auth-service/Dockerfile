FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY auth-service/pom.xml ./auth-service/
COPY auth-service-rest/pom.xml ./auth-service-rest/
COPY auth-service-core/pom.xml ./auth-service-core/
COPY auth-service-dao/pom.xml ./auth-service-dao/
COPY auth-service-api/pom.xml ./auth-service-api/
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/auth-service/target/auth-service-*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 9000
ENTRYPOINT ["/app/wait-for-it.sh", "database:3306", "--", "/app/wait-for-it.sh", "rabbitmq:5672", "--", "/app/wait-for-it.sh", "config:8888", "--", "/app/wait-for-it.sh", "eureka-service:8761", "--", "java", "-jar", "app.jar"]