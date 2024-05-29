FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/system-events-service-0.0.1-SNAPSHOT.jar ./app.jar
COPY ../wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
ENTRYPOINT [
    "wait-for-it.sh", 
    "database:3306", "--",

    "wait-for-it.sh", 
    "config:8888", "--",

    "wait-for-it.sh", 
    "eureka-service:8761", "--", 
    
    "java", "-jar", "app.jar"]