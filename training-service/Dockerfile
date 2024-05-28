FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY training-service/pom.xml ./training-service/
COPY training-service-rest/pom.xml ./training-service-rest/
COPY training-service-core/pom.xml ./training-service-core/
COPY training-service-dao/pom.xml ./training-service-dao/
COPY training-service-api/pom.xml ./training-service-api/
COPY . .
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/training-service/target/training-service-*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]