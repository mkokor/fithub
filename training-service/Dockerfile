FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY training-service/pom.xml ./training-service/
COPY training-service-rest/pom.xml ./training-service-rest/
COPY training-service-core/pom.xml ./training-service-core/
COPY training-service-dao/pom.xml ./training-service-dao/
COPY training-service-api/pom.xml ./training-service-api/
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/training-service/target/training-service-*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8000
ENTRYPOINT ["/app/wait-for-it.sh", "database:3306", "--", "/app/wait-for-it.sh", "rabbitmq:5672", "--", "/app/wait-for-it.sh", "config:8888", "--", "/app/wait-for-it.sh", "eureka-service:8761", "--", "java", "-jar", "app.jar"]