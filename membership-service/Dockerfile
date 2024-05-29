FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY membership-service/pom.xml ./membership-service/
COPY membership-service-rest/pom.xml ./membership-service-rest/
COPY membership-service-core/pom.xml ./membership-service-core/
COPY membership-service-dao/pom.xml ./membership-service-dao/
COPY membership-service-api/pom.xml ./membership-service-api/
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/membership-service/target/membership-service-*.jar app.jar
COPY ../wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
EXPOSE 8003
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