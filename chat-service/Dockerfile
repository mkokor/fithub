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
COPY ../wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
EXPOSE 8002
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