FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY mealplan-service/pom.xml ./mealplan-service/
COPY mealplan-service-rest/pom.xml ./mealplan-service-rest/
COPY mealplan-service-core/pom.xml ./mealplan-service-core/
COPY mealplan-service-dao/pom.xml ./mealplan-service-dao/
COPY mealplan-service-api/pom.xml ./mealplan-service-api/
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/mealplan-service/target/mealplan-service-*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8004
ENTRYPOINT ["/app/wait-for-it.sh", "database:3306", "--", "/app/wait-for-it.sh", "rabbitmq:5672", "--", "/app/wait-for-it.sh", "config:8888", "--", "/app/wait-for-it.sh", "eureka-service:8761", "--", "java", "-jar", "app.jar"]