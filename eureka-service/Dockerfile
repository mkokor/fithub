FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/eureka-service-registry-0.0.1-SNAPSHOT.jar ./app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8761
ENTRYPOINT ["/app/wait-for-it.sh", "config:8888", "--", "java", "-jar", "app.jar"]