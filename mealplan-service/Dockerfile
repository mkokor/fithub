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
COPY ../wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
EXPOSE 8004
ENTRYPOINT [
    "wait-for-it.sh", 
    "database:3306", "--",

    "wait-for-it.sh", 
    "rabbitmq:5672", "--",

    "wait-for-it.sh", 
    "config:8888", "--",

    "wait-for-it.sh", 
    "eureka-service:8761", "--", 

    "wait-for-it.sh", 
    "system-events:9090", "--", 
    
    "java", "-jar", "app.jar"]